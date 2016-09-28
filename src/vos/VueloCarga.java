package vos;

import oracle.sql.DATE;

public class VueloCarga  extends Vuelo{
	public final static String TIPO="Carga";
	public VueloCarga(int id, DATE horaSalida, DATE horaLlegada, int frecuencia, String tipoViaje, String aerolinea,
			String avion, String aeropuertoLL, String aeropuertoSA, double distancia, int duracion) {
		super(id, horaSalida, horaLlegada, frecuencia, TIPO, aerolinea, avion, aeropuertoLL, aeropuertoSA, distancia,
				duracion);
		// TODO Auto-generated constructor stub
	}

	
	

}
