package codapp.analizador;

import java.io.IOException;

public class Analizador {

    public static void main(String[] args) throws IOException {
        try {
            Estados Leer = new Estados();
            Leer.leerArchivo();
        } catch (Exception e) {

        }
    }
}
