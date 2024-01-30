package com.nutrimate.nutrimatebackend.service.report;

import com.nutrimate.nutrimatebackend.mapper.report.ReportMapper;
import com.nutrimate.nutrimatebackend.model.report.ReportDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Log4j2
@Service
public class ReportService {
	
	@Autowired
	private ReportMapper blockmapper;
	
	public int countboarder(ReportDto dto) {
		
		return blockmapper.findByUserIdAndBoarderId(dto);
	}
	
	public int countcomment(ReportDto dto) {
		
		return blockmapper.findByUserIdAndCmtId(dto);
	}
	
	@Transactional
	public int saveBoardReport(ReportDto dto) {
		blockmapper.insertReport(dto);
		return blockmapper.insertBorderReport(dto);
		
		
	}
	
	
	@Transactional
	public int saveCommentReport(ReportDto dto) {
		try {
			blockmapper.insertReport(dto);
			return blockmapper.insertCommentReport(dto);
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return -1;
		}
		
	}
	
	
}
