package br.edu.ifogoiano.WebSocketTemperatura;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling  // Habilita o agendamento de tarefas
public class WebSocketTemperaturaApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebSocketTemperaturaApplication.class, args);
	}
}
