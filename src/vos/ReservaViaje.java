package vos;

public class ReservaViaje extends Reserva{
	private int idVuelo;
	private int idViajero;
	public ReservaViaje(int id , int idVuelo, int idViajero) {
		super(id);
		// TODO Auto-generated constructor stub
		this.idViajero=idViajero;
		this.idVuelo=idVuelo;
	}
	public int getIdVuelo() {
		return idVuelo;
	}
	public void setIdVuelo(int idVuelo) {
		this.idVuelo = idVuelo;
	}
	public int getIdViajero() {
		return idViajero;
	}
	public void setIdViajero(int idViajero) {
		this.idViajero = idViajero;
	}
	


}
