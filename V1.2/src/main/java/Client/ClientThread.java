package Client;

import Server.ConnectionClient;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientThread implements Runnable {
    private final Socket socket;
    private final Thread thread;
    private ConnectionClient connection;
    private boolean flag = true;

    public ClientThread(Socket socket) {
        this.socket = socket;
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        System.out.println("Установлено соединение с сервером " + socket.getLocalSocketAddress());
        ClientLogger.logger.info("Установлено соединение с сервером " + socket.getLocalSocketAddress());
        try {
            connection = new ConnectionClient(socket);
            Scanner scanner = new Scanner(System.in);
            handShakeClient(connection, scanner);
            mainClientLoop(connection, scanner);
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public void handShakeClient(ConnectionClient connection, Scanner scanner) throws IOException {
        String msg;
        while (true) {
            msg = connection.receive();
            System.out.println(msg);
            ClientLogger.logger.info(msg);
            msg = scanner.nextLine();
            connection.send(msg);
            msg = connection.receive();
            System.out.println(msg);
            ClientLogger.logger.info(msg);
            break;
        }
    }

    public void mainClientLoop(ConnectionClient connection, Scanner scanner) throws IOException, InterruptedException {
        Thread readThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    String line;
                    try {
                        while ((line = connection.receive()) != null) {
                            if (!line.equals("/exit")) {
                                System.out.println(line);
                                ClientLogger.logger.info(line);
                            } else break;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        });

        Thread writeThread = new Thread(new Runnable() {
            @Override
            public void run() {
                String msg;
                while (true) {
                    while (flag) {
                        msg = scanner.nextLine();
                        try {
                            if (msg.equals("/exit")) {
                                connection.send(msg);
                                ClientLogger.logger.info(msg);
                                flag = false;
                                //  connection.close();
                                break;
                            } else {
                                connection.send(msg);
                                ClientLogger.logger.info(msg);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                }
            }
        });
        readThread.start();
        writeThread.start();
    }
}