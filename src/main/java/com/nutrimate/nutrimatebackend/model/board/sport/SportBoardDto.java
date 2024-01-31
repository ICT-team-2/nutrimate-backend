package com.nutrimate.nutrimatebackend.model.board.sport;

import lombok.*;
import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Alias("SportBoardDto")
@ToString
public class SportBoardDto {
	private int boardId;
	private long userId;
	private String boardCategory;
	private String boardTitle;
	private String boardContent;
	private String boardThumbnail;
	private int boardViewcount;
	private java.sql.Date createdDate;
	private String deleted;
	private String blocked;
	private String mapImg;
	private int likeCount;
	private String userNick;
	private int idRank;
	
	private String searchUser;
	private String searchTitle;
	private String searchContent;
	private String searchTag;
	
	//private int pageNum; //현재 페이지
	//private int pageSize=10; //한페이지에 10개씩
	//private int totalPage; //총 페이지 수
	//private List<MultipartFile> files;
	
	private int tagId; //태그 기본키
	private String tagName; //태그 이름
	private List<String> hashtag; //해시태그
	private int checkTagId; //해시태그 중복체크
}

