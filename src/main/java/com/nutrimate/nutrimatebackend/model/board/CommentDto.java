package com.nutrimate.nutrimatebackend.model.board;

import java.util.Date;
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
@Alias("CommentDto")
public class CommentDto {

  /** 댓글+대댓글 **/
  private int cmtId; // 댓글번호
  private int boardId; // 글번호(+댓글+대댓글FK)
  private int userId; // 유저아이디(+댓글+대댓글FK)
  private String cmtContent; // 댓글 내용
  private Date createdDate; // 댓글 작성 날짜
  private int cmtDepth; // 댓글 깊이
  private String deleted; // 삭제 여부(Y,N(디폴트))
  private int cmtRef; // 대댓글을 달은 댓글의 번호

}
