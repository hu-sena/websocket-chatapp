package com.websocket.chatapp.config;

import com.websocket.chatapp.model.ChatMessage;
import com.websocket.chatapp.model.MessageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketEventListener {

    private final SimpMessageSendingOperations messageTemplate;
    @EventListener
    public void handleWebSocketDisconnectEventListener(SessionDisconnectEvent event) {
        // when user disconnect from the app
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        // retrieve username from the session
        String username = (String) headerAccessor.getSessionAttributes().get("username");

        if(username != null) {
            log.info("User disconnected: {}", username);
            var chatMessage = ChatMessage.builder()
                    .type(MessageType.LEAVE)
                    .sender(username)
                    .build();

            // it will send message that the user has left the chat
            messageTemplate.convertAndSend("/chatroom/public", chatMessage);
        }
    }
}
