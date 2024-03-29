package com.nutrimate.nutrimatebackend.model.board.feed;

import lombok.*;
import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Alias("FeedDto")
public class FeedDto {
	
	private List<MultipartFile> files;// 업로드 파일
	
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
	
	private String searchWord; // 검색어
	
	
	/* 페이징 */
	private int nowPage; // 현재 페이지
	private int receivePage; // 받아올 페이지
	private int totalPages; // 총 페이지
	
	/** 좋아요 **/
	private int likeId; // 좋아요 기본키
	// private int boardId; // 글번호(+좋아요FK)
	// private int userId; // 유저아이디(+좋아요FK)
	private Date createdDate; // 좋아요 누른 날짜
	private int likeCount; // 좋아요 수
	private int checkedLike; // 좋아요 체크
	
	/** 북마크 **/
	private int bookmarkCount;// 북마크 수
	private int checkedBookmark; // 북마크 체크
	// private int userId; // 유저아이디(+북마크FK)
	// private int boardId; // 글번호(+북마크FK)
	// private Date createdDate; // 북마크 누른 날짜
	
	//팔로우
	private int checkedFollowed; // 팔로우 체크
	
	/** 해시태그 (검색) **/
	// private int boardId; // 글번호(+해시태그FK)
	private int tagId; // 태그 기본키
	private String tagName; // 태그 이름
	private List<String> hashtag; // 해시태그
	private int checkTagId; // 해시태그 중복체크
	
	/** 댓글+대댓글 **/
	private int cmtId; // 댓글번호
	// private int boardId; // 글번호(+댓글+대댓글FK)
	// private int userId; // 유저아이디(+댓글+대댓글FK)
	private String cmtContent; // 댓글 내용
	// private Date createdDate; // 댓글 작성 날짜
	private int cmtDepth; // 댓글 깊이
	// private deleted; // 삭제 여부(Y,N(디폴트))
	private int cmtRef; // 대댓글을 달은 댓글의 번호
	private int commentCount; // 댓글 수
	
}
