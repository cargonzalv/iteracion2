package vos;

import java.util.ArrayList;
import java.util.Date;


public class ViajeViajeros extends Viaje {
	public final static String TIPO="Viajero";
	private double costoEjec;
	private double costoEcon;
	private int cantidadViajeros;
	private ArrayList<Viajeros> viajeros;
	public ViajeViajeros(int id,int idVuelo, Date horaSalida, Date horaLlegada, int frecuencia, String tipoVuelo, String aerolinea,
			String avion, Aeropuerto aeropuertoSA, Aeropuerto aeropuertoLL, double distancia, int duracion, boolean realizado, double costoEjec, double costoEcon) {
		super(id,idVuelo, horaSalida, horaLlegada, frecuencia, TIPO, aerolinea, avion, aeropuertoSA, aeropuertoLL, distancia,
				duracion, realizado,tipoVuelo);
		this.costoEjec = costoEjec;
		this.setCostoEcon(costoEcon);
		// TODO Auto-generated constructor stub
	}
	public double getCostoEjec() {
		return costoEjec;
	}
	public void setCostoEjec(double costoEjec) {
		this.costoEjec = costoEjec;
	}
	public double getCostoEcon() {
		return costoEcon;
	}
	public void setCostoEcon(double costoEcon) {
		this.costoEcon = costoEcon;
	}
	public int getCantidadViajeros() {
		return cantidadViajeros;
	}
	public void setCantidadViajeros(int cantidadViajeros) {
		this.cantidadViajeros = cantidadViajeros;
	}
	public ArrayList<Viajeros> getViajeros() {
		return viajeros;
	}
	public void setViajeros(ArrayList<Viajeros> viajeros) {
		this.viajeros = viajeros;
	}

}
