package vos;

public class Ciudad {
	private String nombre;
	private String nombrePais;
	public Ciudad(String nombre, String nombrePais) {
		super();
		this.nombre = nombre;
		this.nombrePais = nombrePais;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getNombrePais() {
		return nombrePais;
	}
	public void setNombrePais(String nombrePais) {
		this.nombrePais = nombrePais;
	}

}
