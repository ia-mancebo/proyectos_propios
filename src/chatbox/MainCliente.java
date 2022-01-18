package chatbox;

import java.io.IOException;

public class MainCliente {
    public static void main(String[] args) {
        try {
            Cliente cliente1 = new Cliente("localhost", 9000);
            Thread t1 = new Thread(cliente1);
            t1.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
