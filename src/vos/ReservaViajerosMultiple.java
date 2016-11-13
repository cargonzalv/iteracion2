package vos;

import java.util.ArrayList;
import java.util.Date;

public class ReservaViajerosMultiple extends Reserva{
	
	private ArrayList<ReservaViajeros> reservas;
	private int idViajero;
	private String tipoIdViajero;
	private String clase;
	public static final String ECONOMICA = "Economica";
	public static final String EJECUTIVA = "Ejecutiva";

	public ReservaViajerosMultiple(int idReservaGen, int idViajero,String tipoIdViajero, String clase,String fecha,String tipoReserva, String aeroSal, String aeroLleg, ArrayList<ReservaViajeros> reservas) {
		super(idReservaGen,fecha,tipoReserva,aeroSal, aeroLleg);
		// TODO Auto-generated constructor stub
		this.idViajero=idViajero;
		this.setReservas(reservas);
		this.tipoIdViajero = tipoIdViajero;
		this.setAeroSal(aeroSal);
		this.setAeroLleg(aeroLleg);
		this.setClase(clase);;
	}
	
	public int getIdViajero() {
		return idViajero;
	}
	public void setIdViajero(int idViajero) {
		this.idViajero = idViajero;
	}
	
	public String getTipoIdViajero() {
		return tipoIdViajero;
	}
	public void setTipoIdViajero(String tipoIdViajero) {
		this.tipoIdViajero = tipoIdViajero;
	}
	public String getClase() {
		return clase;
	}
	public void setClase(String clase) {
		this.clase = clase;
	}


	

	public ArrayList<ReservaViajeros> getReservas() {
		return reservas;
	}

	public void setReservas(ArrayList<ReservaViajeros> reservas) {
		this.reservas = reservas;
	}

	

}
