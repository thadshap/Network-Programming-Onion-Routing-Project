import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class Client implements Runnable{
    public static final int BUFFER_SIZE = 576;
    private final int clientPort;
    private final int portTo;
    private String message;
    private AESSecurityCap node1Crypt;
    private AESSecurityCap node2Crypt;
    private AESSecurityCap node3Crypt;

    public Client(int clientPort,int portTo,AESSecurityCap node1Crypt,AESSecurityCap node2Crypt,AESSecurityCap node3Crypt,String message) {
        this.clientPort = clientPort;
        this.portTo = portTo;
        this.node1Crypt = node1Crypt;
        this.node2Crypt = node2Crypt;
        this.node3Crypt = node3Crypt;
        this.message = message;
    }

    @Override
    public void run() {

        try (DatagramSocket clientSocket = new DatagramSocket(clientPort)) {
            System.out.println("CLIENT: sends " + message);
            String enc1 = node3Crypt.encrypt(message);
            String enc2= node2Crypt.encrypt(enc1);
            String enc3 = node1Crypt.encrypt(enc2);

            String wrappedMessage = enc3;

            DatagramPacket datagramPacketSendPortTo = new DatagramPacket(
                    wrappedMessage.getBytes(),
                    wrappedMessage.length(),
                    InetAddress.getLocalHost(),
                    portTo
            );
            //System.out.println(new String(datagramPacketSendPortTo.getData(), StandardCharsets.UTF_8));
            clientSocket.send(datagramPacketSendPortTo);

            byte[] buffer = new byte[BUFFER_SIZE];

            DatagramPacket datagramPacketReceive = new DatagramPacket(buffer, 0, buffer.length);
            clientSocket.receive(datagramPacketReceive);

            message = new String(datagramPacketReceive.getData(), StandardCharsets.UTF_8).trim();

            String dec1 = node1Crypt.decrypt(message);
            String dec2= node2Crypt.decrypt(dec1);
            String dec3 = node3Crypt.decrypt(dec2);

            System.out.println("CLIENT: received " + dec3);

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
