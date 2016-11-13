package vos;

public class AvionCarga extends Avion {
	private double cargaMax;

	public AvionCarga(String numSerie, String modelo, String marca, int anioFabrica, String tipo, double cargaMax) {
		super(numSerie, modelo, marca, anioFabrica, tipo);
		// TODO Auto-generated constructor stub
		this.cargaMax=cargaMax;
	}

	public double getCargaMax() {
		return cargaMax;
	}

	public void setCargaMax(double cargaMax) {
		this.cargaMax = cargaMax;
	}
	

}
