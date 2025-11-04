package batalla_naval;

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

	@Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Coordenada c = (Coordenada) obj;
        return fila == c.fila && columna == c.columna;
    }

    @Override
    public int hashCode() {
        return fila * 31 + columna;
    }

    @Override
    public String toString() {
        return "(" + fila + "," + columna + ")";
    }
}