package Client;

import Server.Config;

import java.io.IOException;
import java.net.Socket;

public class Client2 {
    public static void main(String[] args) {
        try {
            Socket clientSocket = new Socket(Config.HOST, Config.PORT);
            ClientThread clientThread = new ClientThread(clientSocket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
