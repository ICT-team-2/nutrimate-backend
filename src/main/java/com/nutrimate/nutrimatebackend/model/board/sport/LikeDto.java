package com.nutrimate.nutrimatebackend.model.board.sport;

import lombok.*;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Alias("LikeDto")
public class LikeDto {
	private String likeId;
	private String boardId;
	private String userId;
	private java.sql.Date createdDate;
}
