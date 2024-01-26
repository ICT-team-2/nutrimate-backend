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
	
	private Long userId;
	private Long challengeCmtId;
	private String challengeCmt;
	private String userNick;
	private Date createdDate;
	
}
