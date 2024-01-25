package com.nutrimate.nutrimatebackend.model.member;

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
@Alias("FeedDto")
public class FeedDto {

  /** 회원정보 **/
  private int userId; // 유저아이디
  private String userNick; // 닉네임
  private String userProfile; // 프로필사진
  private String userIntro; // 자기소개
  private String userName; // 이름

  /** 게시판-피드(무한스크롤) **/
  private int boardId; // 글번호
  // private int userId; // 유저아이디(+게시판FK)
  private String boardCategory; // 카테고리(피드는 'FEED')
  private String boardTitle; // 글제목
  private String boardContent; // 글내용
  private String boardThumbnail; // 썸네일(이미지)
  private int boardViewCount; // 조회수
  private Date boardCreatedDate; // 작성일
  private String deleted; // 삭제 여부(Y,N(디폴트))
  private String blocked; // 차단 여부(Y,N(디폴트))
  /* 페이징 */
  private int nowPage; // 현재 페이지
  private int receivePage; // 받아올 페이지

  /** 좋아요 **/
  private int likeId; // 좋아요 기본키
  // private int boardId; // 글번호(+좋아요FK)
  // private int userId; // 유저아이디(+좋아요FK)
  private Date createdDate; // 좋아요 누른 날짜

  /** 북마크 **/
  // private int userId; // 유저아이디(+북마크FK)
  // private int boardId; // 글번호(+북마크FK)
  // private Date createdDate; // 북마크 누른 날짜

  /** 해시태그 (검색) **/
  // private int boardId; // 글번호(+해시태그FK)
  private int tagId; // 태그 기본키
  private String tagName; // 태그 이름

  /** 신고 **/
  private int reportId; // 신고 기본키
  private String reporterId; // 신고자 아이디
  private Date reportTime; // 신고된 날짜
  private String reportReason; // 신고이유
  // private int boardId; // 신고당한 글번호
  // private int cmtId; // 신고당한 댓글번호

  /** 댓글+대댓글 **/
  private int cmtId; // 댓글번호
  // private int boardId; // 글번호(+댓글+대댓글FK)
  // private int userId; // 유저아이디(+댓글+대댓글FK)
  private String cmtContent; // 댓글 내용
  // private Date createdDate; // 댓글 작성 날짜
  private int cmtDepth; // 댓글 깊이
  // private deleted; // 삭제 여부(Y,N(디폴트))
  private int cmtRef; // 대댓글을 달은 댓글의 번호
  // 부모 깊이를 가져와서 자식은 +1 자식 깊이가 됨
  // seleckKey사용

}
