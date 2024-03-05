package com.nutrimate.nutrimatebackend.service.board.diet;

import com.nutrimate.nutrimatebackend.mapper.board.diet.DietMapper;
import com.nutrimate.nutrimatebackend.model.board.diet.DietDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

@Service
@Log4j2
public class DietService {
	@Autowired
	private DietMapper dietmapper;
	
	//게시글 전체 리스트
	public List<DietDto> selectListDietBoard(DietDto dto) {
		return dietmapper.findAllDietBoard(dto);
	}
	//게시글 상세 보기
	public DietDto selectDietBoardOne(DietDto dto) {
		return dietmapper.findDietBoardOne(dto);
	}
	//게시글 입력
	@Transactional
	public int saveBoard(DietDto dto) {
		try {
			dietmapper.insertBoard(dto);
			dietmapper.insertFoodBoard(dto);
			insertFoodData(dto);//음식 데이터 입력
			return 1;
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return -1;
		}
	}
	
	//해시태그+게시글 저장
	@Transactional
	public int saveBoardANDHashBoardANDHashTag(DietDto dto) {
		try {
			dietmapper.insertBoard(dto);
			dietmapper.insertFoodBoard(dto);
			for (String tagName : dto.getTagNameList()) {
				int count = dietmapper.findHashTagCountByHashTagName(tagName);
				dto.setTagName(tagName);
				if (count == 0) {
					dietmapper.insertHashTag(dto);
					dietmapper.insertBoardHashTagByBoardId(dto);
				} else {
					int tagId = dietmapper.findHashTagIdByHashTagName(tagName);
					dto.setTagId(tagId);
					dietmapper.insertBoardHashTagByBoardId(dto);
				}
			}
			insertFoodData(dto);//음식 데이터 입력
			return 1;
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return -1;
		}
		
	}
	
	//게시글 수정
	@Transactional
	public int editBoard(DietDto dto) {
		try {
			dietmapper.updateBoardByboardId(dto);
			if (dto.getFbImg() != null) {
				dietmapper.updateFoodBoardByboardId(dto);
			}
			editFoodData(dto);//음식 데이터 입력
			return 1;
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return -1;
		}
	}
	
	int insertFoodData(DietDto dto) throws Exception {
		for (Integer foodId : dto.getFoodId()) {
			dietmapper.insertFoodData(dto.getBoardId(), foodId);
		}
		return 1;
	}
	
	int editFoodData(DietDto dto) throws Exception {
		dietmapper.deleteFoodDataByBoardId(dto);
		for (Integer foodId : dto.getFoodId()) {
			dietmapper.insertFoodData(dto.getBoardId(), foodId);
		}
		return 1;
	}
	
	
	//해시보드 수정+글 수정
	@Transactional
	public int editBoardANDHashBoardANDHashTag(DietDto dto) {
		log.info("dto.getTagNameList(): " + dto.getTagNameList());
		try {
			dietmapper.updateBoardByboardId(dto);
			if (dto.getFbImg() != null) {
				dietmapper.updateFoodBoardByboardId(dto);
			}
			List<DietDto> hashtagList = dietmapper.findHashTagByBoardId(dto);
			if (hashtagList != null) {
				dietmapper.deleteBoardHashTagByBoardIdAndTagId(dto);
			}
			for (String tagName : dto.getTagNameList()) {
				int count = dietmapper.findHashTagCountByHashTagName(tagName);
				dto.setTagName(tagName);
				log.info("tagname: " + tagName);
				if (count == 0) {
					log.info("tagname count 0 : " + tagName);
					dietmapper.insertHashTag(dto);
					dietmapper.insertBoardHashTagByBoardId(dto);
				} else {
					log.info("tagname count 1 : " + tagName);
					int tagId = dietmapper.findHashTagIdByHashTagName(tagName);
					dto.setTagId(tagId);
					dietmapper.insertBoardHashTagByBoardId(dto);
				}
			}
			editFoodData(dto);//음식 데이터 입력
			return 1;
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return -1;
		}
	}
	
	
	//게시글 삭제
	public int deleteBoard(DietDto dto) {
		return dietmapper.updateBoardDeleteByBoardId(dto);
	}
	
	//좋아요 여부 판별
	public int countLike(DietDto dto) {
		return dietmapper.findLikeBoardContByBoardIdAndUserId(dto);
	}
	//좋아요
	public int saveLikeBoard(DietDto dto) {
		return dietmapper.insertLikeBoardContByBoardIdAndUserId(dto);
	}
	//좋아요 취소
	public int deleteLikeBoard(DietDto dto) {
		return dietmapper.deleteLikeBoardContByBoardIdAndUserId(dto);
	}
	//방문자수
	public int saveViewCount(DietDto dto) {
		return dietmapper.updateViewCountByBoardId(dto);
		
	}
	//이전
	public DietDto selectPrev(DietDto dto) {
		return dietmapper.findPrevByBoardId(dto);
	}
	//다음
	public DietDto selectNext(DietDto dto) {
		return dietmapper.findNextByBoardId(dto);
	}
	//북마크 중복 여부
	public int countBookMark(DietDto dto) {
		return dietmapper.findBookMarkByBoardIdANDuserId(dto);
	}
	//북마크 입력
	public int saveBookMarkBoard(DietDto dto) {
		return dietmapper.insertBookMarkByBoardIdAndUserId(dto);
	}
	//북마크 삭제
	public int deleteBookMarkBoard(DietDto dto) {
		return dietmapper.deleteBookMarkByBoardIdANDuserId(dto);
	}
	
	//해시태그 가지고 오기
	public List<DietDto> selectHashTag(DietDto dto) {
		return dietmapper.findHashTagByBoardId(dto);
		
	}
	
	
}
