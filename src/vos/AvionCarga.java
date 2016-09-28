package vos;

public class AvionCarga extends Avion {
	private double carga;

	public AvionCarga(String numSerie, String modelo, String marca, int anioFabrica, double carga) {
		super(numSerie, modelo, marca, anioFabrica);
		// TODO Auto-generated constructor stub
		this.carga=carga;
	}

	public double getCarga() {
		return carga;
	}

	public void setCarga(double carga) {
		this.carga = carga;
	}
	

}
