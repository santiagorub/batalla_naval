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

    public boolean recibirDisparos(Coordenada coord) {
        int f = coord.getFila();
        int c = coord.getColumna();
        if (matriz[f][c] == 'B') {
            matriz[f][c] = 'X';
            return true;
        } else if (matriz[f][c] == '~') {
            matriz[f][c] = 'O';
        }
        return false;
    }

    public void mostrar() {
        System.out.println("  A B C D E");
        for (int i = 0; i < 5; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < 5; j++)
                System.out.print(matriz[i][j] + " ");
            System.out.println();
        }
    }

    public boolean todosBarcosHundidos() {
        for (int i = 0; i < 5; i++) {
        for (int j = 0; j < 5; j++) {
            if (matriz[i][j] == 'B') { 
                return false; 
            }
        }
    }
    return true;
    }
}
