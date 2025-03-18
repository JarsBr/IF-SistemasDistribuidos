import java.net.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class TimeServerUDP {
    public static void main(String[] args) {
        int port = 9876;
        DatagramSocket socket = null;

        try {
            socket = new DatagramSocket(port);
            byte[] receiveBuffer = new byte[1024];
            System.out.println("Servidor UDP pronto para receber...");

            while (true) {
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                socket.receive(receivePacket);
                String receivedRegion = new String(receivePacket.getData(), 0, receivePacket.getLength()).trim();

                String timeResponse = getTimeForRegion(receivedRegion);
                byte[] sendBuffer = timeResponse.getBytes();

                InetAddress clientAddress = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();
                DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, clientAddress, clientPort);
                socket.send(sendPacket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (socket != null && !socket.isClosed()) {
                socket.close();
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