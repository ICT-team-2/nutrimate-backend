package com.nutrimate.nutrimatebackend.config.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {   

  
  
         @Override
         public void configureMessageBroker(MessageBrokerRegistry registry) {
            registry.enableSimpleBroker("/sub");//구독 /sub/channel/{roomName}
            registry.setApplicationDestinationPrefixes("/pub");//보낸 메세지
         }
      
 
     
     
         public void registerStompEndpoints(StompEndpointRegistry registry) {

                registry.addEndpoint("/ws")
                        .setAllowedOriginPatterns("*");
                        

          
        }
         


}
