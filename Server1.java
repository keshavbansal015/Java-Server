import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server1 {
    public static void main(String[] args) {
        try {
            int port = 8080;
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server 1 is waiting for connections...");

            Socket socket = serverSocket.accept();
            System.out.println("Connected to client: " + socket.getInetAddress());

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            new Thread(() -> {
                try {
                    while (true) {
                        // Server 1 receives a message
                        String receivedMessage = reader.readLine();
                        if (receivedMessage == null) {
                            break;
                        }
                        System.out.println("Received: " + receivedMessage);

                        // Server 1 sends a response
                        System.out.print("Server 1: ");
                        String response = getUserInput();
                        writer.println(response);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            while (true) {
                // Server 1 sends a message
                System.out.print("Server 1: ");
                String message = getUserInput();
                writer.println(message);

                // Server 1 receives a response
                String response = reader.readLine();
                System.out.println("Received: " + response);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getUserInput() {
        try {
            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
            return consoleReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
