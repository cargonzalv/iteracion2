
package vos;

import org.codehaus.jackson.annotate.*;

/**
 * Clase que representa un Usuario
 */
public class Usuarios {


	@JsonProperty(value="id")
	private int id;


	@JsonProperty(value="name")
	private String tipo;

	@JsonProperty(value="duration")
	private String nombre;

	private String rol;

	private String clave;
	private String nacionalidad;
	private String aerolineaFrecuentada;

	public Usuarios(int id, String tipo, String nombre, String rol, String clave, String nacionalidad,
			String aerloneaF) {
		super();
		this.id = id;
		this.tipo = tipo;
		this.nombre = nombre;
		this.rol = rol;
		this.clave = clave;
		this.nacionalidad = nacionalidad;
		this.aerloneaF = aerloneaF;
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
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getRol() {
		return rol;
	}
	public void setRol(String rol) {
		this.rol = rol;
	}
	public String getClave() {
		return clave;
	}
	public void setClave(String clave) {
		this.clave = clave;
	}
	public String getNacionalidad() {
		return nacionalidad;
	}
	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}
	public String getAerloneaF() {
		return aerloneaF;
	}
	public void setAerloneaF(String aerloneaF) {
		this.aerloneaF = aerloneaF;
	}




}
