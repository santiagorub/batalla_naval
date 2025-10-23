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

    public boolean validarPosicion(int fila, int col, int tamano, boolean horizontal) {
        if (horizontal) {
            if (col + tamano > 5) return false;
            for(int i = 0; i < tamano; i++) {
                if (matriz[fila][col+i] != '~') return false;
            }
        } else {
            if (fila + tamano > 5) return false;
            for(int i = 0; i < tamano; i++) {
                if (matriz[fila+i][col] != '~') return false;
            }
        }
        return true;
    }

    public void colocarBarco(Barco barco, int fila, int col, boolean horizontal) {
        if (!validarPosicion(fila, col, barco.getTamano(), horizontal)) return;

        if (horizontal) {
            for(int i = 0; i < barco.getTamano(); i++) {
                matriz[fila][col+i] = 'B';
            }
        } else {
            for(int i = 0; i < barco.getTamano(); i++) {
                matriz[fila+i][col] = 'B';
            }
        }

        barcos.add(barco);
    }

    
}
