import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.simple.SimpleLogger;
import org.slf4j.simple.SimpleLoggerFactory;

import java.net.URI;
import java.util.logging.Logger;

class TimeClient extends WebSocketClient {
    static Logger LoggerFactory;
    private static final Logger logger = Logger.getLogger(String.valueOf(TimeClient.class));
    private final String timezone;

    public TimeClient(String serverUri, String timezone) {
        super(URI.create(serverUri));
        this.timezone = timezone;
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        send(timezone);
        logger.info("Sent timezone request: " + timezone);
    }

    @Override
    public void onMessage(String message) {
        System.out.println("Server response: " + message);
        close();
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        logger.info("Connection closed");
    }

    @Override
    public void onError(Exception ex) {
        logger.info("Client error: ");
    }
}