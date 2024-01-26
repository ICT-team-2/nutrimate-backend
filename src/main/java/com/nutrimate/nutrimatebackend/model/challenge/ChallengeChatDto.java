package com.nutrimate.nutrimatebackend.model.challenge;

import lombok.*;
import lombok.extern.log4j.Log4j2;

import java.sql.Date;

@Builder
@Setter
@Getter
@Log4j2
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ChallengeChatDto {
	
	private MessageType messageType;
	private RoomType chatRoom;
	private Long userId;
	private String chatMessage;
	private String userNick;
	private Date createdDate;
	
	public enum MessageType {
		CHAT,//채팅
		ENTER,//채팅 입장
		LEAVE//채탕 나가기
	}
	
	public enum RoomType {
		FIRST_ROOM("FIRST_ROOM"),
		SECOND_ROOM("SECOND_ROOM");
		
		private final String roomName;
		RoomType(String roomName) {
			this.roomName = roomName;
		}
	}
}
