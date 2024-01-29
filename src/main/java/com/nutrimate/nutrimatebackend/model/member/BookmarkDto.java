package com.nutrimate.nutrimatebackend.model.member;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Alias("BookmarkDto")
public class BookmarkDto {
	private String boardId;
	private String userId;
	private java.sql.Date createdDate;
}
