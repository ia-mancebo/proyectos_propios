package filosofos_teatro;

import java.io.IOException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    public static void main(String[] args) {
        //Creamos un array de Locks con 3 ordenadores
        Lock[] ordenadores = new Lock[3];
        //Creamos un array de Locks con 3 teclados
        Lock[] teclados = new Lock[3];
        //Creamos un array de 5 filosofos
        Filosofo[] filosofos = new Filosofo[5];

        //Esta parte es antigua y ha sido movida a MainTeatro
        /*try {
            Teatro teatro = new Teatro(50, 9000);
            teatro.start();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        for (int i = 0; i < ordenadores.length; i++) {
            //Rellenamos los arrays de Locks de ordenadores y teclados
            ordenadores[i] = new ReentrantLock();
            teclados[i] = new ReentrantLock();
        }

        for (int i = 0; i < filosofos.length; i++) {
            try {
                //Rellenamos cada filosofo con el array de ordenadores, el array de teclados, un nombre y el limite de entradas que puede comprar en total
                filosofos[i] = new Filosofo(ordenadores, teclados, String.valueOf(i + 1), 20);
                //Iniciamos el hilo
                filosofos[i].start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
