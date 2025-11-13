package batalla_naval;

import java.util.Arrays;

/**
 * Representa el tablero de un jugador dentro del juego Batalla Naval.
 * El tablero es una matriz de 5x5 que utiliza los siguientes símbolos:
 *
 * '~' → agua sin disparo  
 * 'B' → parte de un barco  
 * 'X' → impacto (casilla con barco alcanzado)  
 * 'O' → disparo fallado  
 *
 * Esta clase permite colocar barcos, recibir disparos,
 * verificar si quedan barcos y mostrar el tablero.
 *
 * @author Valentina Diaz
 * @author Santiago Opazo
 * @author Tobias Villarroel
 * @version 1.0
 */
public class Tablero {

    /** Matriz de 5x5 que representa el tablero del jugador. */
    private final char[][] matriz = new char[5][5];

    /**
     * Constructor: inicializa todas las casillas del tablero con '~' indicando agua.
     */
    public Tablero() {
        for (char[] fila : matriz)
            Arrays.fill(fila, '~'); // Cada fila se llena con agua
    }

    /**
     * Intenta colocar un barco en el tablero validando posición y superposición.
     *
     * @param barco      barco a colocar
     * @param fila       fila inicial (0–4)
     * @param col        columna inicial (0–4)
     * @param horizontal true si el barco se coloca horizontalmente
     * @return true si el barco se colocó correctamente, false si la posición no era válida
     */
    public boolean colocarBarco(Barco barco, int fila, int col, boolean horizontal) {

        // Validar límites del tablero y casillas libres
        if (!validarPosicion(fila, col, barco.getTamano(), horizontal))
            return false;

        // Colocar el barco casilla por casilla
        for (int i = 0; i < barco.getTamano(); i++) {
            if (horizontal)
                matriz[fila][col + i] = 'B'; // Coloca hacia la derecha
            else
                matriz[fila + i][col] = 'B'; // Coloca hacia abajo
        }

        return true;
    }

    /**
     * Verifica si un barco puede colocarse en una posición específica
     * sin salirse del tablero y sin superponerse con otros barcos.
     *
     * @param fila fila inicial
     * @param col columna inicial
     * @param tam tamaño del barco
     * @param hor true si es horizontal, false si vertical
     * @return true si la posición es válida
     */
    private boolean validarPosicion(int fila, int col, int tam, boolean hor) {

        // Verificar que la casilla inicial está dentro de rango
        if (!inRange(fila, col)) return false;

        // Evitar que el barco se salga del límite del tablero
        if (hor && col + tam > 5) return false;
        if (!hor && fila + tam > 5) return false;

        // Verificar superposición con otros barcos
        for (int i = 0; i < tam; i++) {
            if (hor && matriz[fila][col + i] != '~') return false;       // Hacia derecha
            if (!hor && matriz[fila + i][col] != '~') return false;       // Hacia abajo
        }

        return true;
    }

    /**
     * Procesa un disparo realizado sobre este tablero.
     *
     * @param c coordenada del disparo
     * @return true si la casilla contenía un barco ('B') y fue impactado
     */
    public boolean recibirDisparo(Coordenada c) {

        // Validar que la coordenada esté dentro del tablero
        if (!inRange(c.getFila(), c.getColumna())) return false;

        char actual = matriz[c.getFila()][c.getColumna()];

        // Caso: impacto sobre un barco
        if (actual == 'B') {
            matriz[c.getFila()][c.getColumna()] = 'X'; // Marca impacto
            return true;
        }

        // Caso: golpea agua por primera vez
        if (actual == '~') {
            matriz[c.getFila()][c.getColumna()] = 'O'; // Marca agua
            return false;
        }

        // Caso: ya se había disparado aquí
        System.out.println("Ya se atacó esa coordenada.");
        return false;
    }

    /**
     * Muestra el tablero por consola.
     * Si mostrarBarcos es false, oculta los barcos del jugador (muestra '~').
     *
     * @param mostrarBarcos true para ver los barcos, false para ocultarlos
     */
    public void mostrar(boolean mostrarBarcos) {
        System.out.println("  A B C D E");

        for (int i = 0; i < 5; i++) {
            System.out.print((i + 1) + " "); // Numeración visible para el jugador

            for (int j = 0; j < 5; j++) {
                char c = matriz[i][j];

                // Oculta barcos si es el tablero enemigo
                if (c == 'B' && !mostrarBarcos) c = '~';

                System.out.print(c + " ");
            }
            System.out.println();
        }
    }

    /**
     * Determina si el jugador ya no tiene barcos sin hundir.
     *
     * @return true si no queda ninguna casilla con 'B'
     */
    public boolean todosBarcosHundidos() {
        for (char[] fila : matriz)
            for (char c : fila)
                if (c == 'B') return false; // Se encontró un barco vivo

        return true; // No quedan partes de barco
    }

    /**
     * Verifica si una coordenada está dentro de los límites del tablero (0 a 4).
     *
     * @param f fila
     * @param c columna
     * @return true si la posición es válida
     */
    private boolean inRange(int f, int c) {
        return f >= 0 && f < 5 && c >= 0 && c < 5;
    }

    /**
     * Convierte una fila completa del tablero a texto,
     * útil para guardar el tablero en archivos.
     *
     * @param fila número de fila (0–4)
     * @param mostrarBarcos true para mostrar barcos, false para ocultarlos
     * @return texto representando la fila
     */
    public String filaComoTexto(int fila, boolean mostrarBarcos) {
        StringBuilder sb = new StringBuilder();
        sb.append(fila + 1).append(" "); // Número de fila visible

        for (int j = 0; j < 5; j++) {
            char c = matriz[fila][j];

            // Ocultar barcos según configuración
            if (c == 'B' && !mostrarBarcos) c = '~';

            sb.append(c).append(" ");
        }

        return sb.toString();
    }
}