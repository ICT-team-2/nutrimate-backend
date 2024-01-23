package com.nutrimate.nutrimatebackend.mapper.admin;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.nutrimate.nutrimatebackend.model.admin.BlockDto;
import com.nutrimate.nutrimatebackend.model.admin.ReportDto;

@Mapper
public interface BlockMapper {

	int findByUserIdAndBoarderId(BlockDto dto);//중복신고확인(게시글)
	int findByUserIdAndCmtId(BlockDto dto);//중복신고확인(댓글)
	int insertReport(BlockDto dto);//신고테이블에 insert
	int insertCommentReport(BlockDto dto);//댓글신고
	int insertBorderReport(BlockDto dto);//글신고
	List<BlockDto> findAllBoard(BlockDto dto);//신고 게시글들 목록
	List<BlockDto> findAllComment(BlockDto dto);//신고 댓글 목록
	List<ReportDto> findByCmtId(BlockDto dto);//신고 이유 댓글 별로
	List<ReportDto> findByBoarderId(BlockDto dto);//신고 이유 게시글 별로
	int updateBoarderBlockByBoardId(BlockDto dto);
	int updateCommentBlockByBoardId(BlockDto dto);
	int updateBoarderBlockCancelByBoardId(BlockDto dto);
	int updateCommentBlockCancelByBoardId(BlockDto dto);
    int deleteReportByReport_id(BlockDto dto);// 신고 내역 삭제-report
    int deleteBoardReportByReport_id(BlockDto dto);//게시글 신고 내역 삭제-boardreport
    int deleteCommentReportByReport_id(BlockDto dto);//댓글 신고 내역 삭제-boardreport

}
