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
  private int[] reportId;
  private String seachKeyWord;
  private String userid;
  private java.sql.Date createddate;
  private String reportreason;
  private String boardid;
  private String boardContent;
  private String cmtid;
  private String usernick;
  private String count;// 신고들어온 수
  private String boardtitle;
  private String cmtcontent;
  private String blocked;
  private String searchUser;//usernick으로 찾을때
  private String searchContent;//댓글 내용, 게시글 제목으로 찾을때
  private int totalPage;//전체 페이지
  private int nowPage; // 현재 페이지
  private String label;//페이징을 위해서 받는 순서
  private String userprofile;

  private String boardcategory;
  
}
