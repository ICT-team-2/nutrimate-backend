package com.nutrimate.nutrimatebackend.model.board.diet;

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
@Alias("dietDto")
public class DietDto {
	
	//private List<MultipartFile> files;//업로드 파일
	private String userNick;
	private int boardId;
	private String userId;
	private String label;
	private String boardCategory;
	private String boardTitle;
	private String boardContent;
	private String foodId;
	private String fbImg;
	private int boardViewCount;
	private java.sql.Date createdDate;
	private String deleted;
	private String blocked;
	private String likeCount;
	private String searchUser;
	private String searchContent;
    private String searchTitle;//제목으로 찾는 키워드
	private String searchHashTag;//해시태그로 찾는 키워드
	private String BOARD;
	private String intakeUnit;
	private String foodIntake;
	private String foodCal;
	private int pagesize;
	private int blockPage;
	private int bookMarkCount;
	private int totalPage;//전체 페이지
	private int nowPage; // 현재 페이지
	private int receivePage; // 받아올 페이지
	
	//태그 관련 키워드
	private String[] tagNameList;
	private String tagName;
	private int tagId;
	
	
}
