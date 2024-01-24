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
@Alias("dietDto")
public class DietDto {
	  private String userNick;
	  private int boardId;
	  private String userId;
	  private String label;
	  private String boardCategory;
	  private String boardTitle;
	  private String boardContent;
	  private String foodId;
	  private String fbImage;
	  private int boardViewCount;
	  private String deleted;
	  private String blocked;
	  private String likeCount;
	  private String searchTitle;
	  private String searchUser;
	  private String BOARD;

}
