package com.nutrimate.nutrimatebackend.mapper.board.diet;

import com.nutrimate.nutrimatebackend.model.board.diet.DietDto;
import com.nutrimate.nutrimatebackend.model.record.FoodDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DietMapper {
	
	//게시글 전체 리스트
	List<DietDto> findAllDietBoard(DietDto dto);//게시글 전체 리스트
	
	//게시글 상세 보기
	DietDto findDietBoardOne(DietDto dto);
	
	//음식 게시글 입력
	int insertFoodBoard(DietDto dto);
	
	//게시글 입력
	void insertBoard(DietDto dto);
	
	//게시글 수정
	void updateBoardByboardId(DietDto dto);
	
	//음식게시글 수정
	int updateFoodBoardByboardId(DietDto dto);
	
	//게시글 삭제
	int updateBoardDeleteByBoardId(DietDto dto);
	
	//좋아요 여부 판별
	int findLikeBoardContByBoardIdAndUserId(DietDto dto);
	
	//좋아요
	int insertLikeBoardContByBoardIdAndUserId(DietDto dto);
	
	//좋아요 삭제
	int deleteLikeBoardContByBoardIdAndUserId(DietDto dto);
	
	int updateViewCountByBoardId(DietDto dto);
	
	//이전글(최근)
	DietDto findPrevByBoardId(DietDto dto);
	
	//다음 글 -(엣날)
	DietDto findNextByBoardId(DietDto dto);
	
	//북마크 여부 판별
	int findBookMarkByBoardIdANDuserId(DietDto dto);
	
	//북마크 입력
	int insertBookMarkByBoardIdAndUserId(DietDto dto);
	
	//북마트 삭제
	int deleteBookMarkByBoardIdANDuserId(DietDto dto);
	
	//해당 해시태그 갯수
	int findHashTagCountByHashTagName(@Param("tagName") String tagName);
	
	//해시태그 저장
	int insertHashTag(DietDto dto);
	
	//해시태그보드 저장
	int insertBoardHashTagByBoardId(DietDto dto);
	
	//해시태그아이디 얻어오기
	int findHashTagIdByHashTagName(@Param("tagName") String tagName);
	
	//해당하는 보드의 해시태그네임 얻어오기
	List<DietDto> findHashTagByBoardId(DietDto dto);
	
	//해당하는 보드의 해시태그 원래 저장값 삭제
	int deleteBoardHashTagByBoardIdAndTagId(DietDto dto);
	
	//해당하는 보드의 해시태그 업데이트
	int updateBoardHashTagByBoardId(DietDto dto);
	
	
	int insertFoodData(@Param("boardId") int boardId, @Param("foodId") int foodId);
	
	int deleteFoodDataByBoardId(DietDto dto);
	
	List<FoodDto> findFoodDataByBoardId(@Param("boardId") int boardId);
}
