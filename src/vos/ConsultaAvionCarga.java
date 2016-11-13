package vos;

import java.util.ArrayList;

public class ConsultaAvionCarga extends ConsultaAvion {

	private ArrayList<VueloCarga> vuelos;
	public ArrayList<VueloCarga> getVuelos() {
		return vuelos;
	}

	public void setVuelos(ArrayList<VueloCarga> vuelos) {
		this.vuelos = vuelos;
	}
	private double cargaMax;
	/**
	 * @param numSerie
	 * @param modelo
	 * @param marca
	 * @param anioFabrica
	 * @param tipo
	 * @param cargaMax
	 */
	public ConsultaAvionCarga(String numSerie, String modelo, String marca, int anioFabrica, String tipo,
			ArrayList<VueloCarga> vuelos, double  millas, double cargaMax) {
		super(numSerie, modelo, marca, anioFabrica, tipo, millas);
		
		this.cargaMax = cargaMax;
		this.vuelos = vuelos;
	}
	
	public double getCargaMax() {
		return cargaMax;
	}
	public void setCargaMax(double cargaMax) {
		this.cargaMax = cargaMax;
	}
	
}
