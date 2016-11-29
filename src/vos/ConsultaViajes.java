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
	public ConsultaViajes(int idViajero, String tipoIdViajero, String idVuelo, Aerolinea aerolinea, String fechaSalida, String fechaLlegada,
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
		this.idViajero = idViajero;
		this.tipoIdViajero = tipoIdViajero;
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
	
	public String getIdVuelo() {
		return idVuelo;
	}
	public void setIdVuelo(String idVuelo) {
		this.idVuelo = idVuelo;
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

	private ArrayList<ReservaViajeros> reservas;
	private String idVuelo;
	private Aerolinea aerolinea;
	private String fechaSalida;
	private String fechaLlegada;
	private String aeroSal;
	private String aeroLleg;
	private double millas;
	private double costo;
	private int idViajero;
	private String tipoIdViajero;
	
}
	

