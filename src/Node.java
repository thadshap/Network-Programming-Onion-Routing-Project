import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

public class Node implements Runnable {
    private final int portCurrent;
    private final int portTo;

    public Node(int port, int portTo) {
        this.portCurrent = port;
        this.portTo = portTo;
    }

    @Override
    public void run() {
        try (DatagramSocket nodeSocket = new DatagramSocket(portCurrent)) {
            byte[] buffer = new byte[5];

            DatagramPacket datagramPacketReceive = new DatagramPacket(buffer, 0, buffer.length);

            nodeSocket.receive(datagramPacketReceive);

            String message = new String(datagramPacketReceive.getData(), StandardCharsets.UTF_8); //TODO Decrypt

            DatagramPacket datagramPacketSendPortTo = new DatagramPacket(
                    message.getBytes(),
                    message.length(),
                    InetAddress.getLocalHost(),
                    portTo
            );
            nodeSocket.send(datagramPacketSendPortTo);

            buffer = new byte[5];
            DatagramPacket datagramPacketReceiveResponse = new DatagramPacket(buffer, 0, buffer.length);
            nodeSocket.receive(datagramPacketReceiveResponse);

            message = new String(datagramPacketReceiveResponse.getData(), StandardCharsets.UTF_8); //TODO Encrypt

            DatagramPacket datagramPacketSendPortFrom = new DatagramPacket(
                    message.getBytes(),
                    message.length(),
                    InetAddress.getLocalHost(),
                    datagramPacketReceive.getPort()
            );
            nodeSocket.send(datagramPacketSendPortFrom);

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Timeout. Client is closing.");
        }
    }
}
