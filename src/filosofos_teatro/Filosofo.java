package filosofos_teatro;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.locks.Lock;

public class Filosofo extends Thread {

    private Lock[] ordenadores;
    private Lock[] teclados;
    private String nombre;
    private int entradas;
    private Socket conexion;
    private Random randomizador;
    private int limiteEntradas;
    private int entradasConsecutivas;

    public Filosofo(Lock[] ordenadores, Lock[] teclados, String nombre) throws IOException {
        this.ordenadores = ordenadores;
        this.teclados = teclados;
        this.nombre = nombre;
        this.entradas = 0;
        this.randomizador = new Random(42);
        this.limiteEntradas = 20;
        this.entradasConsecutivas = 10;
    }

    //Este constructor esta creado en caso de que quieras cambiar la cantidad maxima de tickets que vas a comprar
    //Por defecto ese numero es 20, como especifica el enunciado
    public Filosofo(Lock[] ordenadores, Lock[] teclados, String nombre, int limiteEntradas) throws IOException {
        this.ordenadores = ordenadores;
        this.teclados = teclados;
        this.nombre = nombre;
        this.entradas = 0;
        this.randomizador = new Random(42);
        this.limiteEntradas = limiteEntradas;
        this.entradasConsecutivas = 10;
    }

    //Este constructor esta creado en caso de que quieras cambiar la cantidad de tickets que vas a pedir de forma consecutiva
    //Por defecto ese numero es 10, como especifica el enunciado
    public Filosofo(Lock[] ordenadores, Lock[] teclados, String nombre, int limiteEntradas, int entradasConsecutivas) throws IOException {
        this.ordenadores = ordenadores;
        this.teclados = teclados;
        this.nombre = nombre;
        this.entradas = 0;
        this.randomizador = new Random(42);
        this.limiteEntradas = limiteEntradas;
        this.entradasConsecutivas = entradasConsecutivas;
    }

    public void run() {
        //Hacemos que el filosofo ejecute el codigo cuando sigan quedando entradas en el teatro y no haya pillado 20 entradas ya.
        while (quedanEntradas() && entradas < limiteEntradas) {
            //Creo un booleano aleatorio con el objeto Random
            if (randomizador.nextBoolean()) {
                //Creamos dos numeros aleatorios que van a servir como indices para los dos arrays de locks
                int numeroLockOrdenador = randomizador.nextInt(ordenadores.length);
                int numeroLockTeclado = randomizador.nextInt(teclados.length);
                //Intento lockear un ordenador aleatorio
                if (ordenadores[numeroLockOrdenador].tryLock()) {
                    System.out.println("Soy filosofo " + nombre + " he cogido un ordenador");
                    espera(1000); //Un tiempo de espera para no saturar la consola de salida
                    //Intento lockear un teclado aleatorio
                    if (teclados[numeroLockTeclado].tryLock()) {
                        //SOLO PUEDES ENTRAR AQUÍ CUANDO HAYAS PODIDO LOCKEAR UN TECLADO Y UN ORDENADOR
                        System.out.println("Soy filosofo " + nombre + " he cogido un teclado y ya tenia un ordenador");
                        //Ejecutamos el metodo de pedir entrada
                        pedirEntrada();
                        //Una vez terminada la ejecución, soltamos teclado y ordenador
                        ordenadores[numeroLockOrdenador].unlock();
                        teclados[numeroLockTeclado].unlock();
                        System.out.println("Soy Filosofo " + nombre + " y suelto el ordenador y teclado");
                    } else {
                        //En el caso en el que haya lockeado un ordenador pero no he podido lockear un teclado
                        //suelto el ordenador que haya cogido
                        ordenadores[numeroLockOrdenador].unlock();
                        System.out.println("Soy Filosofo " + nombre + " y suelto el ordenador");
                    }
                }
            } else {
                //ES LO MISMO QUE LO DE ARRIBA PERO EN EL ORDEN INVERSO
                int numeroLockTeclado = randomizador.nextInt(teclados.length);
                int numeroLockOrdenador = randomizador.nextInt(ordenadores.length);
                if (teclados[numeroLockTeclado].tryLock()) {
                    System.out.println("Soy filosofo " + nombre + " he cogido un teclado");
                    if (ordenadores[numeroLockOrdenador].tryLock()) {
                        System.out.println("Soy filosofo " + nombre + " he cogido un ordenador y ya tenia un teclado");
                        pedirEntrada();
                        teclados[numeroLockTeclado].unlock();
                        ordenadores[numeroLockOrdenador].unlock();
                        System.out.println("Soy Filosofo " + nombre + " y suelto el ordenador y teclado");
                    } else {
                        teclados[numeroLockTeclado].unlock();
                        System.out.println("Soy Filosofo " + nombre + " y suelto el teclado");
                    }
                }
            }
            espera(1000);//Un tiempo de espera para no saturar la consola de salida
        }

        while (quedanEntradas()) {
        }//Este while está para que el siguiente System.out lo ponga al final de la consola y no entre medias de toda la ejecucion

        //Este if en verdad daria igual porque solo puedes llegar hasta el cuando se hayan acabado las entradas
        if (!quedanEntradas())
            System.out.println("Soy el filosofo " + this.nombre + " y he comprado " + entradas + " entradas.");
    }

    //Esta explicado en su totalidad en el README
    public boolean quedanEntradas() {
        try {
            conexion = new Socket("localhost", 9000);
            ObjectOutputStream salida = new ObjectOutputStream(conexion.getOutputStream());
            salida.writeObject("¿Quedan entradas?");
            salida.close();
            conexion.close();
            return true;
        } catch (Exception e) {
//            System.out.println("NOTICIAS: Se han acabado las entradas del teatro");
            return false;
        }
    }

    //Esta explicado en su totalidad en el README
    public void pedirEntrada() {
        System.out.println("Soy filosofo " + nombre + " y voy a empezar a pedir entradas");
        try {
            //Este for se repite el mismo numero de veces que entradas consecutivas quiero pedir
            //Por defecto, ese numero es 10, como especifica el enunciado
            for (int i = 0; i < entradasConsecutivas; i++) {
                conexion = new Socket("localhost", 9000);
                ObjectOutputStream salida = new ObjectOutputStream(conexion.getOutputStream());
                salida.writeObject("Una entrada, por favor.");
                ObjectInputStream entrada = new ObjectInputStream(conexion.getInputStream());
                String mensajeEntrada = (String) entrada.readObject();
                //Este if devuelve true en caso de que sigan habiendo entradas, leer README para mayor explicacion
                if (mensajeEntrada.length() > 2) {
                    System.out.println("Soy Filosofo " + nombre + " y tengo " + mensajeEntrada);
                    salida.writeObject("Filosofo " + nombre + " aqui tiene " + mensajeEntrada);
                    //Aumento en 1 el numero de entradas que tiene este filosofo
                    entradas++;
                }
                //Cierro los flujos de entrada y salida y tambien cierro la conexion
                salida.close();
                entrada.close();
                conexion.close();
//                System.out.println("Bucle de " + nombre + " Numero " + i);
            }
        } catch (IOException | ClassNotFoundException e) {
//            System.out.println("Me he quedado sin entradas");
        }
    }

    //He hecho este metodo para no tener que hacer un try y un catch cada vez que quiera hacer un sleep
    public void espera(int tiempo) {
        try {
            Thread.sleep(tiempo);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
