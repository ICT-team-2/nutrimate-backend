package com.nutrimate.nutrimatebackend.model.admin;

import org.apache.ibatis.type.Alias;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Alias("reportDto")
public class ReportDto {
	
  private String reportid;
  private String userid;
  private java.sql.Date createddate;
  private String reportreason;
  
}
