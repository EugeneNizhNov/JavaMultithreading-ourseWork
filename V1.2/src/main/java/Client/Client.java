package Client;

import Server.Config;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

public class Client {
    public static void main(String[] args) throws IOException {
        try {
            Socket clientSocket = new Socket(Config.HOST, Config.PORT);
            new ClientThread(clientSocket);
            } catch (SocketException ex) {
            System.out.println("ergregrgreger");
        }
         }
}
