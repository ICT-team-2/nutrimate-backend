package com.nutrimate.nutrimatebackend.model.admin;

import lombok.*;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Alias("AdminReportDto")
public class AdminReportDto {
	
	private String reportid;
	private String userid;
	private java.sql.Date createddate;
	private String reportreason;
	private int totalPage;//전체 페이지
	private int nowPage; // 현재 페이지
	private String usernick;
	private String label;//페이징을 위해서 받는 순서
	
	
}
