package chatbox;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ServidorSalida extends Thread {

    private final String host;
    private final int port;
    Socket conexion;
    Scanner sc = new Scanner(System.in);
    private ObjectOutputStream flujo_salida;
    private String mensaje;

    public ServidorSalida(String host, int port) throws IOException {
        this.host = host;
        this.port = port;
    }

    @Override
    public void run() {
        while (true) {
            try {
                mensaje = sc.nextLine();
                conexion = new Socket(host, port);
                flujo_salida = new ObjectOutputStream(conexion.getOutputStream());
                flujo_salida.writeObject(mensaje);
                flujo_salida.close();
                conexion.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}

