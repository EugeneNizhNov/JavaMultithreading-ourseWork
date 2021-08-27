package Client;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class ClientLogger {
    public static Logger logger = Logger.getLogger("ServerLog");
    static {
        try(FileInputStream ins = new FileInputStream("src/main/java/Server/log.properties")) {
            LogManager.getLogManager().readConfiguration(ins);
            FileHandler fh = new FileHandler("src/main/java/Client/Client.log");
            logger.addHandler(fh);
            //  SimpleFormatter formatter = new SimpleFormatter();
            // fh.setFormatter(formatter);
            logger.setUseParentHandlers(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
