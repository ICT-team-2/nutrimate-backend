package com.nutrimate.nutrimatebackend.model.challenge;

import java.sql.Date;
import org.apache.ibatis.type.Alias;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

@Builder
@Setter
@Getter
@Log4j2
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Alias("ChallengeChatDto")
public class ChallengeChatDto {
	
	

    private String channelId;
    private String messageId;
    private int COUNT;

    private int chatroomId;
    private int userId;
    private MessageType messageType;
    private String chatMessage;
    private String challengeNick;
    private String chatRoomType;
    private RoomType roomType;
    private Date createdDate;

    public enum MessageType {
        CHAT, // 채팅
        ENTER, // 채팅 입장
        LEAVE, // 채탕 나가기
        CHALLENGE
    }



    public enum RoomType {
        FIRST_ROOM("FIRST_ROOM"), SECOND_ROOM("SECOND_ROOM");

        private final String roomName;

        RoomType(String roomName) {
            this.roomName = roomName;
        }


    }


}
