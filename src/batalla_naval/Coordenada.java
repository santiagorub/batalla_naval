package batalla_naval;

/**
 * Representa una posición dentro del tablero de Batalla Naval.
 * Una coordenada está formada por una fila y una columna, ambas de 0 a 4.
 * Además puede marcarse como ocupada para indicar que un barco utiliza dicha casilla.
 *
 * <p>Esta clase se utiliza tanto para colocar barcos como para procesar disparos.</p>
 *
 * @author Valentina Diaz
 * @author Santiago Opazo
 * @author Tobias Villarroel
 * @version 1.0
 */
public class Coordenada {

    /**
     * Fila dentro del tablero.
     * Su valor está en el rango 0–4.
     */
    private final int fila;

    /**
     * Columna dentro del tablero.
     * Su valor está en el rango 0–4.
     */
    private final int columna;

    /**
     * Indica si la coordenada está ocupada por una parte de un barco.
     * Se utiliza para evitar superposición de barcos.
     */
    private boolean ocupada;

    /**
     * Crea una nueva coordenada con fila y columna dadas.
     *
     * @param fila    número de fila (0–4)
     * @param columna número de columna (0–4)
     */
    public Coordenada(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
        this.ocupada = false; // por defecto, la casilla está libre
    }

    /**
     * Obtiene la fila asociada a esta coordenada.
     *
     * @return número de fila (0–4)
     */
    public int getFila() {
        return fila;
    }

    /**
     * Obtiene la columna asociada a esta coordenada.
     *
     * @return número de columna (0–4)
     */
    public int getColumna() {
        return columna;
    }

    /**
     * Indica si esta coordenada está ocupada por un barco.
     *
     * @return true si un barco ocupa la casilla; false si está libre
     */
    public boolean isOcupada() {
        return ocupada;
    }

    /**
     * Modifica el estado de ocupación de la coordenada.
     *
     * @param ocupada true si la casilla queda marcada como ocupada
     */
    public void setOcupada(boolean ocupada) {
        this.ocupada = ocupada;
    }

    /**
     * Compara esta coordenada con otra para determinar si representan
     * la misma posición del tablero.
     *
     * @param obj objeto con el que comparar
     * @return true si ambas coordenadas tienen la misma fila y columna
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Coordenada c = (Coordenada) obj;
        return fila == c.fila && columna == c.columna;
    }

    /**
     * Genera un código hash basado en fila y columna.
     * Este método permite usar Coordenada como clave en estructuras de datos como HashMap.
     *
     * @return valor hash de la coordenada
     */
    @Override
    public int hashCode() {
        return fila * 31 + columna;
    }

    /**
     * Devuelve una representación en texto de la coordenada,
     * útil para depuración o mensajes en consola.
     *
     * @return texto con formato "(fila,columna)"
     */
    @Override
    public String toString() {
        return "(" + fila + "," + columna + ")";
    }
}