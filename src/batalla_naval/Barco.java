package batalla_naval;

/**
 * Representa un barco dentro del juego Batalla Naval.
 * Es una clase abstracta porque cada tipo de barco tiene
 * un tamaño específico definido en sus subclases.
 *
 * Cada barco posee un nombre, un tamaño (número de casillas que ocupa)
 * y una cantidad de vida equivalente a su tamaño. Cada impacto reduce
 * la vida en 1 unidad hasta que el barco está hundido.
 *
 * @author Valentina Diaz
 * @author Santiago Opazo
 * @author Tobias Villarroel
 * @version 1.0
 */
public abstract class Barco {

    /** Nombre del barco (por ejemplo: Portaaviones, Submarino). */
    protected String nombre;

    /** Cantidad de casillas que ocupa el barco. */
    protected int tamano;

    /** Vida restante del barco. Se inicializa con el tamaño. */
    protected int vida;

    /**
     * Constructor base para todos los barcos.
     *
     * @param nombre Nombre del barco.
     * @param tamano Cantidad de casillas que ocupa.
     */
    public Barco(String nombre, int tamano) {
        this.nombre = nombre;       // Guarda el nombre del barco
        this.tamano = tamano;       // Guarda cuántas casillas ocupa
        this.vida = tamano;         // La vida inicia igual al tamaño
    }

    /** @return nombre del barco. */
    public String getNombre() { return this.nombre; }

    /** @return cantidad de casillas que ocupa el barco. */
    public int getTamano() { return this.tamano; }

    /** @return vida restante del barco. */
    public int getVida() { return this.vida; }

    /**
     * Reduce la vida del barco en 1 si aún no está hundido.
     * Se invoca cuando un disparo impacta sobre una de sus casillas.
     */
    public void recibirImpacto() {
        if (vida > 0) vida--;   // Si aún tiene vida, se le resta 1
    }

    /**
     * Indica si el barco está completamente destruido.
     * @return true si la vida llegó a 0.
     */
    public boolean estaHundido() {
        return vida == 0;       // Hundido cuando vida llega a 0
    }
}

/**
 * Representa un Portaaviones de tamaño 4.
 * Hereda todo el comportamiento de Barco.
 */
class PortaAviones extends Barco {
    public PortaAviones() {
        super("Portaaviones", 4);   // Portaaviones ocupa 4 casillas
    }
}

/**
 * Representa un Submarino de tamaño 3.
 * Hereda todo el comportamiento de Barco.
 */
class Submarino extends Barco {
    public Submarino() {
        super("Submarino", 3);      // Submarino ocupa 3 casillas
    }
}

/**
 * Representa un Destructor de tamaño 2.
 * Hereda todo el comportamiento de Barco.
 */
class Destructor extends Barco {
    public Destructor() {
        super("Destructor", 2);     // Destructor ocupa 2 casillas
    }
}
