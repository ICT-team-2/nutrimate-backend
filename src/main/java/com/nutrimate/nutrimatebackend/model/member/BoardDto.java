package com.nutrimate.nutrimatebackend.model.member;

import java.util.List;

import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Alias("BoardDto")
public class BoardDto {
	private int boardId;
	private long userId;
	private String boardCategory;
	private String boardTitle;
	private String boardContent;
	private String boardThumbnail;
	private String boardViewcount;
	private java.sql.Date createdDate;
	private String deleted;
	private String blocked;
	private String mapImg;
	private int likeCount;
	private String userNick;
	private int idRank;
	
	//private int pageNum; //현재 페이지
	//private int pageSize=10; //한페이지에 10개씩
	//private int totalPage; //총 페이지 수
	
	private String searchUser;
	private String searchTitle;
	
	private List<MultipartFile> files;
}

