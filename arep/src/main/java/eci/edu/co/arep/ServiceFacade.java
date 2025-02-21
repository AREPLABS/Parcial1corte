package eci.edu.co.arep;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;

public class ServiceFacade {
    public static ReflexCalculator calculator;

    public static final int PORT = 50000;
    private static final String USER_AGENT = "Mozilla/5.0";
    private static final String GET_URL = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=fb&apikey=Q1QZFVJQ21K7C6XM";
    private static URL obj = null;

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PORT);
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
        String inputLine,outputLine ;
        while ((inputLine =  in.readLine())  != null) {
            System.out.println("Recibí: " + inputLine);
            if (inputLine.split(" ")[0].startsWith("GET")) {
                String path = inputLine.split(" ")[1];
                if (path.startsWith("/calculadora")) {
                    outputLine = calculator.getReflexCalculator().paginaCalculadora();

                }else if(path.startsWith("/computar?comando")){
                    String comando = path.split("=")[1];
                    outputLine = calculator.getReflexCalculator().paginaOperaciones(comando);
                }
                else{
                    outputLine = calculator.getReflexCalculator().mensajePorDefecto();

                }
                out.println(outputLine);
            }
            if (!in.ready()) {
                break;
            }

        }

        out.close();
        in.close();

    }
    public void process() throws  IOException{
        obj = new URL(GET_URL);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);

        //The following invocation perform the connection implicitly before getting the code
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);

        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
            System.out.println(response.toString());
        } else {
            System.out.println("GET request not worked");
        }
        System.out.println("GET DONE");
    }


}