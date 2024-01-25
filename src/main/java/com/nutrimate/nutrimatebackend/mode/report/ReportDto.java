package com.nutrimate.nutrimatebackend.mode.report;

import org.apache.ibatis.type.Alias;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
