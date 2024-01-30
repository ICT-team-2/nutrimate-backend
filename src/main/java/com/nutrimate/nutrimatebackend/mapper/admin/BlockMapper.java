package com.nutrimate.nutrimatebackend.mapper.admin;

import com.nutrimate.nutrimatebackend.model.admin.AdminReportDto;
import com.nutrimate.nutrimatebackend.model.admin.BlockDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BlockMapper {
	
	
	List<BlockDto> findAllBoard(BlockDto dto);//신고 게시글들 목록
	
	List<BlockDto> findAllComment(BlockDto dto);//신고 댓글 목록
	
	List<AdminReportDto> findByCmtId(BlockDto dto);//신고 이유 댓글 별로
	
	List<AdminReportDto> findByBoarderId(BlockDto dto);//신고 이유 게시글 별로
	
	int updateBoarderBlockByBoardId(BlockDto dto);//신고한 게시글 차단
	
	int updateCommentBlockByBoardId(BlockDto dto);//신고한 댓글 차단
	
	int updateBoarderBlockCancelByBoardId(BlockDto dto);//신고한 게시글 차단 취소
	
	int updateCommentBlockCancelByBoardId(BlockDto dto);//신고한 댓글 차단 취소
	
	int deleteReportByReport_id(BlockDto dto);// 신고 내역 삭제-report
	
	int deleteBoardReportByReport_id(BlockDto dto);//게시글 신고 내역 삭제-boardreport
	
	int deleteCommentReportByReport_id(BlockDto dto);//댓글 신고 내역 삭제-boardreport
	
}
