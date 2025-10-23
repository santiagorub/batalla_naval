package batalla_naval;

import java.util.ArrayList;

public class Tablero {
    private char[][] matriz;
    private ArrayList<Barco> barcos;

    public Tablero() {
        matriz = new char[5][5];
        barcos = new ArrayList<>();
        for(int x = 0; x < 5; x++) {
            for(int y = 0; y < 5; y++) {
                matriz[x][y] = '~';
            }
        }
    }
}
