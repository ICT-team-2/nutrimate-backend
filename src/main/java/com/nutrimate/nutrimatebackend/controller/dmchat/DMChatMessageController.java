package com.nutrimate.nutrimatebackend.controller.dmchat;

import com.nutrimate.nutrimatebackend.model.dmchat.DMChatMessageDto;
import com.nutrimate.nutrimatebackend.service.dmchat.DMChatMessageService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dm/message")
@Log4j2
public class DMChatMessageController {
	
	DMChatMessageService service;
	
	public DMChatMessageController(DMChatMessageService service) {
		this.service = service;
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map> handleException(Exception e) {
		log.error(e.getMessage(), e);
		return ResponseEntity.badRequest()
				.body(Map.of("error", e.getMessage(),
						"success", false));
	}
	
	@GetMapping("/list/{chatroomId}")
	public List<DMChatMessageDto> findChatMessagesByChatroomId(@PathVariable int chatroomId) {
		return service.findChatMessagesByChatroomId(chatroomId);
	}
	
	//insert/delete는 stomp & @MessageMapping으로
	//dto -> userId랑 chatMessage userNick userProfile만 받으면 됨
	@MessageMapping("/dm/chat/{chatroomId}")
	@SendTo("/sub/topic/dm/{chatroomId}")
	public DMChatMessageDto insertChatMessage(
			@Payload DMChatMessageDto dto,
			@DestinationVariable int chatroomId) throws Exception {
		dto.setChatroomId(chatroomId);
		dto.setChatStatus(DMChatMessageDto.ChatStatus.INSERT);
		log.info("greeting: hello" + dto);
		service.insertChatMessage(dto);
		return dto;
	}
	
	@MessageMapping("/dm/chat/delete/{chatroomId}")
	@SendTo("/sub/topic/dm/delete/{chatroomId}")
	public DMChatMessageDto deleteChatMessage(
			DMChatMessageDto dto,
			@DestinationVariable int chatroomId) throws Exception {
		dto.setChatStatus(DMChatMessageDto.ChatStatus.DELETE);
		service.deleteChatMessage(dto.getChatId());
		return dto;
	}
}
