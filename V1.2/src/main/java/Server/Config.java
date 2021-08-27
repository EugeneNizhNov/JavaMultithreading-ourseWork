package Server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Config {
    private static final String CONGIG_FILE = "src/main/resources/config.properties";
    public static int PORT;
    public static String HOST;
    static {
        Properties config = new Properties();
        try {
            FileInputStream fis = new FileInputStream(CONGIG_FILE);
            config.load(fis);
            HOST = config.getProperty("host");
            PORT = Integer.parseInt(config.getProperty("port"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
