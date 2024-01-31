package com.nutrimate.nutrimatebackend.model.board.feed;

import java.util.Date;
import java.util.List;
import org.apache.ibatis.type.Alias;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Alias("FeedCommentDto")
public class FeedCommentDto {

  /** 댓글+대댓글 **/
  private int cmtId; // 댓글번호
  private int boardId; // 글번호(+댓글+대댓글FK)
  private int userId; // 유저아이디(+댓글+대댓글FK)
  private String cmtContent; // 댓글 내용
  private Date createdDate; // 댓글 작성 날짜
  private int cmtDepth; // 댓글 깊이
  private String deleted; // 삭제 여부(Y,N(디폴트))
  private int cmtRef; // 대댓글을 달은 댓글의 번호
  private String userName;
  private String userNick;

  private List<FeedCommentDto> replies; // 대댓글 리스트 가져오기 위한 변수
  private int mycmtId; // 대댓글 입력시 cmtId 받기위한 변수

}
