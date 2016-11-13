package vos;

import java.util.ArrayList;
import java.util.Date;

public class ReservaViajeros extends Reserva{
	
	private int idReservaGeneral;
	private int idViajero;
	private int idVuelo;
	private String tipoIdViajero;
	private String clase;
	public static final String ECONOMICA = "Economica";
	public static final String EJECUTIVA = "Ejecutiva";

	public ReservaViajeros(int id ,int idReservaGeneral, int idVuelo, int idViajero,String tipoIdViajero, String clase,String fecha,String tipoReserva, String aeroSal, String aeroLleg) {
		super(id,fecha,tipoReserva,aeroSal, aeroLleg);
		// TODO Auto-generated constructor stub
		this.idViajero=idViajero;
		this.idVuelo = idVuelo;
		this.tipoIdViajero = tipoIdViajero;
		this.setIdReservaGeneral(idReservaGeneral);
		this.setAeroSal(aeroSal);
		this.setAeroLleg(aeroLleg);
		this.setClase(clase);
	}
	public ReservaViajeros()
	{
		
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
	
	
	public int getIdReservaGeneral() {
		return idReservaGeneral;
	}
	public void setIdReservaGeneral(int idReservaGeneral) {
		this.idReservaGeneral = idReservaGeneral;
	}
	
	public int getIdVuelo() {
		return idVuelo;
	}
	public void setIdVuelo(int idVuelo) {
		this.idVuelo = idVuelo;
	}
	
	

}
