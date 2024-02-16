package com.nutrimate.nutrimatebackend.model.report;

import lombok.*;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Alias("reportDto")
public class ReportDto {
	
	private int reportid;
	private String boardid;
	private String cmtid;
	private String seachKeyWord;
	private String userid;
	private java.sql.Date createddate;
	private String reportreason;
	
}
