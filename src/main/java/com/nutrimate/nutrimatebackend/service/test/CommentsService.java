package com.nutrimate.nutrimatebackend.service.test;

import com.nutrimate.nutrimatebackend.mapper.test.CommentsMapper;
import com.nutrimate.nutrimatebackend.model.member.CommentsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentsService {

    @Autowired
    private CommentsMapper commentsMapper;

    public List<CommentsDto> getAllComments() {
        return commentsMapper.selectAllComments();
    }

    public List<CommentsDto> getCommentsByBoardId(int boardId) {
        return commentsMapper.selectCommentsByBoardId(boardId);
    }

    public void createComment(CommentsDto commentsDto) {
        commentsMapper.insertComment(commentsDto);
    }

    public void updateComment(int cmtId, CommentsDto commentsDto) {
        commentsMapper.updateCommentContent(cmtId, commentsDto);
    }

    public void deleteComment(int cmtId) {
        commentsMapper.deleteComment(cmtId);
    }
  
}
