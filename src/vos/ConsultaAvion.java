package vos;

public class ConsultaAvion {
	public static final String CARGA = "Carga";
	public static final String VIAJEROS = "Viajeros";
	private String numSerie;
	private String modelo;
	private String marca;
	private int anioFabrica;
	private String tipo;
	private double millas;
	/**
	 * @param numSerie
	 * @param modelo
	 * @param marca
	 * @param anioFabrica
	 * @param tipo
	 */
	public ConsultaAvion(String numSerie, String modelo, String marca, int anioFabrica, String tipo, double millas) {
		super();
		this.numSerie = numSerie;
		this.modelo = modelo;
		this.marca = marca;
		this.anioFabrica = anioFabrica;
		this.tipo = tipo;
		this.setMillas(millas);
	}
	public String getNumSerie() {
		return numSerie;
	}
	public void setNumSerie(String numSerie) {
		this.numSerie = numSerie;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public int getAnioFabrica() {
		return anioFabrica;
	}
	public void setAnioFabrica(int anioFabrica) {
		this.anioFabrica = anioFabrica;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public static String getCarga() {
		return CARGA;
	}
	public static String getViajeros() {
		return VIAJEROS;
	}
	public double getMillas() {
		return millas;
	}
	public void setMillas(double millas) {
		this.millas = millas;
	}
}
