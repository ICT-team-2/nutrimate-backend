package com.nutrimate.nutrimatebackend.model.admin;

import org.apache.ibatis.type.Alias;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Alias("reportDto")
public class ReportDto {
	
  private String reportid;
  private String userid;
  private java.sql.Date createddate;
  private String reportreason;
  private int totalPage;//전체 페이지
  private int nowPage; // 현재 페이지
  private String usernick;
  private String label;//페이징을 위해서 받는 순서

  
}
