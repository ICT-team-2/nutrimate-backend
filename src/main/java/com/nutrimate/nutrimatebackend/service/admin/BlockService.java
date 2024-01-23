package com.nutrimate.nutrimatebackend.service.admin;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import com.nutrimate.nutrimatebackend.mapper.admin.BlockMapper;
import com.nutrimate.nutrimatebackend.model.admin.BlockDto;
import com.nutrimate.nutrimatebackend.model.admin.ReportDto;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class BlockService {

	 @Autowired
      private BlockMapper blockmapper;

      public int countboarder(BlockDto dto) {
    	  
    	  return blockmapper.findByUserIdAndBoarderId(dto);
      }
      
      public int countcomment(BlockDto dto) {
    	  
    	  return blockmapper.findByUserIdAndCmtId(dto);
      }
      
      @Transactional
      public int saveBoardReport(BlockDto dto) {
    	  try {
    		  blockmapper.insertReport(dto);
              return blockmapper.insertBorderReport(dto);
    	    } catch (Exception e) {
    	       TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
    	       return -1;
    	    }
        
      }
      
      
      @Transactional
      public int saveCommentReport(BlockDto dto) {
    	  try {
    		  blockmapper.insertReport(dto);
              return blockmapper.insertCommentReport(dto);
    	    } catch (Exception e) {
    	       TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
    	       return -1;
    	    }
        
      }

	public List<BlockDto> selectAllBoard(BlockDto dto) {
		return blockmapper.findAllBoard(dto);
	}
	
	
	public List<BlockDto> selectAllComment(BlockDto dto) {
		return blockmapper.findAllComment(dto);
	}

	public List<ReportDto> selectBoardReport(BlockDto dto) {
		return blockmapper.findByBoarderId(dto);
	}

	public List<ReportDto> selectCommentReport(BlockDto dto) {
		return blockmapper.findByCmtId(dto);
	}

	public int blockBoard(BlockDto dto) {
		return blockmapper.updateBoarderBlockByBoardId(dto);
	}

	public int blockComment(BlockDto dto) {
		return blockmapper.updateCommentBlockByBoardId(dto);
	}

	public int blockCancelBoard(BlockDto dto) {
		return blockmapper.updateBoarderBlockCancelByBoardId(dto);
	}

	public int blockCancelComment(BlockDto dto) {
		return blockmapper.updateCommentBlockCancelByBoardId(dto);
	}
    @Transactional
    public int deleteBlockList(BlockDto dto) {
      try {
        blockmapper.deleteBoardReportByReport_id(dto);
        return blockmapper.deleteReportByReport_id(dto);
      } catch (Exception e) {
         TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
         return -1;
      }
    }

    public int deleteBlockListComment(BlockDto dto) {
      try {
        blockmapper.deleteCommentReportByReport_id(dto);
        return blockmapper.deleteReportByReport_id(dto);
      } catch (Exception e) {
         TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
         return -1;
      }
    }
	
	
	
	
}
