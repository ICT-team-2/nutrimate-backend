package com.nutrimate.nutrimatebackend.controller.dmchat;

import com.nutrimate.nutrimatebackend.model.dmchat.DMChatRoomDto;
import com.nutrimate.nutrimatebackend.service.dmchat.DMChatRoomService;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dm/room")
@Log4j2
public class DMChatRoomController {
	
	DMChatRoomService service;
	
	public DMChatRoomController(DMChatRoomService service) {
		this.service = service;
	}
	
	@ExceptionHandler(RuntimeException.class)
	public Map handleException(Exception e) {
		log.error(e.getMessage(), e);
		return Map.of("error", e.getMessage(),
				"success", false);
	}
	
	//crud
	@PostMapping("/private")
	public Map<String, Object> createChatRoomPrivate(@RequestBody DMChatRoomDto dto) throws Exception {
		service.createChatRoomPrivate(dto);
		return Map.of("success", true,
				"message", "Chatroom created");
	}
	
	@PostMapping("/group")
	public Map<String, Object> createChatRoomGroup(@RequestBody DMChatRoomDto dto) throws Exception {
		service.createChatRoomGroup(dto);
		return Map.of("success", true,
				"message", "Chatroom created");
	}
	
	@PutMapping("/name")
	public Map<String, Object> updateChatRoomName(@RequestBody DMChatRoomDto dto) throws Exception {
		try {
			service.updateChatRoomName(dto);
		} catch (Exception e) {
			throw new RuntimeException("Chatroom name update failed");
		}
		return Map.of("success", true,
				"message", "Chatroom name updated");
	}
	
	@DeleteMapping()
	public Map<String, Object> deleteChatRoom(int chatroomId) throws Exception {
		try {
			service.deleteChatRoom(chatroomId);
		} catch (Exception e) {
			throw new RuntimeException("Chatroom deletion failed");
		}
		return Map.of("success", true,
				"message", "Chatroom deleted");
	}
	
	@GetMapping("/list")
	public List<DMChatRoomDto> findChatRoomsByUserId(int userId) throws Exception {
		try {
			return service.findChatRoomsByUserId(userId);
		} catch (Exception e) {
			throw new RuntimeException("Chatroom retrieval failed");
		}
	}
	
}
