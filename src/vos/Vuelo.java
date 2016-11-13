
package vos;

import java.util.Date;

import org.codehaus.jackson.annotate.*;

/**
 * Clase que representa un Vuelo
 */
public class Vuelo {

	
	@JsonProperty(value="id")
	private int id;

	private Date horaSalida;
	
	private Date horaLlegada;
	
	private int frecuencia;
	
	private String aerolinea;
	private String avion;
	
	private boolean realizado;
	
	private String tipoViaje;
	
	private String tipoVuelo;

	
	@JsonProperty(value="aeropuertoLL")
	private Aeropuerto aeropuertoLL;
	
	@JsonProperty(value="aeropuertoSA")
	private Aeropuerto aeropuertoSA;
	
	@JsonProperty(value="Distancia")
	private double distancia;
	
	@JsonProperty(value="Duracion")
	private String duracion;
	
	public final static String VIAJEROS = "Viajeros";
	public final static String CARGA = "Carga";

	public Vuelo(int id, Date horaSalida, Date horaLlegada, int frecuencia,String tipoViaje, String aerolinea,
			String avion, Aeropuerto aeropuertoSA, Aeropuerto aeropuertoLL, double distancia, String duracion, boolean realizado, String tipoVuelo) {
		super();
		this.id = id;
		this.horaSalida = horaSalida;
		this.horaLlegada = horaLlegada;
		this.frecuencia = frecuencia;
		this.aerolinea = aerolinea;
		this.avion = avion;
		this.aeropuertoLL = aeropuertoLL;
		this.aeropuertoSA = aeropuertoSA;
		this.distancia = distancia;
		this.duracion = duracion;
		this.realizado = realizado;
		this.tipoViaje = tipoViaje;
		this.tipoVuelo = tipoVuelo;
	}

	public boolean isRealizado() {
		return realizado;
	}

	public void setRealizado(boolean realizado) {
		this.realizado = realizado;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getHoraSalida() {
		return horaSalida;
	}

	public void setHoraSalida(Date horaSalida) {
		this.horaSalida = horaSalida;
	}

	public Date getHoraLlegada() {
		return horaLlegada;
	}

	public void setHoraLlegada(Date horaLlegada) {
		this.horaLlegada = horaLlegada;
	}

	public int getFrecuencia() {
		return frecuencia;
	}

	public void setFrecuencia(int frecuencia) {
		this.frecuencia = frecuencia;
	}

	

	public String getAerolinea() {
		return aerolinea;
	}

	public void setAerolinea(String aerolinea) {
		this.aerolinea = aerolinea;
	}

	public String getAvion() {
		return avion;
	}

	public void setAvion(String avion) {
		this.avion = avion;
	}

	public Aeropuerto getAeropuertoLL() {
		return aeropuertoLL;
	}

	public void setAeropuertoLL(Aeropuerto aeropuertoLL) {
		this.aeropuertoLL = aeropuertoLL;
	}

	public Aeropuerto getAeropuertoSA() {
		return aeropuertoSA;
	}

	public void setAeropuertoSA(Aeropuerto aeropuertoSA) {
		this.aeropuertoSA = aeropuertoSA;
	}

	public double getDistancia() {
		return distancia;
	}

	public void setDistancia(double distancia) {
		this.distancia = distancia;
	}

	public String getDuracion() {
		return duracion;
	}

	public void setDuracion(String duracion) {
		this.duracion = duracion;
	}

	public String getTipoViaje() {
		return tipoViaje;
	}

	public void setTipoViaje(String tipoVuelo) {
		this.tipoViaje = tipoVuelo;
	}

	public String getTipoVuelo() {
		return tipoVuelo;
	}

	public void setTipoVuelo(String tipoVuelo) {
		this.tipoVuelo = tipoVuelo;
	}

	
	
	
	



}
