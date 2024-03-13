package com.nutrimate.nutrimatebackend.model.dmchat;

import lombok.*;
import org.apache.ibatis.type.Alias;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Alias("DMChatMessageDto")
public class DMChatMessageDto {
	int chatId;
	int userId;
	int chatroomId;
	String chatMessage;
	MessageType messageType = MessageType.CHAT;
	String userNick;
	String userProfile;
	
	ChatStatus chatStatus = ChatStatus.INSERT;
	
	public enum ChatStatus {
		INSERT, DELETE
	}
	
	public enum MessageType {
		CHAT,//채팅
		ENTER,//채팅 입장
		LEAVE,//채팅 나가기
	}
}
