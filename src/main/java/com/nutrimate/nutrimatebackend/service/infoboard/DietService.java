package com.nutrimate.nutrimatebackend.service.infoboard;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import com.nutrimate.nutrimatebackend.mapper.infoboard.DietMapper;
import com.nutrimate.nutrimatebackend.model.infoboard.DietDto;

@Service
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
            return dietmapper.insertFoodBoard(dto);
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
              return dietmapper.updateFoodBoardByboardId(dto);
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
      return dietmapper.InsertLikeBoardContByBoardIdAndUserId(dto);
    }
    //좋아요 취소
    public int DeleteLikeBoard(DietDto dto) {
      return dietmapper.DeleteLikeBoardContByBoardIdAndUserId(dto);
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





}
