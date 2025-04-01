package br.edu.ifogoiano.WebSocketTemperatura;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.List;
import java.util.Random;

@Controller
public class TemperatureController {

    private final SimpMessagingTemplate messagingTemplate;

    @Value("${openweathermap.api.key}")
    private String apiKey;

    public TemperatureController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Scheduled(fixedRate = 5000)
    public void sendTemperatureUpdate() {
        // Código para chamar a API e obter a temperatura
        String location = "London"; // Exemplo fixo para testar
        double temperature = 22.5; // Exemplo fixo para testar

        TemperatureData data = new TemperatureData(location, temperature);
        messagingTemplate.convertAndSend("/topic/temperature", data);
    }
}

