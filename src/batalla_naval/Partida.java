package batalla_naval;

import java.util.Scanner;

public class Partida {

    private Jugador jugador1;
    private Jugador jugador2;
    private boolean turnoJugador1 = true;

    public Partida() {
        Scanner sc = new Scanner(System.in);

        System.out.println("=== Batalla Naval ===");

        // Jugadores ingresan alias
        System.out.println("\nJugador 1:");
        String alias1 = pedirAlias(sc);

        System.out.println("\nJugador 2:");
        String alias2 = pedirAlias(sc);

        jugador1 = new Jugador(alias1);
        jugador2 = new Jugador(alias2);

        // Colocar barcos
        jugador1.colocarBarcos();
        jugador2.colocarBarcos();

        // Iniciar juego
        iniciar(sc);
    }

    private void iniciar(Scanner sc) {

    sc.nextLine(); // <-- limpieza de buffer inicial

    while (true) {

        Jugador actual = turnoJugador1 ? jugador1 : jugador2;
        Jugador enemigo = turnoJugador1 ? jugador2 : jugador1;

        System.out.println("\nTurno de: " + actual.getNombre());

        enemigo.getTablero().mostrarEnemigo();
        actual.getTablero().mostrarPropio();

        System.out.print("Ingrese coordenada (Ej: A3) o presione ESC + ENTER para salir: ");
        String input = sc.nextLine();

        // ✅ Detectar ESC
        if (verificarSalida(input, sc)) {
            System.out.println(actual.getNombre() + " abandonó la partida.");
            break;
        }

        // Validación mínima del input
        if (input.length() != 2) {
            System.out.println("Formato inválido. Ejemplo válido: B2");
            continue;
        }

        char colChar = Character.toUpperCase(input.charAt(0));
        int col = colChar - 'A';
        int fila = Character.getNumericValue(input.charAt(1)) - 1;

        Coordenada disparo = new Coordenada(fila, col);

        boolean acierto = actual.realizarAtaque(disparo, enemigo);

        if (enemigo.todosBarcosHundidos()) {
            System.out.println("¡" + actual.getNombre() + " GANÓ!");
            break;
        }

        if (!acierto) {
            turnoJugador1 = !turnoJugador1;
        }
    }
}


    // MÉTODO NUEVO → pide alias con validación
    private String pedirAlias(Scanner sc) {
        String alias;

        while (true) {
            System.out.print("Ingrese su alias de 3 letras (MAYÚSCULAS o minúsculas): ");
            alias = sc.next().trim().toUpperCase();

            if (alias.matches("[A-Z]{3}")) {
                System.out.println("Alias confirmado: " + alias);
                return alias;
            }

            System.out.println("Alias inválido. Debe contener EXACTAMENTE 3 letras (ej: ABC).");
        }
    }

    // Detecta si el usuario quiere salir usando ESC
    private boolean verificarSalida(String input, Scanner sc) {
    // El caracter ESC = ASCII 27
        if (input.length() == 1 && input.charAt(0) == 27) {
            System.out.println("¿Seguro que deseas salir? (ENTER para confirmar, cualquier tecla para cancelar)");
            String confirm = sc.nextLine();

        if (confirm.isEmpty()) {
            System.out.println("Saliendo de la partida...");
            return true;
        }

        System.out.println("Operación cancelada. Continuando...");
        }
        return false;
    }
}