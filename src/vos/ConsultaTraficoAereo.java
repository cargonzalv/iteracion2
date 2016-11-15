package vos;

import java.util.ArrayList;

public class ConsultaTraficoAereo {

	private ArrayList<VueloViajeros> viajesViajeros;
	private ArrayList<VueloCarga> viajesCarga;
	public ArrayList<VueloViajeros> getViajesViajeros() {
		return viajesViajeros;
	}
	public void setViajesViajeros(ArrayList<VueloViajeros> viajesViajeros) {
		this.viajesViajeros = viajesViajeros;
	}
	public ArrayList<VueloCarga> getViajesCarga() {
		return viajesCarga;
	}
	public void setViajesCarga(ArrayList<VueloCarga> viajesCarga) {
		this.viajesCarga = viajesCarga;
	}
	/**
	 * @param viajesViajeros
	 * @param viajesCarga
	 */
	public ConsultaTraficoAereo(ArrayList<VueloViajeros> viajesViajeros, ArrayList<VueloCarga> viajesCarga) {
		super();
		this.viajesViajeros = viajesViajeros;
		this.viajesCarga = viajesCarga;
	}
	
}
