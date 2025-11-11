package batalla_naval;

public abstract class Barco {
	protected String nombre;
	protected int tamano;
	protected int vida;
	
	public Barco(String nombre, int tamano) {
		this.nombre = nombre;
		this.tamano = tamano;
		this.vida = tamano;
	}
	
	public String getNombre() {
		return this.nombre;
	}
	
	public int getTamano() {
		return this.tamano;
	}
	
	public int getVida() {
		return this.vida;
	}

	public void recibirImpacto() {
		if (vida > 0 ) vida--;
	}

	public boolean estaHundido() {
		return vida == 0;
	}
}

class PortaAviones extends Barco {
	public PortaAviones() {
		super("Portaaviones", 4);
	}
}

class Submarino extends Barco {
	public Submarino() {
		super("Submarino", 3);
	}
}

class Destructor extends Barco {
	public Destructor() {
		super("Destructor", 2);
	}
}