##
In this blog, I would :
1. Create a Hello world Java server
2. Use NASA APOD API to show Pictures.
3. User Joke.api to tell you jokes
4. Integrate cleverchatbot for funny conversations.

This blog is to show how people can work with APIs using Java, and create a java server 
from scratch and connect it to frontend.


1. First we create a Hello world Java server.

The code is in file "SimpleHttpServer.java"

Now, a small discussion for every module used in this code,
1. IOException: 
This module is used to handle Input/Output (IO) exceptions. In the context of the server, 
IO operations like reading from or writing to streams can throw IOExceptions. It's important 
to catch and handle these exceptions to ensure proper error handling.

2. PrintWriter:
This module provides the capability to print formatted representations of objects to a 
text-output stream. In the server code, PrintWriter is used to write responses back to the 
client. It simplifies the process of sending text-based data over the output stream.

3. ServerSocket:
This module provides a mechanism to listen for incoming connections on a specific port. 
In the server code, ServerSocket is used to create and listen on a socket, waiting for 
clients to establish connections. It plays a crucial role in setting up the server to accept 
incoming requests.
    
4. Socket:
This module represents a socket, which is an endpoint for sending or receiving data across 
a computer network. In the server code, Socket is used to accept connections from clients. 
Once a connection is established, the server can communicate with the client 
through the socket.

5. Scanner:
This module is used for parsing primitive types and strings using regular expressions. 
In the server code, Scanner is used to read the incoming data from the client. 
It simplifies the process of reading lines from the input stream, making it easier to 
handle client requests.

* You can see the requests getting logged in the terminal. 
Now we can only send "GET" requests from the browser. 


2. Now we extend the server, to display pictures from the NASA API.

Code is in "NasaADOP.java"

Now, a small discussion for other module used in this code,

* URL: 
the URL class in Java provides a convenient and straightforward way to work with URLs, 
making it a valuable tool for tasks involving network resources and web services.

3. Integrate Joke.api to get jokes:


4. Now, lets try running 2 (you can run more) servers on the same port and make them talk to each other.
Code is in:
Server1.java and Server2.java

The end!

References:
1. ![java.net.URL](https://www.baeldung.com/java-url)
2. ![java.net](https://docs.oracle.com/javase/8/docs/api/java/net/package-summary.html)



