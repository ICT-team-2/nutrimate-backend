package com.nutrimate.nutrimatebackend.controller.challenge;

import com.nutrimate.nutrimatebackend.model.challenge.ChallengeChatDto;
import com.nutrimate.nutrimatebackend.service.challenge.ChallengeChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	
	//채팅 참여여부 확인
	@PostMapping("/chat/member")
	public ResponseEntity<Map<String, Object>> findAccountChallenge(ChallengeChatDto dto) {
		Map<String, Object> challengeChatMemeber = new HashMap<>();
		//채팅에 참여해 본적이 있는지 확인
		int count = challengeService.countChatMember(dto);
		if (count == 1) {
			dto = challengeService.selectChatMember(dto);
			challengeChatMemeber.put("userId", dto.getUserId());
			challengeChatMemeber.put("chatRoomId", dto.getChatroomId());
			challengeChatMemeber.put("challengeNick", dto.getChallengeNick());
			challengeChatMemeber.put("memberOk", 1);
			return ResponseEntity.ok(challengeChatMemeber);
		} else {
			count = challengeService.selectChallenge(dto);//다른 채팅이라도 챌린지 참여한적 있는 지 확인
			if (count == 1) {
				int affect = challengeService.createAccountChat(dto);
				if (affect == 1) {
					
					dto = challengeService.selectChatMember(dto);
					challengeChatMemeber.put("userId", dto.getUserId());
					challengeChatMemeber.put("chatRoomId", dto.getChatroomId());
					challengeChatMemeber.put("challengeNick", dto.getChallengeNick());
					challengeChatMemeber.put("memberOk", 1);
					return ResponseEntity.ok(challengeChatMemeber);
				} else {
					challengeChatMemeber.put("memberOk", -1);//채팅 참여 실패 알람 띄움
					return ResponseEntity.ok(challengeChatMemeber);
					
				}
			}
			challengeChatMemeber.put("memberOk", 0);//챌린지 참여한적 아예 x 닉네임 입력 란으로 이동
			return ResponseEntity.ok(challengeChatMemeber);
		}
		
		
	}
	
	//챌린지 계정 만들기--계정있는 지 확인한
	@PostMapping("/account")
	public ResponseEntity<Map<String, Object>> createAccountChallenge(@RequestBody ChallengeChatDto dto) {
		Map<String, Object> challengeChatMemeber = new HashMap<>();
		//챌린지 중복 확인
		int count = challengeService.selectchallengeAccount(dto);
		if (count == 0) {
			int affect = challengeService.createAccountChallenge(dto);
			if (affect == 1) {
				dto = challengeService.selectChatMember(dto);
				challengeChatMemeber.put("userId", dto.getUserId());
				challengeChatMemeber.put("chatRoomId", dto.getChatroomId());
				challengeChatMemeber.put("challengeNick", dto.getChallengeNick());
				challengeChatMemeber.put("memberOk", 1);
				return ResponseEntity.ok(challengeChatMemeber);
			} else {
				challengeChatMemeber.put("memberNotOk", "계정을 만드는 것을 실패했습니다.");
				return ResponseEntity.ok(challengeChatMemeber);
			}
			
		}
		challengeChatMemeber.put("memberDupl", "중복된 닉네임이 있습니다.");
		return ResponseEntity.ok(challengeChatMemeber);
		
	}
	
	
	//이전의 모든 메세지를 가져오기
	@GetMapping("/chat/prev")
	public List<ChallengeChatDto> getAllPrevMessage(ChallengeChatDto dto) {
		List<ChallengeChatDto> list = challengeService.getAllPrevMessage(dto);
		log.info("getAllPrevMessage : " + list);
		return list;
	}
	
	
	//메세지 보내주기 및 메세지 저장
	@MessageMapping("/chat/{roomType}")
	public void sendMessage(@DestinationVariable String roomType, ChallengeChatDto message) {
		log.info("sendMessage : " + message);
		simpMessageSendingOperations.convertAndSend("/sub/channel/" + roomType, message);
		log.info("sendMessage : " + message);
		//message.setUserId(challengeService.chatIdSave(message));
		challengeService.chatRoomSave(message);
	}
	
	
	//챌린지 성공 기록하기
	@GetMapping("/success/record")
	public Map challengeSuccessRecord(@ModelAttribute ChallengeChatDto dto) {
		Map map = new HashMap();
		int count = challengeService.countChallengeSuccess(dto);
		if (count == 0) {
			int affect = challengeService.saveChallengeSuccess(dto);
			if (affect == 1) {
				map.put("SUCCESS", 1);
			} else {
				map.put("SUCCESSNOT", 1);
			}
		}
		if (count == 1) {
			map.put("SUCCESS", 0);//이미 챌린지에 참여했습니다.
		}
		return map;
	}
	
	//챌린지 등수
	@GetMapping("/success")
	public List<ChallengeChatDto> challengeSuccessList(ChallengeChatDto dto) {
		return challengeService.getChallengeSuccessList(dto);
	}
	
}
