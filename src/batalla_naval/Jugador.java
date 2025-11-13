package batalla_naval;

import java.util.Scanner;

/**
 * Representa a un jugador dentro del juego Batalla Naval.
 * Cada jugador posee un tablero propio y debe encargarse
 * de colocar sus barcos, realizar ataques y verificar 
 * su progreso durante la partida.
 * 
 * <p>Incluye validaciones de entrada de usuario para asegurar que
 * las coordenadas y orientaciones sean correctas.</p>
 * 
 * @author Tobias Villarroel
 * @author Santiago Opazo
 * @author Valentina Diaz
 * 
 * @version 1.0
 */
public class Jugador {
    /** Nombre o alias del jugador (3 letras mayúsculas). */
    private final String nombre;
    /** Tablero asociado al jugador. */
    private final Tablero tablero;

    /**
     * Crea un nuevo jugador con el nombre indicado y un tablero vacío.
     * 
     * @param nombre alias del jugador
     */
    public Jugador(String nombre) {
        this.nombre = nombre;
        this.tablero = new Tablero();
    }

    /** @return el nombre del jugador */
    public String getNombre() { return nombre; }

    /** @return el tablero del jugador */
    public Tablero getTablero() { return tablero; }

    /**
     * Permite al jugador colocar sus barcos en el tablero.
     * Solicita al usuario las coordenadas y orientación (H/V)
     * para cada barco, validando que no se superpongan ni salgan del tablero.
     */
    public void colocarBarcos() {
        Scanner sc = new Scanner(System.in);
        Barco[] barcos = { new Destructor(), new Submarino(), new PortaAviones() };

        System.out.println("\nColocando barcos para " + nombre + "...\n");

        for (Barco b : barcos) {
            boolean colocado = false;
            while (!colocado) {
                tablero.mostrar(true);
                System.out.println("Colocando " + b.getNombre() + " (" + b.getTamano() + " casillas)");

                int fila = pedirFila(sc);
                int col = pedirColumna(sc);
                boolean horizontal = pedirOrientacion(sc);

                if (tablero.colocarBarco(b, fila, col, horizontal)) {
                    colocado = true;
                } else {
                    System.out.println("Posición inválida o superpuesta. Intente nuevamente.");
                }
            }
        }

        System.out.println("\n¡Todos los barcos colocados, " + nombre + "!");
        tablero.mostrar(true);
        System.out.println("\nPresione ENTER para continuar...");
        sc.nextLine(); 
        sc.nextLine(); 

        for (int i = 0; i < 40; i++) System.out.println();
        System.out.println("Turno del siguiente jugador...");
    }

    /**
     * Solicita al jugador ingresar una fila (1–5) y valida la entrada.
     * @param sc objeto Scanner para lectura
     * @return índice de fila (0–4)
     */
    private int pedirFila(Scanner sc) {
        while (true) {
            System.out.print("Fila (1-5): ");
            String input = sc.next().trim();
            try {
                int fila = Integer.parseInt(input) - 1;
                if (fila >= 0 && fila < 5) return fila;
                System.out.println("Debe ingresar un número entre 1 y 5.");
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Use un número entre 1 y 5.");
            }
        }
    }

    /**
     * Solicita al jugador ingresar una columna (A–E) y valida la entrada.
     * @param sc objeto Scanner para lectura
     * @return índice de columna (0–4)
     */
    private int pedirColumna(Scanner sc) {
        while (true) {
            System.out.print("Columna (A-E): ");
            String input = sc.next().trim().toUpperCase();
            if (input.length() == 1 && input.charAt(0) >= 'A' && input.charAt(0) <= 'E')
                return input.charAt(0) - 'A';
            System.out.println("Entrada inválida. Use una letra entre A y E.");
        }
    }

    /**
     * Solicita al jugador la orientación del barco (horizontal o vertical).
     * @param sc objeto Scanner para lectura
     * @return {@code true} si el barco es horizontal, {@code false} si es vertical
     */
    private boolean pedirOrientacion(Scanner sc) {
        while (true) {
            System.out.print("Orientación (H = horizontal, V = vertical): ");
            String input = sc.next().trim().toUpperCase();
            if (input.equals("H")) return true;
            if (input.equals("V")) return false;
            System.out.println("Entrada inválida. Escriba H o V.");
        }
    }

    /**
     * Ejecuta un ataque sobre el tablero enemigo.
     * Muestra mensajes de impacto o agua según el resultado.
     * 
     * @param coord coordenadas del disparo
     * @param enemigo jugador oponente
     * @return {@code true} si hubo impacto, {@code false} si fue agua
     */
    public boolean realizarAtaque(Coordenada coord, Jugador enemigo) {
        boolean acierto = enemigo.getTablero().recibirDisparo(coord);
        if (acierto) System.out.println("¡Impacto!");
        else System.out.println("¡Agua!");
        return acierto;
    }

    /**
     * Verifica si todos los barcos del jugador fueron hundidos.
     * @return {@code true} si no quedan barcos, {@code false} en caso contrario
     */
    public boolean todosBarcosHundidos() {
        return tablero.todosBarcosHundidos();
    }
}