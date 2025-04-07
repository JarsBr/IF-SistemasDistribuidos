package com.example.WebSocketRelogioSping;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    private final RelogioWebSocketHandler handler;
    public WebSocketConfig(RelogioWebSocketHandler handler) {
        this.handler = handler;
    }
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(handler, "/relogio").setAllowedOrigins("*");
    }
}