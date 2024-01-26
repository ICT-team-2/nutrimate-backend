package com.nutrimate.nutrimatebackend.mapper.infoboard;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.nutrimate.nutrimatebackend.model.infoboard.DietDto;

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
    int InsertLikeBoardContByBoardIdAndUserId(DietDto dto);
    //좋아요 삭제
    int DeleteLikeBoardContByBoardIdAndUserId(DietDto dto);

    int updateViewCountByBoardId(DietDto dto);
    //이전글(최근)
    DietDto findPrevByBoardId(DietDto dto);
    //다음 글 -(엣날)
    DietDto findNextByBoardId(DietDto dto);
    //북마크 여부 판별
    int findBookMarkByBoardIdANDuserId(DietDto dto);
    //북마크 입력
    int InsertBookMarkByBoardIdANDuserId(DietDto dto);
    //북마트 삭제
    int DeleteBookMarkByBoardIdANDuserId(DietDto dto);
 

}
