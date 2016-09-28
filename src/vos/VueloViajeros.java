package vos;

import oracle.sql.DATE;

public class VueloViajeros extends Vuelo {
	public final static String TIPO="Viaje";

	public VueloViajeros(int id, DATE horaSalida, DATE horaLlegada, int frecuencia, String tipoViaje, String aerolinea,
			String avion, String aeropuertoLL, String aeropuertoSA, double distancia, int duracion) {
		super(id, horaSalida, horaLlegada, frecuencia, TIPO, aerolinea, avion, aeropuertoLL, aeropuertoSA, distancia,
				duracion);
		// TODO Auto-generated constructor stub
	}

}
