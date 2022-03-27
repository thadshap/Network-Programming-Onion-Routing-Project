import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        int serverPort = 50000;
        int node1Port = 50001;
        int node2Port = 50002;
        int node3Port = 50003;
        int clientPort = 50004;

        Client client = new Client(clientPort,node1Port,"HEI");
        Node node1 = new Node(node1Port,node2Port);
        Node node2 = new Node(node2Port,node3Port);
        Node node3 = new Node(node3Port,serverPort);
        Server server = new Server(serverPort);

        ExecutorService executorServiceClient = Executors.newFixedThreadPool(1);
        ExecutorService executorServiceNode1 = Executors.newFixedThreadPool(1);
        ExecutorService executorServiceNode2 = Executors.newFixedThreadPool(1);
        ExecutorService executorServiceNode3 = Executors.newFixedThreadPool(1);
        ExecutorService executorServiceServer = Executors.newFixedThreadPool(1);
        executorServiceClient.submit(client);
        executorServiceNode1.submit(node1);
        executorServiceNode2.submit(node2);
        executorServiceNode3.submit(node3);
        executorServiceServer.submit(server);

    }
}
