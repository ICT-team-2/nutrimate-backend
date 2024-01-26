package com.nutrimate.nutrimatebackend.controller.challenge;

import com.nutrimate.nutrimatebackend.model.challenge.ChallengeChatDto;
import com.nutrimate.nutrimatebackend.service.challenge.ChallengeChatService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RestController
@RequestMapping("/challenge")
public class ChallengeController {
	
	private final ChallengeChatService challengeService;
	
	public ChallengeController(ChallengeChatService challengeService) {
		this.challengeService = challengeService;
	}

	//이전의 모든 메세지를 가져오기
	@GetMapping("/chat/prev")
	public List<ChallengeChatDto> getAllPrevMessage(ChallengeChatDto dto) {
		return challengeService.getAllPrevMessage(dto);
	}
	
	
}
