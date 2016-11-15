package vos;

import java.util.Date;

public class ViajeCarga  extends Viaje{
	public static final String TIPO="Carga";
	private double costoDensidad;
	private double pesoTotal;
	public ViajeCarga(int id,int idVuelo, Date horaSalida, Date horaLlegada, int frecuencia, String tipoVuelo, String aerolinea,
			String avion, Aeropuerto aeropuertoSA, Aeropuerto aeropuertoLL, double distancia, int duracion, boolean realizado,double costoDensidad) {
		super(id, idVuelo, horaSalida, horaLlegada, frecuencia, TIPO, aerolinea, avion, aeropuertoSA, aeropuertoLL, distancia,
				duracion, realizado,tipoVuelo);
		this.setCostoDensidad(costoDensidad);
		// TODO Auto-generated constructor stub
	}
	public double getCostoDensidad() {
		return costoDensidad;
	}
	public void setCostoDensidad(double costoDensidad) {
		this.costoDensidad = costoDensidad;
	}
	public double getPesoTotal() {
		return pesoTotal;
	}
	public void setPesoTotal(double pesoTotal) {
		this.pesoTotal = pesoTotal;
	}
	

	
	

}
