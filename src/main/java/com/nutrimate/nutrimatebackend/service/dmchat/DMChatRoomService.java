package com.nutrimate.nutrimatebackend.service.dmchat;

import com.nutrimate.nutrimatebackend.mapper.dmchat.DMChatRoomMapper;
import com.nutrimate.nutrimatebackend.model.dmchat.DMChatRoomDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Log4j2
public class DMChatRoomService {
	DMChatRoomMapper mapper;
	
	public DMChatRoomService(DMChatRoomMapper mapper) {
		this.mapper = mapper;
	}
	
	//crud
	public int createChatRoom(DMChatRoomDto dto) {
		try {
			mapper.insertChatRoom(dto);
			mapper.insertMemberChatRoom(dto);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException("Chatroom creation failed");
		}
		return 1;
	}
	
	@Transactional
	public int createChatRoomPrivate(DMChatRoomDto dto) {
		dto.setChatroomType(DMChatRoomDto.ChatRoomType.PRIVATE);
		dto.setUserIds(List.of(dto.getUserId(), dto.getOpponentId()));
		return createChatRoom(dto);
	}
	
	@Transactional
	public int createChatRoomGroup(DMChatRoomDto dto) {
		dto.setChatroomType(DMChatRoomDto.ChatRoomType.GROUP);
		dto.setChatroomName("그룹 채팅방");
		return createChatRoom(dto);
	}
	
	public int updateChatRoomName(DMChatRoomDto dto) {
		return mapper.updateChatRoomName(dto);
	}
	
	public int deleteChatRoom(int chatroomId) {
		return mapper.deleteChatRoom(chatroomId);
	}
	
	public List<DMChatRoomDto> findChatRoomsByUserId(int userId) {
		return mapper.findChatRoomsByUserId(userId);
	}
}
