package com.nutrimate.nutrimatebackend.mapper.dmchat;

import com.nutrimate.nutrimatebackend.model.dmchat.DMChatRoomDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DMChatRoomMapper {
	//crud
	
	int insertChatRoom(DMChatRoomDto dto);
	
	List<DMChatRoomDto> findChatRoomsByUserId(int userId);
	
	int updateChatRoomName(DMChatRoomDto dto);
	
	int deleteChatRoom(int chatroomId);
	
	int insertMemberChatRoom(DMChatRoomDto dto);
}
