package batalla_naval;

import java.util.Scanner;

public class Partida {
    private final Jugador j1, j2;
    private boolean turnoJ1 = true;

    public Partida() {
        Scanner sc = new Scanner(System.in);
        System.out.println("=== BATALLA NAVAL ===");

        j1 = new Jugador(pedirAlias(sc, "Jugador 1"));
        j2 = new Jugador(pedirAlias(sc, "Jugador 2"));

        System.out.println("\n--- Colocación de barcos ---");
        j1.colocarBarcos();
        j2.colocarBarcos();

        System.out.println("\n--- ¡Comienza la batalla! ---");
        jugar(sc);

        sc.close();
    }

    private void jugar(Scanner sc) {
        while (true) {
            Jugador actual = turnoJ1 ? j1 : j2;
            Jugador enemigo = turnoJ1 ? j2 : j1;

            System.out.println("\nTurno de " + actual.getNombre());
            System.out.println("                                     ");            
            mostrarTableros(actual, enemigo);

            System.out.print("Ingrese coordenada (ej: A3) o escriba ESC para salir: ");
            String input = sc.next().trim().toUpperCase();

            if (input.equals("ESC")) {
                System.out.print("¿Seguro que desea salir? (S/N): ");
                if (sc.next().trim().equalsIgnoreCase("S")) {
                    System.out.println("Partida finalizada por el jugador.");
                    break;
                }
                continue;
            }

            if (input.length() != 2) {
                System.out.println("Formato inválido. Use una letra y un número (ej: B2).");
                continue;
            }

            char letra = input.charAt(0);
            int col = letra - 'A';
            int fila = Character.getNumericValue(input.charAt(1)) - 1;

            if (col < 0 || col > 4 || fila < 0 || fila > 4) {
                System.out.println("Coordenada fuera de rango. Use A-E y 1-5.");
                continue;
            }

            Coordenada coord = new Coordenada(fila, col);
            boolean acierto = actual.realizarAtaque(coord, enemigo);

            if (enemigo.todosBarcosHundidos()) {
                System.out.println(actual.getNombre() + " ha ganado la partida!");
                break;
            }

            if (!acierto) {
                turnoJ1 = !turnoJ1; // solo cambia de turno si falló
            }
        }
    }

    private String pedirAlias(Scanner sc, String msg) {
        while (true) {
            System.out.print(msg + " - Ingrese un alias de 3 letras: ");
            String alias = sc.next().trim().toUpperCase();
            if (alias.matches("[A-Z]{3}")) return alias;
            System.out.println("Alias inválido. Ejemplo válido: ABC");
        }
    }

    private void mostrarTableros(Jugador actual, Jugador enemigo) {
    System.out.println("                                     ");
    System.out.println("  Tu tablero      Tablero enemigo");
    System.out.println("                                     ");
    System.out.println("  A B C D E          A B C D E");

    for (int i = 0; i < 5; i++) {
        String filaPropia = actual.getTablero().filaComoTexto(i, true);
        String filaEnemiga = enemigo.getTablero().filaComoTexto(i, false);
        System.out.printf("%s       %s%n", filaPropia, filaEnemiga);
    }

    
}
}