package vos;

import java.util.ArrayList;
import java.util.Date;

public class CreacionReservaGrupalYCarga {

	private ArrayList<Integer> idViajeros;
	private ArrayList<String> tipoIdViajeros;
	private int idVuelo;
	private String clase;
	private String fecha;
	private String tipoReserva;
	private String aeroSal;
	private String aeroLleg;
	private ArrayList<Integer> idCargas;
	
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
	
	/**
	 * @param viajeros
	 * @param idVuelo
	 * @param clase
	 * @param fecha
	 * @param tipoReserva
	 * @param aeroSal
	 * @param aeroLleg
	 * @param cargas
	 */
	public CreacionReservaGrupalYCarga(ArrayList<Integer> viajeros,ArrayList<String> tipoIdViajeros, int vuelo, String clase, String fecha,
			String tipoReserva, String aeroSal, String aeroLleg, ArrayList<Integer> cargas) {
		super();
		this.setIdViajeros(viajeros);
		this.setTipoIdViajeros(tipoIdViajeros);
		this.idVuelo = vuelo;
		this.clase = clase;
		this.fecha = fecha;
		this.tipoReserva = tipoReserva;
		this.aeroSal = aeroSal;
		this.aeroLleg = aeroLleg;
		this.setIdCargas(cargas);
	}

	public int getIdVuelo() {
		return idVuelo;
	}
	public void setIdVuelo(int idVuelo) {
		this.idVuelo = idVuelo;
	}
	public ArrayList<Integer> getIdViajeros() {
		return idViajeros;
	}
	public void setIdViajeros(ArrayList<Integer> idViajeros) {
		this.idViajeros = idViajeros;
	}
	public ArrayList<Integer> getIdCargas() {
		return idCargas;
	}
	public void setIdCargas(ArrayList<Integer> idCargas) {
		this.idCargas = idCargas;
	}
	public ArrayList<String> getTipoIdViajeros() {
		return tipoIdViajeros;
	}
	public void setTipoIdViajeros(ArrayList<String> tipoIdViajeros) {
		this.tipoIdViajeros = tipoIdViajeros;
	}
}
