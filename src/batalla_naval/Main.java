package batalla_naval;

/**
 * Punto de entrada principal para el juego Batalla Naval.
 * 
 * <p>Esta clase únicamente inicia una nueva partida creando
 * una instancia de {@link Partida}, la cual gestiona todo
 * el flujo del juego desde su constructor.</p>
 *
 * @author Valentina Diaz
 * @author Santiago Opazo
 * @author Tobias Villarroel
 * @version 1.0
 */
public class Main {

    /**
     * Método principal del programa.
     * Inicia automáticamente una nueva partida.
     *
     * @param args argumentos de línea de comando (no se utilizan)
     */
    public static void main(String[] args) {

        // Crear e iniciar una nueva partida
        new Partida();
    }
}
