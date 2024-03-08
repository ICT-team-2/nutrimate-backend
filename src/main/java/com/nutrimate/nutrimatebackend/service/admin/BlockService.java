package com.nutrimate.nutrimatebackend.service.admin;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import com.nutrimate.nutrimatebackend.mapper.admin.BlockMapper;
import com.nutrimate.nutrimatebackend.model.admin.AdminReportDto;
import com.nutrimate.nutrimatebackend.model.admin.BlockDto;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class BlockService {
	
	@Autowired
	private BlockMapper blockmapper;
	
	
	//신고한 글 보기-게시글
	public List<BlockDto> selectAllBoard(BlockDto dto) {
		return blockmapper.findAllBoard(dto);
	}
	
	//신고한 글 보기-댓글
	public List<BlockDto> selectAllComment(BlockDto dto) {
		return blockmapper.findAllComment(dto);
	}
	//신고한 글 사유-게시글
	public List<AdminReportDto> selectBoardReport(BlockDto dto) {
		return blockmapper.findByBoarderId(dto);
	}
	//신고한 글 사유-댓글
	public List<AdminReportDto> selectCommentReport(BlockDto dto) {
		return blockmapper.findByCmtId(dto);
	}
	//신고한 게시글 차단
	public int blockBoard(BlockDto dto) {
		return blockmapper.updateBoarderBlockByBoardId(dto);
	}
	//신고한 댓글 차단
	public int blockComment(BlockDto dto) {
		return blockmapper.updateCommentBlockByBoardId(dto);
	}
	//신고한 게시글 차단 취소
	public int blockCancelBoard(BlockDto dto) {
		return blockmapper.updateBoarderBlockCancelByBoardId(dto);
	}
	//신고한 댓글 차단 취소
	public int blockCancelComment(BlockDto dto) {
		return blockmapper.updateCommentBlockCancelByBoardId(dto);
	}
	//게시글 신고 내역 삭제
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
	//댓글 신고 내역 삭제
	@Transactional
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
