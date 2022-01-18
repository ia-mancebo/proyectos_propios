package filosofos_teatro;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Teatro extends Thread {

    ServerSocket servidor;
    int[] numeroEntrada;
    int index;
    String textoConf;

    public Teatro(int numeroEntrada, int port) throws IOException {
        //Inicio el server
        servidor = new ServerSocket(port);
        this.numeroEntrada = new int[numeroEntrada];
        this.index = 0;
        //Texto que se usará para saber si el filosofo quiere comprar un ticket
        //Esto esta explicado en su totalidad en el README
        this.textoConf = "Una entrada, por favor.";
    }

    @Override
    public void run() {
        //Entro en el bucle si el index es menor que la longitud del array numeroEntrada
        while (index < numeroEntrada.length) {
            try {
                //Meto en una variable el Socket que me devuelve accept
                Socket cliente = servidor.accept();
                //Creamos un flujo de entrada
                ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());
                //Compruebo si el mensaje se refiere a la peticion de un ticket o a la comprobacion de que el servidor sigue abierto
                //Explicado en detalle en el README
                if (textoConf.compareTo((String) entrada.readObject()) == 0) {
                    //El filosofo ha enviado una peticion de ticket
                    //Sincronizamos esta parte para no vender accidentalmente entradas de mas
                    synchronized (this) {
                        //Volvemos a comprobar que el indice sigue siendo menor que la longitud del array por si acaso
                        //Esto arregla el caso hipotetico en el que varios filosofos pueden llegar a este codigo solo habiendo una entrada mas por vender
                        //Explicado en el README
                        if (index < numeroEntrada.length) {
                            //Abrimos un canal de salida
                            ObjectOutputStream salida = new ObjectOutputStream(cliente.getOutputStream());
                            //Escribimos el numero de entrada y algo de texto
                            salida.writeObject("la entrada numero: " + (index + 1));
                            //Leemos lo que nos ha mandado el filosofo, que es su nombre y algo de texto
                            System.out.println((String) entrada.readObject());
                            //Aumentamos el indice en uno para indicar que hemos vendido una
                            index++;
                            //Imprimimos cuantas entradas faltan
                            System.out.println("Quedan " + (numeroEntrada.length - index) + " entradas");
                            //Cerramos el flujo de salida
                            salida.close();
                        } else {
                            //Esto envia un mensaje al cliente diciendo "No" para notificarle de que no quedan mas entradas
                            ObjectOutputStream salida = new ObjectOutputStream(cliente.getOutputStream());
                            salida.writeObject("No");
                            salida.close();
                        }
                        //Esperamos 1 segundo hasta la siguiente peticion
                        Thread.sleep(1000);
                    }
                }
                //Cerramos los flujos de entrada y el Socket que hemos guardado anteriormente
                entrada.close();
                cliente.close();
            } catch (IOException | ClassNotFoundException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            //El servidor se cierra una vez vendidas todas las entradas
            //Solo se puede llegar a esta parte del codigo cuando se sale del while
            servidor.close();
            System.out.println("Cierro la venta de entradas");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
