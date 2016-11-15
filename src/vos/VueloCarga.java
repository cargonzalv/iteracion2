package vos;

import java.util.ArrayList;
import java.util.Date;


public class VueloCarga  extends Vuelo{
	public static final String TIPO="Carga";
	private double costoDensidad;
	private int pesoTotal;
	private ArrayList<Carga> cargas;
	public VueloCarga(int id, Date horaSalida, Date horaLlegada, int frecuencia, String tipoViaje, String aerolinea,
			String avion, Aeropuerto aeropuertoSA, Aeropuerto aeropuertoLL, double distancia, String duracion, boolean realizado,double costoDensidad) {
		super(id, horaSalida, horaLlegada, frecuencia, tipoViaje, aerolinea, avion, aeropuertoSA, aeropuertoLL, distancia,
				duracion, realizado,TIPO);
		this.setCostoDensidad(costoDensidad);
		// TODO Auto-generated constructor stub
	}
	public double getCostoDensidad() {
		return costoDensidad;
	}
	public void setCostoDensidad(double costoDensidad) {
		this.costoDensidad = costoDensidad;
	}
	public int getPesoTotal() {
		return pesoTotal;
	}
	public void setPesoTotal(int pesoTotal) {
		this.pesoTotal = pesoTotal;
	}
	public ArrayList<Carga> getCargas() {
		return cargas;
	}
	public void setCargas(ArrayList<Carga> cargas) {
		this.cargas = cargas;
	}

	
	

}
