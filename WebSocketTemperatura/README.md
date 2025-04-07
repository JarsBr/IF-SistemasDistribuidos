# Monitoramento de Temperatura em Tempo Real

Para visualizar o monitoramento funcionando, acesse o arquivo `index.html` diretamente no navegador, usando `localhost`. Certifique-se que o servidor Spring Boot esteja em execução para que a conexão WebSocket funcione corretamente.
>http://localhost:8080/index.html

## Funcionalidade

- **Servidor (Spring Boot):**
    - **Integração com a API de Clima:**  
      Utiliza o `RestTemplate` para fazer requisições à API do OpenWeatherMap e obter dados climáticos (temperatura, umidade, descrição do clima e velocidade do vento) para 10 localizações pré-definidas.

    - **Agendamento de Tarefas:**  
      Com a anotação `@Scheduled(fixedRate = 5000)`, o servidor executa a tarefa de buscar e enviar os dados a cada 5 segundos, escolhendo aleatoriamente uma das localizações.

    - **Envio via WebSocket:**  
      Os dados obtidos são encapsulados em um objeto `TemperatureData` e enviados para o tópico `/topic/temperature` usando o `SimpMessagingTemplate`.

- **Cliente (HTML + JavaScript):**
    - **Conexão WebSocket:**  
      A página web se conecta ao endpoint `/ws` utilizando SockJS e STOMP.js.

    - **Recepção e Exibição dos Dados:**  
      Ao se inscrever no tópico `/topic/temperature`, o cliente recebe as mensagens enviadas pelo servidor e atualiza a interface exibindo as informações recebidas em tempo real.

## Fluxo da Aplicação

1. **Inicialização do Servidor:**  
   O Spring Boot configura o WebSocket e habilita o agendamento das tarefas para realizar chamadas à API do OpenWeatherMap a cada 5 segundos.

2. **Requisição à API:**  
   A cada execução agendada, o servidor seleciona uma localização aleatória dentre as 10 definidas e faz a requisição para obter os dados climáticos.

3. **Envio dos Dados:**  
   O servidor processa a resposta da API, encapsula os dados em um objeto `TemperatureData` e envia esses dados para o tópico `/topic/temperature`.

4. **Atualização do Cliente:**  
   O cliente web, que se conectou via WebSocket, recebe os dados e os exibe na tela, atualizando as informações em tempo real.
