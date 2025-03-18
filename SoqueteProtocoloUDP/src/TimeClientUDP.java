import java.net.*;
import java.util.Scanner;

public class TimeClientUDP {
    public static void main(String[] args) {
        String serverAddress = "localhost";
        int serverPort = 9876;
        DatagramSocket socket = null;

        try {
            socket = new DatagramSocket();
            Scanner scanner = new Scanner(System.in);
            System.out.print("Digite a região geográfica (ex: America/Sao_Paulo, Europe/London, Asia/Tokyo): ");
            String region = scanner.nextLine();
            byte[] sendBuffer = region.getBytes();

            InetAddress serverInetAddress = InetAddress.getByName(serverAddress);
            DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, serverInetAddress, serverPort);
            socket.send(sendPacket);

            byte[] receiveBuffer = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            socket.receive(receivePacket);

            String receivedTime = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("Hora recebida do servidor: " + receivedTime);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        }
    }
}

