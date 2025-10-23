public class Coordenada {
	
	private int fila;
	private int columna;
	private boolean ocupada;
	
	public Coordenada(int fila, int columna) {
		this.fila = fila;
		this.columna = columna;
		this.ocupada = false;
	}
	
	public int getFila() {
		return fila;
	}
	public int getColumna() {
		return columna;
	}
	public boolean isOcupada() {
		return ocupada;
	}
	
	public void setOcupada(boolean ocupada) {
		this.ocupada = ocupada;
	}
}