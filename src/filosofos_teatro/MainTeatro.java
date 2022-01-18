package filosofos_teatro;

import java.io.IOException;

public class MainTeatro {

    public static void main(String[] args) {
        try {
            Teatro teatro = new Teatro(50, 9000);
            teatro.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
