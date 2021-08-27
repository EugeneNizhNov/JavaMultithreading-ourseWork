package Server;

import java.io.IOException;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ClientServerTread implements Runnable {
    private final Socket socket;
    private Thread thread;
    private static final String serverName = "Server: ";
    private static final Map<ConnectionClient, String> connectionMap = new ConcurrentHashMap<ConnectionClient, String>();
    ConnectionClient connection;

    public ClientServerTread(Socket socket) {
        this.socket = socket;
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        System.out.println(serverName + "Установлено соединение с клиентом " + socket.getLocalSocketAddress());
        LoggerServer.logger.info(serverName + "Установлено соединение с клиентом " + socket.getLocalSocketAddress());
        String userName;
        try {
            connection = new ConnectionClient(socket);
            userName = handShakeServer();
            mainServerLoop(userName);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public String handShakeServer() throws IOException {
        String name;
        connection.send(serverName + "Введите имя пользователя");
        while ((name = connection.receive()) != null) {
            System.out.println(serverName + "Пользователь " + name + " подключился");
            LoggerServer.logger.info(serverName + "Пользователь " + name + " подключился");
            connectionMap.put(connection, name);
            for (Map.Entry<ConnectionClient, String> entry : connectionMap.entrySet()) {
                entry.getKey().send(serverName + "Подключился новый пользователь " + name);
            }
            break;
        }
        return name;
    }

    public void mainServerLoop(String userName) throws IOException {

        while (true) {
            String line;
            while ((line = connection.receive()) != null) {
                if (!line.equals("/exit")) {
                    System.out.println(serverName + userName + ": " + line);
                    LoggerServer.logger.info(serverName + userName + ": " + line);
                    String broadCastMassage = serverName + userName + ": " + line;
                    broadCast(broadCastMassage, userName);
                } else {
                    connection.send("/exit");
                    userExit(userName);
                    break;
                }
            }
        }
    }

    public void broadCast(String broadCastMassage, String userName) throws IOException {
        for (Map.Entry<ConnectionClient, String> entry : connectionMap.entrySet()) {
            if (!entry.getValue().equals(userName))
                entry.getKey().send(broadCastMassage);
        }
    }

    public void userExit(String userName) throws IOException {
        connectionMap.remove(connection, userName);
        String broadCastMassage = "Пользователь " + userName + " вышел из чата";
        broadCast(broadCastMassage, userName);
    }
}
