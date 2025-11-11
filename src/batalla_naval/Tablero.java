package batalla_naval;
import java.util.Arrays;

public class Tablero {
    private final char[][] matriz = new char[5][5];

    public Tablero() {
        for (char[] fila : matriz) Arrays.fill(fila, '~');
    }

    public boolean colocarBarco(Barco barco, int fila, int col, boolean horizontal) {
        if (!validarPosicion(fila, col, barco.getTamano(), horizontal)) return false;

        for (int i = 0; i < barco.getTamano(); i++) {
            if (horizontal) matriz[fila][col + i] = 'B';
            else matriz[fila + i][col] = 'B';
        }
        return true;
    }

    private boolean validarPosicion(int fila, int col, int tam, boolean hor) {
        if (!inRange(fila, col)) return false;
        if (hor && col + tam > 5) return false;
        if (!hor && fila + tam > 5) return false;

        for (int i = 0; i < tam; i++) {
            if (hor && matriz[fila][col + i] != '~') return false;
            if (!hor && matriz[fila + i][col] != '~') return false;
        }
        return true;
    }

    public boolean recibirDisparo(Coordenada c) {
        if (!inRange(c.getFila(), c.getColumna())) return false;
        char actual = matriz[c.getFila()][c.getColumna()];
        if (actual == 'B') { 
            matriz[c.getFila()][c.getColumna()] = 'X'; 
            return true; 
        }
        if (actual == '~') { 
            matriz[c.getFila()][c.getColumna()] = 'O'; 
            return false; 
        }
        System.out.println("Ya se atacó esa coordenada.");
        return false;
    }

    public void mostrar(boolean mostrarBarcos) {
        System.out.println("  A B C D E");
        for (int i = 0; i < 5; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < 5; j++) {
                char c = matriz[i][j];
                if (c == 'B' && !mostrarBarcos) c = '~';
                System.out.print(c + " ");
            }
            System.out.println();
        }
    }

    public boolean todosBarcosHundidos() {
        for (char[] fila : matriz)
            for (char c : fila)
                if (c == 'B') return false;
        return true;
    }

    private boolean inRange(int f, int c) {
        return f >= 0 && f < 5 && c >= 0 && c < 5;
    }

    public String filaComoTexto(int fila, boolean mostrarBarcos) {
    StringBuilder sb = new StringBuilder();
    sb.append(fila + 1).append(" ");
    for (int j = 0; j < 5; j++) {
        char c = matriz[fila][j];
        if (c == 'B' && !mostrarBarcos) c = '~';
        sb.append(c).append(" ");
    }
    return sb.toString();
}


}