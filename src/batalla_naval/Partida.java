package batalla_naval;

import java.util.Scanner;

/**
 * Controla el flujo principal de una partida de Batalla Naval.
 * 
 * <p>Esta clase administra:</p>
 * <ul>
 *     <li>La creación de jugadores</li>
 *     <li>La colocación inicial de barcos</li>
 *     <li>El turno por turno de los ataques</li>
 *     <li>El historial de partidas y ranking</li>
 *     <li>La detección de ganador o salida anticipada</li>
 * </ul>
 *
 * <p>Una partida comienza automáticamente desde su constructor.</p>
 *
 * @author Valentina Diaz
 * @author Santiago Opazo
 * @author Tobias Villarroel
 * @version 1.0
 */
public class Partida {

    /** Jugador 1. */
    private final Jugador j1;

    /** Jugador 2. */
    private final Jugador j2;

    /** Indica si es turno del jugador 1 (true) o del jugador 2 (false). */
    private boolean turnoJ1 = true;

    /** Registro utilizado para almacenar y mostrar historial y ranking. */
    private final Historial historial = new Historial();

    /**
     * Constructor: inicia todo el proceso de juego sin necesidad de métodos externos.
     * 
     * <p>Incluye:</p>
     * <ul>
     *     <li>Muestra historial previo</li>
     *     <li>Solicita alias</li>
     *     <li>Colocación de barcos</li>
     *     <li>Inicio del combate por turnos</li>
     * </ul>
     */
    public Partida() {

        Scanner sc = new Scanner(System.in);
        System.out.println("=== BATALLA NAVAL ===");

        // Mostrar historial previo y ranking existente
        historial.mostrarHistorial();
        historial.mostrarRanking();

        // Crear jugadores con alias validado
        j1 = new Jugador(pedirAlias(sc, "Jugador 1"));
        j2 = new Jugador(pedirAlias(sc, "Jugador 2"));

        System.out.println("\n--- Colocación de barcos ---");
        j1.colocarBarcos();
        j2.colocarBarcos();

        // Iniciar el combate
        System.out.println("\n--- ¡Comienza la batalla! ---");
        jugar(sc);

        sc.close();
    }

    /**
     * Contiene el ciclo principal de la batalla.
     * Cada turno permite ingresar coordenadas para atacar,
     * cambiar de turno, o salir anticipadamente.
     *
     * @param sc Scanner para lectura de entrada del usuario
     */
    private void jugar(Scanner sc) {

        while (true) {

            // Determinar jugador actual y enemigo
            Jugador actual = turnoJ1 ? j1 : j2;
            Jugador enemigo = turnoJ1 ? j2 : j1;

            System.out.println("\nTurno de " + actual.getNombre());
            mostrarTableros(actual, enemigo);

            System.out.print("Ingrese coordenada (ej: A3) o escriba ESC para salir: ");
            String input = sc.next().trim().toUpperCase();

            // Opción de salida
            if (input.equals("ESC")) {
                System.out.print("¿Seguro que desea salir? (S/N): ");
                if (sc.next().trim().equalsIgnoreCase("S")) {

                    System.out.println("Partida finalizada por el jugador.");

                    // El ganador es el contrario si alguien se retira
                    String ganador = turnoJ1 ? j2.getNombre() : j1.getNombre();

                    historial.registrarPartida(j1.getNombre(), j2.getNombre(), ganador);
                    historial.mostrarHistorial();
                    historial.mostrarRanking();
                    break;
                }
                continue;
            }

            // Validar formato de la coordenada
            if (input.length() != 2) {
                System.out.println("Formato inválido. Use letra + número (ej: B2).");
                continue;
            }

            char letra = input.charAt(0);
            int col = letra - 'A';
            int fila = Character.getNumericValue(input.charAt(1)) - 1;

            // Validar rango A-E y 1-5
            if (col < 0 || col > 4 || fila < 0 || fila > 4) {
                System.out.println("Coordenada fuera de rango. Use A-E y 1-5.");
                continue;
            }

            // Ejecutar ataque
            Coordenada coord = new Coordenada(fila, col);
            boolean acierto = actual.realizarAtaque(coord, enemigo);

            // Verificar si todos los barcos enemigos fueron hundidos
            if (enemigo.todosBarcosHundidos()) {
                System.out.println(actual.getNombre() + " ha ganado la partida!");
                historial.registrarPartida(j1.getNombre(), j2.getNombre(), actual.getNombre());
                break;
            }

            // Si falló el disparo, se cambia de turno
            if (!acierto) {

                System.out.println("\nTurno finalizado. Presione ENTER para pasar el turno...");
                sc.nextLine();  // limpiar buffer
                sc.nextLine();  // esperar ENTER

                // Simulación de limpieza de pantalla
                for (int i = 0; i < 40; i++) System.out.println();

                System.out.println("Jugador siguiente, presione ENTER cuando esté listo...");
                sc.nextLine();  // Espera ENTER del segundo jugador

                // Limpieza extra
                for (int i = 0; i < 40; i++) System.out.println();

                turnoJ1 = !turnoJ1; // Cambiar turno
            }
        }

        // Mostrar ranking final tras terminar la partida
        historial.mostrarRanking();
    }

    /**
     * Solicita un alias válido al jugador (3 letras A–Z).
     * 
     * @param sc Scanner para lectura
     * @param msg mensaje a mostrar
     * @return alias validado en mayúsculas
     */
    private String pedirAlias(Scanner sc, String msg) {

        while (true) {
            System.out.print(msg + " - Ingrese un alias de 3 letras: ");
            String alias = sc.next().trim().toUpperCase();

            if (alias.matches("[A-Z]{3}")) {

                // Avisar si el jugador ya aparece en el historial
                if (historial.buscarJugador(alias)) {
                    System.out.println("Aviso: el jugador " + alias + " ya tiene victorias registradas.");
                }

                return alias;
            }

            System.out.println("Alias inválido. Ejemplo válido: ABC");
        }
    }

    /**
     * Muestra el tablero propio y el tablero enemigo lado a lado.
     * El tablero enemigo oculta los barcos.
     *
     * @param actual jugador que está en turno
     * @param enemigo jugador objetivo del ataque
     */
    private void mostrarTableros(Jugador actual, Jugador enemigo) {

        System.out.println("                                     ");
        System.out.println("  Tu tablero      Tablero enemigo");
        System.out.println("                                     ");
        System.out.println("  A B C D E          A B C D E");

        for (int i = 0; i < 5; i++) {

            // Fila del tablero propio
            String filaPropia = actual.getTablero().filaComoTexto(i, true);

            // Fila del tablero enemigo (ocultando barcos)
            String filaEnemiga = enemigo.getTablero().filaComoTexto(i, false);

            System.out.printf("%s       %s%n", filaPropia, filaEnemiga);
        }
    }
}
