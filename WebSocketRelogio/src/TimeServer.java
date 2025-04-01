import java.io.IOException;
import java.net.InetSocketAddress;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.Scanner;
import java.net.URI;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.simple.SimpleLogger;
import org.slf4j.simple.SimpleLoggerFactory;

import java.net.URI;
import java.util.logging.Logger;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.java_websocket.server.WebSocketServer;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.WebSocket;


import java.net.InetSocketAddress;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.Scanner;
import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.handshake.ServerHandshake;
import org.java_websocket.server.WebSocketServer;
import java.util.logging.Logger;

public class TimeServer extends WebSocketServer {
    private static final Logger logger = Logger.getLogger(TimeServer.class.getName());

    public TimeServer(int port) {
        super(new InetSocketAddress(port));
        logger.info("Servidor WebSocket iniciado na porta " + port);
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        logger.info("Nova conexão de: " + conn.getRemoteSocketAddress());
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        try {
            ZonedDateTime time = ZonedDateTime.now(ZoneId.of(message));
            String response = time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z"));
            conn.send(response);
            logger.info("Hora enviada para o fuso horário: " + message);
        } catch (Exception e) {
            conn.send("Fuso horário inválido");
            logger.info("Fuso horário inválido recebido.");
        } finally {
            conn.close();
        }
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        logger.info("Conexão fechada de: " + conn.getRemoteSocketAddress());
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        logger.info("Erro no WebSocket.");
    }

    @Override
    public void onStart() {
        logger.info("Servidor WebSocket pronto para aceitar conexões.");
    }

    public static void main(String[] args) {
        int port = 8080;
        TimeServer server = new TimeServer(port);
        server.start();
        logger.info("Servidor WebSocket iniciado na porta " + port);

        // Cliente
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o fuso horário: Europe/London - Asia/Tokyo - Australia/Sydney - Africa/Nairobi ");
        String timezone = scanner.nextLine();
        scanner.close();

        try {
            TimeClient client = new TimeClient("ws://localhost:" + port, timezone);
            client.connectBlocking();
        } catch (Exception e) {
            logger.info("Erro ao conectar com o cliente.");
        }
    }
}
