package com.nutrimate.nutrimatebackend.model.dmchat;

import lombok.*;
import org.apache.ibatis.type.Alias;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Alias("DMChatRoomDto")
public class DMChatRoomDto {
	int chatroomId;
	List<Integer> userIds; //group
	int userId; //private
	int opponentId; //private
	String chatroomName;
	ChatRoomType chatroomType;
	String chatroomProfile;
	
	String latestChat;
	String latestChatDate;
	
	List<DMChatUserInfo> memberList; //group
	
	
	public enum ChatRoomType {
		PRIVATE, GROUP
	}
}
