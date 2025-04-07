package br.edu.ifogoiano.WebSocketTemperatura;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Controller
public class TemperatureController {

    private final SimpMessagingTemplate messagingTemplate;

    @Value("${openweathermap.api.key}")
    private String apiKey;

    public TemperatureController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @GetMapping("/sendTemperatures")
    public void sendTemperatureUpdates() {
        List<String> locations = Arrays.asList(
                "London", "Paris", "New York", "Tokyo", "Moscow",
                "Rio de Janeiro", "Beijing", "Sydney", "Berlin", "Madrid"
        );

        RestTemplate restTemplate = new RestTemplate();
        for (String location : locations) {
            String url = "http://api.openweathermap.org/data/2.5/weather?q=" + location +
                    "&units=metric&appid=" + apiKey;

            TemperatureResponse response = restTemplate.getForObject(url, TemperatureResponse.class);
            double temperature = response != null && response.getMain() != null ? response.getMain().getTemp() : 0;
            String description = response.getWeather().get(0).getDescription();
            int humidity = response.getMain().getHumidity();
            double windSpeed = response.getWind().getSpeed();

            TemperatureData data = new TemperatureData(location, temperature, description, humidity, windSpeed);
            messagingTemplate.convertAndSend("/topic/temperature", data);
        }
    }
}
