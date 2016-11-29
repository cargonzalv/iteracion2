
package vos;

import java.util.Date;

import org.codehaus.jackson.annotate.*;

/**
 * Clase que representa un Vuelo
 */
public class Vuelo {

	
	@JsonProperty(value="id")
	private String id;

	
	
	private int frecuencia;
	
	private String aerolinea;
	
	
	
	private String tipoVuelo;

	
	@JsonProperty(value="aeropuertoLL")
	private Aeropuerto aeropuertoLL;
	
	@JsonProperty(value="aeropuertoSA")
	private Aeropuerto aeropuertoSA;
	
	@JsonProperty(value="Distancia")
	private double distancia;
	
	@JsonProperty(value="Duracion")
	private int duracion;
	
	public final static String VIAJEROS = "Viajeros";
	public final static String CARGA = "Carga";

	public Vuelo(String id, int frecuencia, String aerolinea,
			 Aeropuerto aeropuertoSA, Aeropuerto aeropuertoLL, double distancia, int duracion, String tipoVuelo) {
		super();
		this.id = id;
		this.frecuencia = frecuencia;
		this.aerolinea = aerolinea;
		this.aeropuertoLL = aeropuertoLL;
		this.aeropuertoSA = aeropuertoSA;
		this.distancia = distancia;
		this.duracion = duracion;
		this.tipoVuelo = tipoVuelo;
	}

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public int getDuracion() {
		return duracion;
	}

	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}



	public String getTipoVuelo() {
		return tipoVuelo;
	}

	public void setTipoVuelo(String tipoVuelo) {
		this.tipoVuelo = tipoVuelo;
	}

	
	
	
	



}
