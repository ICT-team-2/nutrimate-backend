package com.nutrimate.nutrimatebackend.model.infoboard;

import java.util.List;
import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;
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
  
      private List<MultipartFile> files;//업로드 파일
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
	  private int totalPage;//전체 페이지
	  private int nowPage; // 현재 페이지
	  private int receivePage; // 받아올 페이지


}
