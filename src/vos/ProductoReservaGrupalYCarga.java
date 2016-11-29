package vos;

import java.util.ArrayList;
import java.util.Date;

public class ProductoReservaGrupalYCarga {

	private ArrayList<Reserva> reservasViajeros;
	private String idVuelo;
	private Viajeros viajeroEncargado;
	private String clase;
	private String fecha;
	private String tipoReserva;
	private String aeroSal;
	private String aeroLleg;
	private ArrayList<ReservaCarga> reservasCargas;
	/**
	 * @param reservasViajeros
	 * @param idVuelo
	 * @param clase
	 * @param fecha
	 * @param tipoReserva
	 * @param aeroSal
	 * @param aeroLleg
	 * @param reservasCargas
	 */
	public ProductoReservaGrupalYCarga(ArrayList<Reserva> reservasViajeros, String idVuelo, String clase,
			String fecha, String tipoReserva, String aeroSal, String aeroLleg, ArrayList<ReservaCarga> reservasCargas, Viajeros viajeroEncargado) {
		super();
		this.reservasViajeros = reservasViajeros;
		this.idVuelo = idVuelo;
		this.clase = clase;
		this.fecha = fecha;
		this.tipoReserva = tipoReserva;
		this.aeroSal = aeroSal;
		this.aeroLleg = aeroLleg;
		this.reservasCargas = reservasCargas;
		this.viajeroEncargado = viajeroEncargado;
	}
	public ArrayList<Reserva> getReservasViajeros() {
		return reservasViajeros;
	}
	public void setReservasViajeros(ArrayList<Reserva> reservasViajeros) {
		this.reservasViajeros = reservasViajeros;
	}
	public String getIdVuelo() {
		return idVuelo;
	}
	public void setIdVuelo(String idVuelo) {
		this.idVuelo = idVuelo;
	}
	public String getClase() {
		return clase;
	}
	public void setClase(String clase) {
		this.clase = clase;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getTipoReserva() {
		return tipoReserva;
	}
	public void setTipoReserva(String tipoReserva) {
		this.tipoReserva = tipoReserva;
	}
	public String getAeroSal() {
		return aeroSal;
	}
	public void setAeroSal(String aeroSal) {
		this.aeroSal = aeroSal;
	}
	public String getAeroLleg() {
		return aeroLleg;
	}
	public void setAeroLleg(String aeroLleg) {
		this.aeroLleg = aeroLleg;
	}
	public ArrayList<ReservaCarga> getReservasCargas() {
		return reservasCargas;
	}
	public void setReservasCargas(ArrayList<ReservaCarga> reservasCargas) {
		this.reservasCargas = reservasCargas;
	}
	public Viajeros getViajeroEncargado() {
		return viajeroEncargado;
	}
	public void setViajeroEncargado(Viajeros viajeroEncargado) {
		this.viajeroEncargado = viajeroEncargado;
	}
}
