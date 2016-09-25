
package vos;

import org.codehaus.jackson.annotate.*;

/**
 * Clase que representa un Vuelo
 */
public class Vuelo {

	
	@JsonProperty(value="id")
	private int id;

	
	@JsonProperty(value="Tipo")
	private String tipo;

	
	@JsonProperty(value="AeropuertoLL")
	private String AeropuertoLL;
	
	@JsonProperty(value="AeropuertoSA")
	private String AeropuertoSA;
	
	@JsonProperty(value="Distancia")
	private int distancia;
	
	@JsonProperty(value="Duracion")
	private int duracion;

	
	public Vuelo(@JsonProperty(value="id")int id, @JsonProperty(value="Tipo")String tipo,@JsonProperty(value="AeropuertoLL") String AeropuertoLL, @JsonProperty(value="AeropuertoSA") String AeropuertSA, @JsonProperty(value="Distancia") int distancia,@JsonProperty(value="Duracion") int duracion) {
		super();
		this.id = id;
		this.tipo = tipo;
		this.AeropuertoLL= AeropuertoLL;
		this.AeropuertoSA=AeropuertSA;
		this.duracion=duracion;
		this.distancia=distancia;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
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

	public int getDistancia() {
		return distancia;
	}

	public void setDistancia(int distancia) {
		this.distancia = distancia;
	}

	public int getDuracion() {
		return duracion;
	}

	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}
	
	



}
