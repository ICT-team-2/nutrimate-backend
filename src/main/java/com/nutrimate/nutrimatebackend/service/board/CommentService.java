package com.nutrimate.nutrimatebackend.service.board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nutrimate.nutrimatebackend.mapper.board.CommentMapper;
import com.nutrimate.nutrimatebackend.model.board.CommentDto;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    public List<CommentDto> getCommentsWithReplies(List<CommentDto> allComments) {
        // commentId를 key로 가지고 CommentDto를 value로 가지는 맵을 생성합니다.
        Map<Integer, CommentDto> commentMap = new HashMap<>();
        for (CommentDto comment : allComments) {
            if (comment.getDeleted().equals("Y")) {
                comment.setCmtContent("삭제된 댓글입니다.");
            }
            commentMap.put(comment.getCmtId(), comment);
        }
        // 대댓글을 부모 댓글의 reply 리스트에 추가합니다.
        for (CommentDto comment : allComments) {
            if (comment.getCmtRef() != null) {
                CommentDto parentComment = commentMap.get(comment.getCmtRef());
                if (parentComment != null) {
                    parentComment.getReplies().add(comment);
                }
            }
        }
        // 최상위 댓글만 리스트에 남기고 나머지는 제거합니다.
        List<CommentDto> comments = new ArrayList<>();
        for (CommentDto comment : allComments) {
            if (comment.getCmtDepth() == 0) {
                comments.add(comment);
            }
        }
        return comments;
    }


    // 글번호에 따른 댓글+대댓글 목록 조회
    public List<CommentDto> findCommentsByBoardId(int boardId) {
        List<CommentDto> allComments = commentMapper.findCommentsByBoardId(boardId);
        // log.info("allComments: " + allComments);
        return getCommentsWithReplies(allComments);
    }

    public List<CommentDto> findRepliesByParentId(int parentCommentId) {
        return commentMapper.findRepliesByParentId(parentCommentId);
    }

    // 대댓글 수 확인
    public int countReplies(int commentId) {
        return commentMapper.countReplies(commentId);
    }

    // 댓글 작성
    public void insertComment(CommentDto commentDto) {
        commentMapper.insertComment(commentDto);
    }

    // 대댓글 작성
    public void insertReply(CommentDto commentDto) {
        commentMapper.insertReply(commentDto);
    }

    // 댓글/대댓글 수정
    public void updateComment(CommentDto commentDto) {
        commentMapper.updateComment(commentDto);
    }

    // 댓글/대댓글 삭제
    // 일단 댓글에 대댓글이 있는지를 확인한 후에 있을 경우 없을 경우에는 삭제,
    // 있을 경우에는 삭제된 댓글입니다로 변경
    public CommentDto deleteComment(CommentDto dto) {
        int replyCount = countRepliesByCommentId(dto);
        log.info("replyCount: " + replyCount, "dto.getCmtId: " + dto.getCmtId());
        // 대댓글이 없을 경우
        if (replyCount <= 1) {
            dto.setAllDeleted("Y");
            commentMapper.deleteComment(dto);
            commentMapper.updateAllDeletedWhenReplyDeleted(dto);
            return dto;
        } else {
            int deletedReplyCount = countDeletedRepliesByCommentId(dto);
            if (replyCount == deletedReplyCount) {
                // 대댓글이 전부 삭제처리되어있을 경우
                dto.setAllDeleted("Y");
                commentMapper.deleteAllRepliesAndComment(dto);
                commentMapper.updateAllDeletedWhenReplyDeleted(dto);
                return dto;
            }
            commentMapper.updateToNoCommentIfGetReplies(dto);
            return dto;
        }
    }

    public Integer findCmtRefByCmtId(int cmtId) {
        return commentMapper.findCmtRefByCmtId(cmtId);
    }

    public int countDeletedRepliesByCommentId(CommentDto dto) {
        return commentMapper.findCountDeletedRepliesByCommentId(dto);
    }

    public int countRepliesByCommentId(CommentDto dto) {
        return commentMapper.findCountRepliesByCmtId(dto);
    }

}
