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
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Alias("blockDto")
public class BlockDto {
	
  private int reportid;
  private String seachKeyWord;
  private String userid;
  private java.sql.Date createddate;
  private String reportreason;
  private String boardid;
  private String cmtid;
  private String usernick;
  private String count;
  private String boardtitle;
  private String cmtcontent;
  private String blocked;
  private String searchUser;
  private String searchContent;
  
}
