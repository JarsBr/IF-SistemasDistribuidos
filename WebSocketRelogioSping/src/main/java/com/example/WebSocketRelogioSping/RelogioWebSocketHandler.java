package com.example.WebSocketRelogioSping;

import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.stereotype.Component;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
@Component
public class RelogioWebSocketHandler extends TextWebSocketHandler {
    @Override
    protected void handleTextMessage(WebSocketSession session,
                                     TextMessage message) throws Exception {
        String regiao = message.getPayload();
        ZonedDateTime horaAtual = ZonedDateTime.now(ZoneId.of(regiao));
        String resposta = horaAtual.format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss VV"));
        session.sendMessage(new TextMessage(resposta));
    }
}