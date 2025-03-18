import java.io.*;
import java.net.*;
import java.util.Scanner;

public class TimeClientTCP {
    public static void main(String[] args) {
        String serverAddress = "localhost";
        int serverPort = 9876;

        try (Socket socket = new Socket(serverAddress, serverPort);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             Scanner scanner = new Scanner(System.in)) {

            System.out.print("Digite a região geográfica (ex: America/Sao_Paulo, Asia/Tokyo, Europe/London): ");
            String region = scanner.nextLine();
            out.println(region);

            String receivedTime = in.readLine();
            System.out.println("Hora recebida do servidor: " + receivedTime);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
