package vos;
import java.util.ArrayList;
import java.util.Date;

public class ConsultaViajes 
{

	
	
	public Aerolinea getAerolinea() {
		return aerolinea;
	}
	/**
	 * @param clase
	 * @param vuelos
	 * @param aerolinea
	 * @param fechaSalida
	 * @param fechaLlegada
	 * @param aeroSal
	 * @param aeroLleg
	 * @param millas
	 * @param tiempo
	 * @param costo
	 */
	public ConsultaViajes(ArrayList<ReservaViajeros> reservas, int idVuelo, Aerolinea aerolinea, String fechaSalida, String fechaLlegada,
			String aeroSal, String aeroLleg, double millas, double costo) {
		super();
		this.setIdVuelo(idVuelo);
		this.aerolinea = aerolinea;
		this.fechaSalida = fechaSalida;
		this.fechaLlegada = fechaLlegada;
		this.aeroSal = aeroSal;
		this.aeroLleg = aeroLleg;
		this.millas = millas;
		this.costo = costo;
		this.setReservas(reservas);
	}
	public void setAerolinea(Aerolinea aerolinea) {
		this.aerolinea = aerolinea;
	}
	public String getFechaSalida() {
		return fechaSalida;
	}
	public void setFechaSalida(String fechaSalida) {
		this.fechaSalida = fechaSalida;
	}
	public String getFechaLlegada() {
		return fechaLlegada;
	}
	public void setFechaLlegada(String fechaLlegada) {
		this.fechaLlegada = fechaLlegada;
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
	public double getMillas() {
		return millas;
	}
	public void setMillas(double millas) {
		this.millas = millas;
	}
	
	public double getCosto() {
		return costo;
	}
	public void setCosto(double costo) {
		this.costo = costo;
	}
	
	public int getIdVuelo() {
		return idVuelo;
	}
	public void setIdVuelo(int idVuelo) {
		this.idVuelo = idVuelo;
	}

	public ArrayList<ReservaViajeros> getReservas() {
		return reservas;
	}
	public void setReservas(ArrayList<ReservaViajeros> reservas) {
		this.reservas = reservas;
	}

	private ArrayList<ReservaViajeros> reservas;
	private int idVuelo;
	private Aerolinea aerolinea;
	private String fechaSalida;
	private String fechaLlegada;
	private String aeroSal;
	private String aeroLleg;
	private double millas;
	private double costo;
	
}
	

