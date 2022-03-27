import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

public class Node implements Runnable {
    private final int portCurrent;
    private final int portTo;
    private AESSecurityCap crypt;

    public Node(int port, int portTo, AESSecurityCap crypt) {
        this.portCurrent = port;
        this.portTo = portTo;
        this.crypt = crypt;
    }

    @Override
    public void run() {
        try (DatagramSocket nodeSocket = new DatagramSocket(portCurrent)) {
            byte[] buffer = new byte[100];

            DatagramPacket datagramPacketReceive = new DatagramPacket(buffer, 0, buffer.length);
            nodeSocket.receive(datagramPacketReceive);

            String message = new String(datagramPacketReceive.getData(), StandardCharsets.UTF_8).trim(); //TODO Decrypt
            System.out.println("Node with port nr. " + portCurrent + " received message " + message);
            String decryptedMessage = crypt.decrypt(message);

            DatagramPacket datagramPacketSendPortTo = new DatagramPacket(
                    decryptedMessage.getBytes(),
                    decryptedMessage.length(),
                    InetAddress.getLocalHost(),
                    portTo
            );
            nodeSocket.send(datagramPacketSendPortTo);

            buffer = new byte[100];
            DatagramPacket datagramPacketReceiveResponse = new DatagramPacket(buffer, 0, buffer.length);
            nodeSocket.receive(datagramPacketReceiveResponse);

            message = new String(datagramPacketReceiveResponse.getData(), StandardCharsets.UTF_8).trim(); //TODO Encrypt
            String encryptedMessage = crypt.encrypt(message);

            DatagramPacket datagramPacketSendPortFrom = new DatagramPacket(
                    encryptedMessage.getBytes(),
                    encryptedMessage.length(),
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
