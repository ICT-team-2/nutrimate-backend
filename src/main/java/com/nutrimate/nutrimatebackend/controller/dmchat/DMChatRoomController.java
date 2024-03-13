package com.nutrimate.nutrimatebackend.controller.dmchat;

import com.nutrimate.nutrimatebackend.model.dmchat.DMChatRoomDto;
import com.nutrimate.nutrimatebackend.service.dmchat.DMChatRoomService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
	public ResponseEntity<Map> handleException(Exception e) {
		log.error(e.getMessage(), e);
		return ResponseEntity.badRequest().body(
				Map.of("error", e.getMessage(),
						"success", false));
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
	
	@PutMapping(value = "/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public Map<String, Object> changeProfileImage(DMChatRoomDto dto, List<MultipartFile> profileImage) throws IOException {
		
		if (profileImage == null || profileImage.isEmpty()) {
			return Map.of("success", false,
					"message", "파일이 없습니다.");
		}
		//프로필 이미지 변경
		String fileName = service.updateChatRoomProfile(dto, profileImage);
		
		return Map.of("success", true,
				"message", "프로필 이미지가 변경되었습니다.",
				"userProfile", fileName);
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
	
	@GetMapping("/user/search")
	public List findUserListBySearchWord(String searchWord) throws Exception {
		try {
			return service.findUserListBySearchWord(searchWord);
		} catch (Exception e) {
			throw new RuntimeException("User search failed");
		}
	}
	
	@DeleteMapping("/user")
	public Map<String, Object> deleteMemberChatRoom(DMChatRoomDto dto) throws Exception {
		try {
			service.deleteMemberChatRoom(dto);
		} catch (Exception e) {
			throw new RuntimeException("Chatroom member deletion failed");
		}
		return Map.of("success", true,
				"message", "Chatroom member deleted");
	}
}
