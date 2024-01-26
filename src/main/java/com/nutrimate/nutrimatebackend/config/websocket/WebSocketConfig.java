package com.nutrimate.nutrimatebackend.config.websocket;

import com.nutrimate.nutrimatebackend.controller.challenge.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
	
	
	private final WebSocketServer webSocketServer;
	@Autowired
	public WebSocketConfig(WebSocketServer webSocketServer) {
		this.webSocketServer = webSocketServer;
	}
	//클라이언트 접속을 위한 엔드 포인트 설정
	//엔드 포인트 - http://localhost:9999/websocket/socket에서 /socket부분을 의미함
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(webSocketServer, "/ws/chat")
				.setAllowedOrigins("*");
		
	}
	
	
}
