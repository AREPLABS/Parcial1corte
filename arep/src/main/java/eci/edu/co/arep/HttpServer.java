package eci.edu.co.arep;

import java.net.*;
import java.io.*;

public class HttpServer {
    public final int PORT = 36000;
  public static void main(String[] args) throws IOException {

   ServerSocket serverSocket = null;
        try { 
            serverSocket = new ServerSocket(36000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 36000.");
        }

        Socket clientSocket = null;
        while(true) {
            try {
                System.out.println("Listo para recibir ...");
                clientSocket = serverSocket.accept();
                handreRequest(clientSocket);
            } catch (IOException e) {
                System.err.println("Accept failed.");
            }
        }
  }

  public static void handreRequest(Socket clientSocket) throws IOException{

    PrintWriter out = new PrintWriter(
                          clientSocket.getOutputStream(), true);
    BufferedReader in = new BufferedReader(
                          new InputStreamReader(clientSocket.getInputStream()));
    String inputLine, outputLine;
    while ((inputLine = in.readLine()) != null) {
       System.out.println("Recibí: " + inputLine);
       if (!in.ready()) {break; }
    }
      outputLine = "HTTP/1.1 200 OK\r\n"
              + "Content-Type: text/html\r\n"
              + "\r\n"
              + "<!DOCTYPE html>\n"
              + "<html>\n"
              + "<head>\n"
              + "<meta charset=\"UTF-8\">\n"
              + "<title>Title of the document</title>\n"
              + "</head>\n"
              + "<body>\n"
              + "<h1>Mi propio mensaje</h1>\n"
              + "</body>\n"
              + "</html>\n";
      out.println(outputLine);
     out.close(); 
     in.close(); 

  }

}
