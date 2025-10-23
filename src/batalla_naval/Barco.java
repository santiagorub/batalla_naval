package batalla_naval;

public abstract class Barco {
	protected String nombre;
	protected int tamano;
	protected int vida;
	
	public Barco(String nombre, int tamano) {
		this.nombre = nombre;
		this.tamano = tamano;
		this.vida = 0;
	}
	
	public String getNombre() {
		return this.nombre;
	}
	
	public int getTamano() {
		return this.tamano;
	}
	
	public double getVida() {
		return this.vida;
	}

	public void recibirImpacto() {
		if (vida > 0 ) vida--;
	}

	public boolean estaHundido() {
		return vida == 0;
	}
}