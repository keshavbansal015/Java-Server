import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerA {
    public static void main(String[] args) {
        try {
            int port = 8080;
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server A is waiting for connections at port " + port);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Server A connected to client: " + socket.getInetAddress());

                new Thread(() -> {
                    try {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        String message = reader.readLine();
                        System.out.println("Server A received: \n" + message);

                        // Simulate some processing time
                        Thread.sleep(2);

                        // Server 1 sends a response
                        PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
                        writer.println("Hello from Server A!");

                        socket.close();
                        System.out.println("Connection with client " + socket.getInetAddress() + " closed.");
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
