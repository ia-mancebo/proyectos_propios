package chatbox;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Cliente /*extends Socket*/ implements Runnable {

    private String host;
    private int port;

    public Cliente(String host, int port) throws IOException {
        /*super(host, port);*/
        this.host = host;
        this.port = port;
    }

    @Override
    public void run() {
        try {
            ClienteEntrada clienteEntrada = new ClienteEntrada(this.port+1);
            Thread hiloEntrada = new Thread(clienteEntrada);
            hiloEntrada.start();

            ClienteSalida clienteSalida = new ClienteSalida(this.host, this.port);
            Thread hiloSalida = new Thread(clienteSalida);
            hiloSalida.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}