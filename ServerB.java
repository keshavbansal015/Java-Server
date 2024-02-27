import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerB {
    public static void main(String[] args) {
        try {
            int port = 8080;
            Socket socket = new Socket("localhost", port);
            System.out.println("Server B connected to Server A.");

            // Server 2 sends a message
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            writer.println("Hello from Server B!");

            // Server 2 receives a response
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String response = reader.readLine();
            System.out.println("Server B received: " + response);

            socket.close();
            System.out.println("Connection with Server A closed.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
