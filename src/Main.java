import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        // http link
        String message = "http://example.com";

        // ports to client, nodes and server
        int clientPort = 50000;
        int node1Port = 50001;
        int node2Port = 50002;
        int node3Port = 50003;
        int serverPort = 50004;

        // all ports get their own instance AESSecurityCap to be able to get their keys and do encryption and decryption
        AESSecurityCap node1Crypt = new AESSecurityCap();
        AESSecurityCap node2Crypt = new AESSecurityCap();
        AESSecurityCap node3Crypt = new AESSecurityCap();

        // makes a connection through keys like all the nodes can do encryption and decryption through each other
        node1Crypt.setReceiverPublicKey(node2Crypt.getPublickey());
        node2Crypt.setReceiverPublicKey(node3Crypt.getPublickey());
        node3Crypt.setReceiverPublicKey(node1Crypt.getPublickey());

        // created client, nodes and server
        Client client = new Client(clientPort,node1Port,node1Crypt,node2Crypt,node3Crypt,message);
        Node node1 = new Node(node1Port,node2Port, node1Crypt);
        Node node2 = new Node(node2Port,node3Port, node2Crypt);
        Node node3 = new Node(node3Port,serverPort, node3Crypt);
        Server server = new Server(serverPort);

        // create threads for client, nodes and threads
        ExecutorService executorServiceClient = Executors.newFixedThreadPool(1);
        ExecutorService executorServiceNode1 = Executors.newFixedThreadPool(1);
        ExecutorService executorServiceNode2 = Executors.newFixedThreadPool(1);
        ExecutorService executorServiceNode3 = Executors.newFixedThreadPool(1);
        ExecutorService executorServiceServer = Executors.newFixedThreadPool(1);

        // execute threads to do their tasks which depends on what client's, node' and server's tasks are
        executorServiceClient.submit(client);
        executorServiceNode1.submit(node1);
        executorServiceNode2.submit(node2);
        executorServiceNode3.submit(node3);
        executorServiceServer.submit(server);

        // kills the threads after they are done with their tasks
        executorServiceClient.shutdown();
        executorServiceNode1.shutdown();
        executorServiceNode2.shutdown();
        executorServiceNode3.shutdown();
        executorServiceServer.shutdown();
    }
}
