import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class Server implements Runnable {
    private final int nodePort;

    public Server(int nodePort) {
        this.nodePort = nodePort;
    }

    @Override
    public void run() {
        byte[] buffer = new byte[100];

        try (DatagramSocket serverSocket = new DatagramSocket(nodePort)) {
            DatagramPacket datagramPacketReceive = new DatagramPacket(buffer, 0, buffer.length);
            serverSocket.receive(datagramPacketReceive);
            String result = new String(datagramPacketReceive.getData(), StandardCharsets.UTF_8).trim();
            System.out.println("SERVER: " + result);
            //TODO Execute GET Request and claim Response
            String response = "Success";
            System.out.println("Reached server");
            DatagramPacket datagramPacketSendPortTo = new DatagramPacket(
                    response.getBytes(),
                    response.length(),
                    InetAddress.getLocalHost(),
                    datagramPacketReceive.getPort()
            );
            serverSocket.send(datagramPacketSendPortTo);
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}