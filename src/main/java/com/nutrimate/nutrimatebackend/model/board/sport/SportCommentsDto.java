package com.nutrimate.nutrimatebackend.model.board.sport;

import lombok.*;
import org.apache.ibatis.type.Alias;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Alias("SportCommentsDto")
public class SportCommentsDto {
	private String userNick;
	private int cmtId;
	private long userId;
	private int boardId;
	private String cmtContent;
	private java.sql.Date createdDate;
	private String deleted; //삭제여부(default='N')
	private String blocked; //차단여부(default='N')
	
	private int cmtDepth; //댓글의 깊이(default=0)
	private int cmtRef; //대댓글을 달은 댓글의 번호, 댓글의 경우 null(default=null)
	private int parentDepth; //부모 댓글의 깊이(+1하면 자식 댓글의 깊이)
	private List<SportCommentsDto> replies = new ArrayList<>(); //대댓글 리스트
}
