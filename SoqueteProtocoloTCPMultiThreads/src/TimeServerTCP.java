import java.io.*;
import java.net.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class TimeServerTCP {
    public static void main(String[] args) {
        int port = 9876;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Servidor TCP pronto para receber...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(new ClientHandler(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ClientHandler implements Runnable {
    private Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            String region = in.readLine();
            String timeResponse = getTimeForRegion(region);
            out.println(timeResponse);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static String getTimeForRegion(String region) {
        try {
            ZonedDateTime now = ZonedDateTime.now(ZoneId.of(region));
            return now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z"));
        } catch (Exception e) {
            return "Região inválida";
        }
    }
}
