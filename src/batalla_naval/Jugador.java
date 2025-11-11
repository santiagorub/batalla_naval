package batalla_naval;

import java.util.Scanner;

public class Jugador {
    private final String nombre;
    private final Tablero tablero;

    public Jugador(String nombre) {
        this.nombre = nombre;
        this.tablero = new Tablero();
    }

    public String getNombre() { return nombre; }
    public Tablero getTablero() { return tablero; }

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

    // mostrar el tablero final del jugador
    System.out.println("\n¡Todos los barcos colocados, " + nombre + "!");
    tablero.mostrar(true);
    
    System.out.println("\nPresione ENTER para continuar...");
    sc.nextLine(); // Limpia el buffer si quedó algo pendiente
    sc.nextLine(); // Espera que el jugador presione ENTER


    for (int i = 0; i < 20; i++) System.out.println();
    System.out.println("Turno del siguiente jugador...");
}

// === Métodos auxiliares ===

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

private int pedirColumna(Scanner sc) {
    while (true) {
        System.out.print("Columna (A-E): ");
        String input = sc.next().trim().toUpperCase();
        if (input.length() == 1 && input.charAt(0) >= 'A' && input.charAt(0) <= 'E')
            return input.charAt(0) - 'A';
        System.out.println("Entrada inválida. Use una letra entre A y E.");
    }
}

private boolean pedirOrientacion(Scanner sc) {
    while (true) {
        System.out.print("Orientación (H = horizontal, V = vertical): ");
        String input = sc.next().trim().toUpperCase();
        if (input.equals("H")) return true;
        if (input.equals("V")) return false;
        System.out.println("Entrada inválida. Escriba H o V.");
    }
}

    public boolean realizarAtaque(Coordenada coord, Jugador enemigo) {
        boolean acierto = enemigo.getTablero().recibirDisparo(coord);
        if (acierto) System.out.println("¡Impacto!");
        else System.out.println("Agua");
        return acierto;
    }

    public boolean todosBarcosHundidos() {
        return tablero.todosBarcosHundidos();
    }
}