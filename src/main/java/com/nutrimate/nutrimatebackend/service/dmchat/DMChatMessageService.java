package com.nutrimate.nutrimatebackend.service.dmchat;

import com.nutrimate.nutrimatebackend.mapper.dmchat.DMChatMessageMapper;
import com.nutrimate.nutrimatebackend.model.dmchat.DMChatMessageDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DMChatMessageService {
	DMChatMessageMapper mapper;
	
	public DMChatMessageService(DMChatMessageMapper mapper) {
		this.mapper = mapper;
	}
	
	//crud
	public int insertChatMessage(DMChatMessageDto dto) {
		return mapper.insertChatMessage(dto);
	}
	
	public List<DMChatMessageDto> findChatMessagesByChatroomId(int chatroomId) {
		return mapper.findChatMessagesByChatroomId(chatroomId);
	}
	
	
	public int deleteChatMessage(int chatId) {
		return mapper.deleteChatMessage(chatId);
	}
	
}
