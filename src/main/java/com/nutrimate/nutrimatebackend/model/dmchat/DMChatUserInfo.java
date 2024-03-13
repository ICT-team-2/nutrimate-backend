package com.nutrimate.nutrimatebackend.model.dmchat;

import lombok.*;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Alias("DMChatUserInfo")
public class DMChatUserInfo {
	
	private int userId;
	private String userNick;
	private String userProfile;
}
