package com.nutrimate.nutrimatebackend.model.profile;


import lombok.*;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Alias("PagingDto")
public class PagingDto {
	boolean mypage; //자신이 작성한 게시글 목록을 가져올 건지 여부
	boolean bookmark; //북마크 목록을 가져올 건지 여부
	private int totalPage;
	//request param
	private int nowPage = 1;
	private int receivePage = 5;
	private int userId;
}
