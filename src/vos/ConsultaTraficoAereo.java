package vos;

import java.util.ArrayList;

public class ConsultaTraficoAereo {

	private ArrayList<ViajeViajeros> viajesViajeros;
	private ArrayList<ViajeCarga> viajesCarga;
	public ArrayList<ViajeViajeros> getViajesViajeros() {
		return viajesViajeros;
	}
	public void setViajesViajeros(ArrayList<ViajeViajeros> viajesViajeros) {
		this.viajesViajeros = viajesViajeros;
	}
	public ArrayList<ViajeCarga> getViajesCarga() {
		return viajesCarga;
	}
	public void setViajesCarga(ArrayList<ViajeCarga> viajesCarga) {
		this.viajesCarga = viajesCarga;
	}
	/**
	 * @param viajesViajeros
	 * @param viajesCarga
	 */
	public ConsultaTraficoAereo(ArrayList<ViajeViajeros> viajesViajeros, ArrayList<ViajeCarga> viajesCarga) {
		super();
		this.viajesViajeros = viajesViajeros;
		this.viajesCarga = viajesCarga;
	}
	
}
