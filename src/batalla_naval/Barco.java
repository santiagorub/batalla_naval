package batalla_naval;

public abstract class Barco {
	private String nombre;
	private int tamano;
	private double vida;
	
	public Barco() {
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
}