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

    // Validar posicion
    public boolean validarPosicion(int fila, int col, int tamano, boolean horizontal) {
        if (!inRange(fila, col)) return false;

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

    //Colocar Barco
    public void colocarBarco(Barco barco, int fila, int col, boolean horizontal) {
        if (!validarPosicion(fila, col, barco.getTamano(), horizontal)){
            System.out.println("Posición inválida para colocar el barco.");
            return;
        }

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

    //Recibir disparos
    public boolean recibirDisparos(Coordenada coord) {
        int f = coord.getFila();
        int c = coord.getColumna();

        if (!inRange(f, c)){
            System.out.println("Coordenada fuera de rango.");
            return false;
        }

        if (matriz[f][c] == 'B') {
            matriz[f][c] = 'X';
            return true;
        } else if (matriz[f][c] == '~') {
            matriz[f][c] = 'O';
            return false;
        } else if (matriz[f][c] == 'X' || matriz[f][c] == 'O') {
            System.out.println("Ya has atacado esta coordenada.");
            return false;
        }
        return false;
    }

    //Mostrar tablero propio
    public void mostrarPropio(){
        System.out.println("\nTu tablero:");
        mostrar(true);
    }

    //Mostrar tablero enemigo
    public void mostrarEnemigo(){
        System.out.println("\nTablero enemigo:");
        mostrar(false);
    }

    //Mostrar tablero
    public void mostrar(boolean mostrarBarcos) {
        System.out.println("  A B C D E");
        for (int i = 0; i < 5; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < 5; j++) {
                char celda = matriz[i][j];
                if (celda == 'B' && !mostrarBarcos) {
                System.out.print("~ ");
                } else {
                    System.out.print(celda + " ");
                }
            }
            System.out.println();
        }
    }

    // Verificar si todos los barcos estan hundidos 
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

    // Verificar si una coordenada esta dentro del rango del tablero
    private boolean inRange(int fila, int col) {
        return fila >= 0 && fila < 5 && col >= 0 && col < 5;
    }

    // Contar partes de barcos restantes
    public int partesRestantes() {
        int count = 0;
        for (char[] fila : matriz)
            for (char celda : fila)
                if (celda == 'B') count++;
        return count;
    }
}
