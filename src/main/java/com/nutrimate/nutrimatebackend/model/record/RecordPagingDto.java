package com.nutrimate.nutrimatebackend.model.record;

import lombok.*;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Alias("RecordPagingDto")
public class RecordPagingDto {
	private String searchWord;
	private int nowPage = 1;
	private int totalPage = 0;
	private int receivePage = 10; //페이지당 받을 데이터 수
	private int totalCount = 0;
	
	private int userId;
}
