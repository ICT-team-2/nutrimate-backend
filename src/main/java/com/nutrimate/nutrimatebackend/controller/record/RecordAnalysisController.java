package com.nutrimate.nutrimatebackend.controller.record;

import com.nutrimate.nutrimatebackend.model.record.AnalyzeDateDto;
import com.nutrimate.nutrimatebackend.model.record.RecordAnalysisDto;
import com.nutrimate.nutrimatebackend.service.record.RecordAnalysisService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/record/analysis")
public class RecordAnalysisController {
	
	private RecordAnalysisService recordAnalysisService;
	
	public RecordAnalysisController(RecordAnalysisService recordAnalysisService) {
		this.recordAnalysisService = recordAnalysisService;
	}
	@GetMapping()
	public RecordAnalysisDto findRecordAnalysis(AnalyzeDateDto dateDto) {
		return recordAnalysisService.findRecordAnalysis(dateDto).get(0);
	}
	@GetMapping("/graph")
	public List<RecordAnalysisDto> findRecordAnalysisForGraph(AnalyzeDateDto dateDto) {
		return recordAnalysisService.findRecordAnalysisForGraph(dateDto);
	}
	
}
