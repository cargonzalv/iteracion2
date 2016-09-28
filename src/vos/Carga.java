package vos;

public class Carga {
	private int trackingNumber;
	private String contenido;
	private double peso;
	private double voulumen;
	public Carga(int trackingNumber, String contenido, double peso, double voulumen) {
		super();
		this.trackingNumber = trackingNumber;
		this.contenido = contenido;
		this.peso = peso;
		this.voulumen = voulumen;
	}
	public int getTrackingNumber() {
		return trackingNumber;
	}
	public void setTrackingNumber(int trackingNumber) {
		this.trackingNumber = trackingNumber;
	}
	public String getContenido() {
		return contenido;
	}
	public void setContenido(String contenido) {
		this.contenido = contenido;
	}
	public double getPeso() {
		return peso;
	}
	public void setPeso(double peso) {
		this.peso = peso;
	}
	public double getVoulumen() {
		return voulumen;
	}
	public void setVoulumen(double voulumen) {
		this.voulumen = voulumen;
	}
	

}
