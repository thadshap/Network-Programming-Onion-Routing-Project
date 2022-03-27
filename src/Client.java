import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class Client implements Runnable{

    private final int clientPort;
    private final int portTo;
    private final String message;

    public Client(int clientPort, int portTo, String message) {
        this.clientPort = clientPort;
        this.portTo = portTo;
        this.message = message;
    }

    @Override
    public void run() {

        try (DatagramSocket clientSocket = new DatagramSocket(clientPort)) {

            String wrappedMessage = message;

            DatagramPacket datagramPacketSendPortTo = new DatagramPacket(
                    wrappedMessage.getBytes(),
                    wrappedMessage.length(),
                    InetAddress.getLocalHost(),
                    portTo
            );
            System.out.println(new String(datagramPacketSendPortTo.getData(), StandardCharsets.UTF_8));
            clientSocket.send(datagramPacketSendPortTo);

            byte[] buffer = new byte[5];

            DatagramPacket datagramPacketReceive = new DatagramPacket(buffer, 0, buffer.length);
            clientSocket.receive(datagramPacketReceive);
            System.out.println("CLIENT:" + new String(datagramPacketReceive.getData(), StandardCharsets.UTF_8));
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
