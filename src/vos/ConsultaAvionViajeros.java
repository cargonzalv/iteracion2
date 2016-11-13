package vos;

import java.util.ArrayList;

public class ConsultaAvionViajeros extends ConsultaAvion {

	private int sillasEjecutivas;
	private int sillasEconomicas;
	private ArrayList<VueloViajeros> vuelos;
	private double millas;
	
	
	/**
	 * @param numSerie
	 * @param modelo
	 * @param marca
	 * @param anioFabrica
	 * @param tipo
	 * @param sillasEjecutivas
	 * @param sillasEconomicas
	 * @param vuelos
	 * @param millas
	 */
	public ConsultaAvionViajeros(String numSerie, String modelo, String marca, int anioFabrica, String tipo
			,ArrayList<VueloViajeros> vuelos, double millas,int sillasEjecutivas, int sillasEconomicas) {
		super(numSerie, modelo, marca, anioFabrica, tipo, millas);
		
		this.sillasEjecutivas = sillasEjecutivas;
		this.sillasEconomicas = sillasEconomicas;
		this.vuelos = vuelos;
		this.millas = millas;
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
	public ArrayList<VueloViajeros> getVuelos() {
		return vuelos;
	}
	public void setVuelos(ArrayList<VueloViajeros> vuelos) {
		this.vuelos = vuelos;
	}
	public double getMillas() {
		return millas;
	}
	public void setMillas(double millas) {
		this.millas = millas;
	}
	
}
