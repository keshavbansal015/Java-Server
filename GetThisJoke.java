import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.net.URL;

public class GetThisJoke {
    public static void main(String[] args) {
        int port = 8080;

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

    private static void handleRequest(Socket clientSocket) {
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

            // Send an HTML response with a form
            String[] joke = makeApiRequest();
            StringBuilder outJoke = new StringBuilder();
            if (joke[1].length() > 1) {
                outJoke.append(joke[0]);
                outJoke.append("\n\r");
                outJoke.append(joke[1]);
            } else {
                outJoke.append(joke[0]);
            }
            String response = "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n\r\n" +
                    "<html><body>" +
                    "<h2>Hello World!, this is a server with Java backend!</h2>" +
                    "<form action=\"/submit\" method=\"get\">" +
                    "<input type=\"submit\" value=\"Get new joke!\">" +
                    "</form>" +
                    "<p id=\"apiResponse\">" + outJoke + "</p>" +
                    "</body></html>";
            out.println(response);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Close the client socket
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static String[] makeApiRequest() {
        // Simulate making an API request and return the response
        // String apiKey = "DEMO_KEY";
        String apiUrl = "https://v2.jokeapi.dev/joke/Any";
        // + apiKey;
        try (
                Scanner scanner = new Scanner(new URL(apiUrl).openStream())) {
            StringBuilder response = new StringBuilder();

            while (scanner.hasNextLine()) {
                response.append(scanner.nextLine());
            }

            String responseString = response.toString();
            if (responseString.contains("\"joke\"")) {
                String premise = responseString.split("\"joke\":")[1].split("\"flags\"")[0];
                String[] returnResponse = { premise, new String() };
                return returnResponse;
            } else {
                String premise = responseString.split("\"setup\":")[1].split("\"delivery\"")[0];
                String punchline = responseString.split("\"delivery\":")[1].split("\"flags\"")[0];
                return new String[] { premise, punchline };

            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
            ;
        }
        String[] empStrings = { "", "" };
        return empStrings;
    }
}
