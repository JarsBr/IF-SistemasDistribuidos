package com.example.WebSocketRelogioSping;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import java.net.URI;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
public class ClientePollQueueWebSocket extends WebSocketClient {

    private void sendNextTimezone() {
        if (!timezones.isEmpty()) {
            String timezone = timezones.poll();
            System.out.println("\nSolicitando horário para '" + timezone + "'...");
            send(timezone);
        } else {
            System.out.println("\nTodas as solicitações foram enviadas.");
            this.close(1000, "Fechamento solicitado pelo cliente");
        }
    }
    private static Queue<String> timezones = new
            LinkedList<>(Arrays.asList("America/Sao_Paulo", "Europe/Paris",
            "Europe/Moscow", "Asia/Shanghai", "Asia/Tokyo"));

    public ClientePollQueueWebSocket(URI serverUri) {
        super(serverUri);
    }

    public static void main(String[] args) throws Exception {
        ClientePollQueueWebSocket client = new ClientePollQueueWebSocket(
                new URI("ws://localhost:8080/relogio"));
        client.connect();
    }

    @Override
    public void onOpen(ServerHandshake handshake) {
        System.out.println("Conectado! Solicitando horários...");
        sendNextTimezone();
    }

    @Override
    public void onMessage(String message) {
        System.out.println("Horário recebido: " + message);
        sendNextTimezone();
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("Conexão fechada: " + reason);
    }

    @Override
    public void onError(Exception ex) {
        ex.printStackTrace();
    }


}