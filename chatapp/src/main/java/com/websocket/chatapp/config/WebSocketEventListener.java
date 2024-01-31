package com.websocket.chatapp.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketEventListener {
    @EventListener
    public void handleWebSocketDisconnectEventListener(SessionDisconnectEvent event) {
        // TODO: implement the logic when user disconnect from the app
    }
}
