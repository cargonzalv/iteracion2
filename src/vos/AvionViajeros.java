package vos;

public class AvionViajeros extends Avion {
	private int sillasEjecutivas;
	private int sillasEconomicas;
	
	public AvionViajeros(String numSerie, String modelo, String marca, int anioFabrica,String tipo, int sillasEjecutivas,int sillasEconomicas) {
		super(numSerie, modelo, marca, anioFabrica, tipo);
		// TODO Auto-generated constructor stub
		this.sillasEconomicas=sillasEconomicas;
		this.sillasEjecutivas=sillasEjecutivas;
	}

	public int getSillasEjecutivas() {
		return sillasEjecutivas;
	}

	public void setSillasEjecutivas(int sillasEjecutivas) {
		this.sillasEjecutivas = sillasEjecutivas;
	}

	public int getSillasEconomicas() {
		return sillasEconomicas;
	}

	public void setSillasEconomicas(int sillasEconomicas) {
		this.sillasEconomicas = sillasEconomicas;
	}
	

}

