package vos;

import java.util.Date;

public class ReservaCarga extends Reserva {
	private int idVuelo;
	private int idRemitente;
	private int idCarga;
	private String tipoIdRemitente;

	public ReservaCarga(int id, int idVuelo, int idRemitente, String tipoIdRemitente, int idCarga, String fecha, String tipoReserva, String aeroSal, String aeroLleg) {
		super(id,fecha,tipoReserva, aeroSal, aeroLleg);
		// TODO Auto-generated constructor stub
		this.idCarga=idCarga;
		this.idRemitente= idRemitente;
		this.idVuelo=idVuelo;
		this.setTipoIdRemitente(tipoIdRemitente);
		
			
		
	}

	public int getIdVuelo() {
		return idVuelo;
	}

	public void setIdVuelo(int idVuelo) {
		this.idVuelo = idVuelo;
	}

	public int getIdRemitente() {
		return idRemitente;
	}

	public void setIdRemitente(int idRemitente) {
		this.idRemitente = idRemitente;
	}

	public int getIdCarga() {
		return idCarga;
	}

	public void setIdCarga(int idCarga) {
		this.idCarga = idCarga;
	}

	public String getTipoIdRemitente() {
		return tipoIdRemitente;
	}

	public void setTipoIdRemitente(String tipoIdRemitente) {
		this.tipoIdRemitente = tipoIdRemitente;
	}
	

}
