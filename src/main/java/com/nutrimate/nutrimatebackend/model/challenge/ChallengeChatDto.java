package com.nutrimate.nutrimatebackend.model.challenge;

import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.type.Alias;

import java.sql.Date;

@Builder
@Setter
@Getter
@Log4j2
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Alias("ChallengeChatDto")
public class ChallengeChatDto {
	
	private Long chatId;
	private MessageType messageType;
	private RoomType roomType;
	private Long userId;
	private String chatMessage;
	private String challengeNick;
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
