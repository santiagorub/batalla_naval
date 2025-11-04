package batalla_naval;

import java.util.Scanner;

public class Jugador {
    private String nombre;
    private Tablero tablero;

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
                tablero.mostrar();
                System.out.println("Colocando " + b.getNombre() + " (" + b.getTamano() + " casillas)");

                System.out.print("Fila (1-5): ");
                int fila = sc.nextInt() - 1;
                System.out.print("Columna (A-E): ");
                int col = sc.next().toUpperCase().charAt(0) - 'A';
                System.out.print("Horizontal (true/false): ");
                boolean horizontal = sc.nextBoolean();

                if (tablero.validarPosicion(fila, col, b.getTamano(), horizontal)) {
                    tablero.colocarBarco(b, fila, col, horizontal);
                    colocado = true;
                } else {
                    System.out.println("Posición inválida, intenta de nuevo.");
                }
            }
        }
		sc.close();
    }

    public boolean realizarAtaque(Coordenada coord, Jugador enemigo) {
        boolean acierto = enemigo.getTablero().recibirDisparos(coord);
        if (acierto) System.out.println("¡Impacto!");
        else System.out.println("Agua");
        return acierto;
    }

    public boolean todosBarcosHundidos() {
        return this.todosBarcosHundidos();
    }
}
