import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.Scanner;

public class NasaADOP {
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

            // Fetch image URL from NASA API
            String[] imageUrlAndDescription = getImageUrlFromNasaApi();
            String embed = buildHtmlResponse(imageUrlAndDescription);
            StringBuilder response = new StringBuilder();

            response.append("HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n\r\n");
            response.append("<html><body><h2>Hello World!, this is today's APOD!</h2>");
            response.append(embed);
            response.append("</body></html>");
            out.println(response.toString());
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

    private static String[] getImageUrlFromNasaApi() {
        // Make an HTTP request to the NASA API to get the image URL
        // Replace this with your actual NASA API key and the correct API endpoint
        String apiKey = "DEMO_KEY";
        String apiUrl = "https://api.nasa.gov/planetary/apod?api_key=" + apiKey;
        String[] returnStrings = new String[3];
        try (Scanner scanner = new Scanner(new URL(apiUrl).openStream())) {
            StringBuilder response = new StringBuilder();
            while (scanner.hasNextLine()) {
                response.append(scanner.nextLine());
            }

            // Parse the JSON response to get the image / video URL
            // This is a simplified example, you may want to use a JSON
            // library for robust parsing
            String responseString = response.toString();
            String title = responseString.split("title\":\"")[1].split("\",")[0];
            String mediaLink = responseString.split("\"url\":\"")[1].split("\"}")[0];
            String explanation = responseString.split("explanation\":\"")[1].split("\",")[0];

            // System.out.println(title);
            // System.out.println(mediaLink);
            // System.out.println(explanation);
            if (mediaLink.length() > 2) {
                returnStrings[0] = title;
                returnStrings[1] = mediaLink;
                returnStrings[2] = explanation;
                // System.out.println(returnStrings[2]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnStrings;
    }

    private static String buildHtmlResponse(String[] imageUrlAndDescription) {
        StringBuilder embed = new StringBuilder();
        embed.append("<p><h3>" + imageUrlAndDescription[0] + "</h3></p>");
        embed.append("<p><img src=\"" + imageUrlAndDescription[1] + "\"></p>");
        embed.append("<p>" + imageUrlAndDescription[2] + "</p>");

        // System.out.println(embed.toString());
        return embed.toString();
    }
}
