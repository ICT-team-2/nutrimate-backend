package com.nutrimate.nutrimatebackend.mapper.dmchat;

import com.nutrimate.nutrimatebackend.model.dmchat.DMChatMessageDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DMChatMessageMapper {
	//crud
	int insertChatMessage(DMChatMessageDto dto);
	
	List<DMChatMessageDto> findChatMessagesByChatroomId(int chatroomId);
	
	int deleteChatMessage(int chatId);
}
