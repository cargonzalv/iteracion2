package vos;

public class ReservaCarga extends Reserva {
	private int idVuelo;
	private int idRemitente;
	private int idCarga;

	public ReservaCarga(int id, int idVuelo, int idRemitente, int idCarga) {
		super(id);
		// TODO Auto-generated constructor stub
		this.idCarga=idCarga;
		this.idRemitente= idRemitente;
		this.idVuelo=idVuelo;
		
			
		
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
	

}
