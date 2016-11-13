package vos;

import java.util.Date;

public class Reserva {
 private int id ;
 private String fecha;
 private String tipoReserva;
 private String aeroSal;
 private String aeroLleg;
 public static final String CARGA = "Carga";
 public static final String VIAJEROS = "Viajeros";

public Reserva(int id, String fecha,String tipoReserva, String aeroSal, String aeroLleg) {
	super();
	this.id = id;
	this.fecha = fecha;
	this.tipoReserva = tipoReserva;
	this.aeroSal = aeroSal;
	this.aeroLleg = aeroLleg;
}

public Reserva()
{
	
}

public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}

public String getFecha() {
	return fecha;
}

public void setFecha(String fecha) {
	this.fecha = fecha;
}

public String getTipoReserva() {
	return tipoReserva;
}

public void setTipoReserva(String tipoReserva) {
	this.tipoReserva = tipoReserva;
}

public String getAeroSal() {
	return aeroSal;
}

public void setAeroSal(String aeroSal) {
	this.aeroSal = aeroSal;
}

public String getAeroLleg() {
	return aeroLleg;
}

public void setAeroLleg(String aeroLleg) {
	this.aeroLleg = aeroLleg;
}
 
}
