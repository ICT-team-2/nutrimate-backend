package com.nutrimate.nutrimatebackend.model.infoboard;

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
@Alias("foodBoardDto")
public class FoodboardDto {
	  private String userNick;
	  private String boardId;
	  private String userId;
	  private String boardCategory;
	  private String boardTitle;
	  private String boardContent;
	  private String boardViewCount;
	  private java.sql.Date createdDate;
	  private String deleted;
	  private String blocked;
	  private String foodId;
	  private String likeCount;
	  private String intakeUnit;
	  private String foodIntake;
	  private String foodCal;
}
