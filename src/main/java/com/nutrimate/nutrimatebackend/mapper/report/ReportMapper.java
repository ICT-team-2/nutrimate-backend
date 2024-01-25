package com.nutrimate.nutrimatebackend.mapper.report;

import org.apache.ibatis.annotations.Mapper;
import com.nutrimate.nutrimatebackend.mode.report.ReportDto;
@Mapper
public interface ReportMapper {

	int findByUserIdAndBoarderId(ReportDto dto);//중복신고확인(게시글)
	int findByUserIdAndCmtId(ReportDto dto);//중복신고확인(댓글)
	int insertReport(ReportDto dto);//신고테이블에 insert
	int insertCommentReport(ReportDto dto);//댓글신고
	int insertBorderReport(ReportDto dto);//글신고
	

}
