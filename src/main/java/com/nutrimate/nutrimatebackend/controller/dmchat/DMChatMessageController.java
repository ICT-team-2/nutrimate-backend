package com.nutrimate.nutrimatebackend.controller.dmchat;

import com.nutrimate.nutrimatebackend.model.dmchat.DMChatMessageDto;
import com.nutrimate.nutrimatebackend.service.dmchat.DMChatMessageService;
import lombok.extern.log4j.Log4j2;
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
	public Map handleException(Exception e) {
		log.error(e.getMessage(), e);
		return Map.of("error", e.getMessage(),
				"success", false);
	}
	
	@GetMapping("/{chatroomId}")
	public List<DMChatMessageDto> findChatMessagesByChatroomId(@PathVariable int chatroomId) {
		return service.findChatMessagesByChatroomId(chatroomId);
	}
	
	//insert/delete는 stomp & @MessageMapping으로
	@MessageMapping("/dm/chat")
	@SendTo("/sub/topic/dm")
	public DMChatMessageDto greeting(@Payload DMChatMessageDto message) throws Exception {
		log.info("greeting: hello" + message);
		return message;
	}
}
