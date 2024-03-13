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
@Alias("statisticsDto")
public class StatisticsDto {
	
  private String count;
  private String boardcategory;
  private String boardtitle;
  private String boardId;
  private String boardContent;
  private String usernick;
  private java.sql.Date createddate;
  private java.sql.Date day;
  private String month;
  
}
