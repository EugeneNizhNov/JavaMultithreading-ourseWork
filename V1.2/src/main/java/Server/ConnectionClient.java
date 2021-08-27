package Server;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ConnectionClient implements Closeable {
    private final Socket socket;
    private final DataInputStream in;
    private final DataOutputStream out;

    public ConnectionClient(Socket socket) throws IOException {
        this.socket = socket;
        out = new DataOutputStream(socket.getOutputStream());
        in = new DataInputStream(socket.getInputStream());
    }
    public void send(String msg) throws IOException {
     //  synchronized (out) {
            out.writeUTF(msg);
            out.flush();
       // }
    }

    public String receive() throws IOException {
       // synchronized (in) {
            return in.readUTF();
       // }
    }

    @Override
    public void close() throws IOException {
        // in.close();
         out.close();
         socket.close();
    }
}
