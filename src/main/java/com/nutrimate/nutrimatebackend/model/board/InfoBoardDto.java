package com.nutrimate.nutrimatebackend.model.board;


import lombok.*;
import org.apache.ibatis.type.Alias;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Alias("InfoBoardDto")
public class InfoBoardDto { //정보 게시판 (리스트용)
	String boardTitle;    //제목
	String boardContent;  //내용
	String boardCategory; //카테고리
	int boardId; //게시글 pk
	int rank;   //순위
	int userId; //작성자 pk
	
	String userNick; //작성자 닉네임
	Date createdDate; //작성일
	int viewCount; //조회수
	
	List<String> hashtag = new ArrayList<>(); //해시태그
}
