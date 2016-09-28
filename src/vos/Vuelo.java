
package vos;

import java.util.Date;

import org.codehaus.jackson.annotate.*;
import oracle.sql.DATE;

/**
 * Clase que representa un Vuelo
 */
public class Vuelo {

	
	@JsonProperty(value="id")
	private int id;

	private Date horaSalida;
	
	private Date horaLlegada;
	
	private int frecuencia;
	
	private String tipoViaje;
	private String aerolinea;
	private String avion;

	
	@JsonProperty(value="AeropuertoLL")
	private String AeropuertoLL;
	
	@JsonProperty(value="AeropuertoSA")
	private String AeropuertoSA;
	
	@JsonProperty(value="Distancia")
	private double distancia;
	
	@JsonProperty(value="Duracion")
	private String duracion;

	public Vuelo(int id, Date horaSalida, Date horaLlegada, int frecuencia, String tipoViaje, String aerolinea,
			String avion, String aeropuertoLL, String aeropuertoSA, double distancia, String duracion) {
		super();
		this.id = id;
		this.horaSalida = horaSalida;
		this.horaLlegada = horaLlegada;
		this.frecuencia = frecuencia;
		this.tipoViaje = tipoViaje;
		this.aerolinea = aerolinea;
		this.avion = avion;
		AeropuertoLL = aeropuertoLL;
		AeropuertoSA = aeropuertoSA;
		this.distancia = distancia;
		this.duracion = duracion;
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

	public String getTipoViaje() {
		return tipoViaje;
	}

	public void setTipoViaje(String tipoViaje) {
		this.tipoViaje = tipoViaje;
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

	public String getAeropuertoLL() {
		return AeropuertoLL;
	}

	public void setAeropuertoLL(String aeropuertoLL) {
		AeropuertoLL = aeropuertoLL;
	}

	public String getAeropuertoSA() {
		return AeropuertoSA;
	}

	public void setAeropuertoSA(String aeropuertoSA) {
		AeropuertoSA = aeropuertoSA;
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

	
	
	
	



}
