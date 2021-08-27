package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(Config.PORT))
        {
            while (true) {
                Socket clientSocket = serverSocket.accept();
               new ClientServerTread(clientSocket);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
