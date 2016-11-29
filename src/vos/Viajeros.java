package vos;

public class Viajeros extends Usuarios{

	private double millas;
	public final static String ROL="Viajero";
	
	public Viajeros(int id, String tipo, String nombre, String rol, String clave,
			String aerloneaF, double millas) {
		super(id, tipo, nombre, ROL, clave, aerloneaF);
		// TODO Auto-generated constructor stub
	}

	public double getMillas() {
		return millas;
	}

	public void setMillas(double millas) {
		this.millas = millas;
	}

}
