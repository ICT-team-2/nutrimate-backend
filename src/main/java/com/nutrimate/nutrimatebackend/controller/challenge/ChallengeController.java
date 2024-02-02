package com.nutrimate.nutrimatebackend.controller.challenge;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.nutrimate.nutrimatebackend.model.challenge.ChallengeChatDto;
import com.nutrimate.nutrimatebackend.service.challenge.ChallengeChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RestController
@RequestMapping("/challenge")
@RequiredArgsConstructor
@Slf4j
public class ChallengeController {
	
    @Autowired
	private final ChallengeChatService challengeService;
    @Autowired
    private final SimpMessageSendingOperations simpMessageSendingOperations;
    
    
	

	//이전의 모든 메세지를 가져오기
	@GetMapping("/chat/prev")
	public List<ChallengeChatDto> getAllPrevMessage(ChallengeChatDto dto) {
		return challengeService.getAllPrevMessage(dto);
	}
	
	@MessageMapping("/chat/{RoomType}")
    public void sendMessage(@DestinationVariable String RoomType,SimpMessageHeaderAccessor headerAccessor, ChallengeChatDto message) {
      simpMessageSendingOperations.convertAndSend("/sub/channel/"+RoomType, message);
     //ChallengeChatService.chatRoomSave(message);+ RoomType
    }
	
	
}
