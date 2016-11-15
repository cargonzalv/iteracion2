package vos;

import java.util.Date;

public class Viaje extends Vuelo

{
	private int id;
	private Date horaSalida;

	private Date horaLlegada;

	private boolean realizado;
	private String tipoViaje;

	private String avion;


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


	public boolean isRealizado() {
		return realizado;
	}


	public void setRealizado(boolean realizado) {
		this.realizado = realizado;
	}


	public String getTipoViaje() {
		return tipoViaje;
	}


	public void setTipoViaje(String tipoViaje) {
		this.tipoViaje = tipoViaje;
	}


	public String getAvion() {
		return avion;
	}


	public void setAvion(String avion) {
		this.avion = avion;
	}


	public Viaje(int id, int idVuelo, Date horaSalida, Date horaLlegada, int frecuencia, String tipoViaje, String aerolinea,
			String avion, Aeropuerto aeropuertoSA, Aeropuerto aeropuertoLL, double distancia, int duracion,
			boolean realizado, String tipoVuelo ) 
	{
		super(idVuelo,frecuencia, aerolinea, aeropuertoSA, aeropuertoLL, distancia,
				duracion, tipoVuelo);
		// TODO Auto-generated constructor stub
		this.id = id;
		this.horaSalida = horaSalida;
		this.horaLlegada = horaLlegada;
		this.realizado = realizado;
		this.tipoViaje = tipoViaje;
		this.avion = avion;
		
	}

}
