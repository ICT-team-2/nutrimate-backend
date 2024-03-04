package com.nutrimate.nutrimatebackend.model.board.diet;

import com.nutrimate.nutrimatebackend.model.FoodDto;
import lombok.*;
import org.apache.ibatis.type.Alias;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Alias("DietDto")
public class DietDto {
	
	//private List<MultipartFile> files;//업로드 파일
	private String userNick;
	private int boardId;
	private int userId;
	private int label; //rank(글 순서)
	private String boardCategory;
	private String boardTitle;
	private String boardContent;
	private int boardViewCount;
	private java.sql.Date createdDate;
	private String deleted;
	private String blocked;
	
	//검색 관련
	private String searchUser;
	private String searchContent;
	private String searchTitle;//제목으로 찾는 키워드
	private String searchHashTag;//해시태그로 찾는 키워드
	
	private String updateViewCount; //true일 경우 조회수 증가
	
	
	private List<Integer> foodId = new ArrayList<>();
	private String fbImg;
	private List<FoodDto> foodList = new ArrayList<>();
	
	//페이징 관련
	private int totalPage;//전체 페이지
	private int nowPage; // 현재 페이지
	private int receivePage = 10; // 받아올 페이지
	private Integer prevBoardId;
	private Integer nextBoardId;
	
	//좋아요,북마크 관련
	private int checkedLike;
	private int checkedBookmark;
	private int bookmarkCount;
	private int likeCount;
	
	//태그 관련 키워드
	private List<String> tagNameList = new ArrayList<>();
	private String tagName;
	private int tagId;
	
}
