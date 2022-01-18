package chatbox;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorEntrada extends ServerSocket implements Runnable {

    private ObjectInputStream flujo_entrada;

    public ServidorEntrada(int port) throws IOException {
        super(port);
    }

    @Override
    public void run() {

        while (true) {
            try {
                Socket cliente = this.accept();
                flujo_entrada = new ObjectInputStream(cliente.getInputStream());
                String mensaje = (String) flujo_entrada.readObject();
                System.out.println(mensaje);
                flujo_entrada.close();
                cliente.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }

    }
}