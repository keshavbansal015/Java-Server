import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


public class SimpleHttpServer {
    public static void main(String[] args) {
        int port = 8080; // You can change this to any available port

        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server listening on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                handleRequest(clientSocket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleRequest(Socket clientSocket) throws IOException {
        // try-with-resources statement introduced in Java 7
        try (
                Scanner in = new Scanner(clientSocket.getInputStream());
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
            // Read the HTTP request from the client
            while (in.hasNextLine()) {
                String line = in.nextLine();
                System.out.println("Client request: " + line);

                // Break when an empty line is encountered, signaling the end of the request
                if (line.isEmpty()) {
                    break;
                }
            }

            // Send a simple HTTP response back to the client
            String response = "HTTP/1.1 200 OK\r\nContent-Type: text/plain\r\n\r\nHello World!, this is a server!";
            out.println(response);
        }
        // Close the client socket
        clientSocket.close();
    }
}

