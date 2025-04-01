package br.edu.ifogoiano.WebSocketTemperatura;

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
        // Configuração do broker para prefixo /topic
        registry.enableSimpleBroker("/topic");  // Gerencia os destinos de mensagens com prefixo /topic
        registry.setApplicationDestinationPrefixes("/app");  // Define o prefixo para as mensagens enviadas pela aplicação
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Criação do endpoint /ws, com fallback utilizando SockJS
        registry.addEndpoint("/ws").withSockJS();  // Permite que o cliente se conecte via WebSocket
    }
}