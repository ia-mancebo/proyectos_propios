package chatbox;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor /*extends ServerSocket*/ implements Runnable {

    private int port;

    public Servidor(int port) throws IOException {
        this.port = port;
    }

    @Override
    public void run() {
        try {
            ServidorEntrada servidorEntrada = new ServidorEntrada((port));
            Thread t1 = new Thread(servidorEntrada);
            t1.start();

            ServidorSalida servidorSalida = new ServidorSalida("localhost",(port+1));
            Thread t2 = new Thread(servidorSalida);
            t2.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
