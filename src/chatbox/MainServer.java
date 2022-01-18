package chatbox;

import java.io.IOException;

public class MainServer {
    public static void main(String[] args) {
        try {
            Servidor server = new Servidor(9000);
            Thread t1 = new Thread(server);
            t1.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
