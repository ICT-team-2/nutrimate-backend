package com.nutrimate.nutrimatebackend.model.challenge;

import lombok.*;
import lombok.extern.log4j.Log4j2;

import java.sql.Date;

@Builder
@Setter
@Getter
@Log4j2
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ChallengeCommentDto {
	
	private Long cmtId;
	private Long userId;
	private String cmtContent;
	private String challengeNick;
	private String userNick;
	private Date createdDate;
	
	private String deleted;
	private String cmtDepth;
	private String cmtRef;
	private String cmtRefWriter; //부모 댓글의 작성자
}
