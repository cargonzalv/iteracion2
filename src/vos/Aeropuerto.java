package vos;

public class Aeropuerto {

	private String codigo;
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getNombreCiudad() {
		return nombreCiudad;
	}
	public void setNombreCiudad(String nombreCiudad) {
		this.nombreCiudad = nombreCiudad;
	}
	/**
	 * @param codigo
	 * @param nombre
	 * @param tipo
	 * @param nombreCiudad
	 */
	public Aeropuerto(String codigo, String nombre, String tipo, String nombreCiudad) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		this.tipo = tipo;
		this.nombreCiudad = nombreCiudad;
	}
	private String nombre;
	private String tipo;
	private String nombreCiudad;
	
}
