package com.nutrimate.nutrimatebackend.controller.challenge;

import com.nutrimate.nutrimatebackend.model.challenge.ChallengeChatDto;
import com.nutrimate.nutrimatebackend.service.challenge.ChallengeChatService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

//웹 소켓 서버
@Component
@Log4j2
public class WebSocketServer extends TextWebSocketHandler {
	
	//접속한 클라이언트를 저장하기 위한 속성(필드)
	//키는 웹소켓의 세션 아이디
	//값은 웹소켓 세션 객체
	private Map<String, WebSocketSession> clients = new HashMap<>();
	private ChallengeChatService service;
	
	//클라이언트와 연결이 될 때 마다 실행
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		UriComponents uriComponents = UriComponentsBuilder.fromUri(session.getUri()).build();
		
		// DTO 객체 생성 - userId, roomType 파라미터는 필수
		ChallengeChatDto userRoomDto = new ChallengeChatDto();
		userRoomDto.setUserId(Long.valueOf(uriComponents.getQueryParams().getFirst("userId")));
		userRoomDto.setRoomType(ChallengeChatDto.RoomType.valueOf(uriComponents.getQueryParams().getFirst("roomType")));
		
		// 클라이언트를 맵에 추가
		clients.put(session.getId(), session);
		log.info("New session established: " + session.getId() + ", User ID: " + userRoomDto.getUserId() + ", Room Name: " + userRoomDto.getRoomType());
		
		service.enterChatInUser(userRoomDto);
	}
	
	//클라이언트로부터 메시지를 받을때 마다 실행
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		log.info(session.getId() + "로부터 받은 메세지: "
				+ message.getPayload());
		//접속한 모든 클라이언트에게 session.getId()가 보낸 메세지 뿌려주기
		for (WebSocketSession client : clients.values()) {
			if (!session.getId().equals(client.getId())) {
				client.sendMessage(message);
			}
		}
	}
	//통신 상의 에러가 발생했을 때 실행
	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		log.info(session.getId() + "와의 통신 장애 발생: "
				+ exception.getMessage());
	}
	//연결이 끊겼을 때 실행
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		clients.remove(session.getId());
		log.info(session.getId() + "의 통신이 끊어졌습니다.");
	}
}
