package vos;

public class Aerolinea {
	private String Cod_IATA;
	private String nomvbre;
	
	public Aerolinea(String cod_IATA, String nomvbre) {
		super();
		this.Cod_IATA = cod_IATA;
		this.nomvbre = nomvbre;
	}
	public String getCod_IATA() {
		return Cod_IATA;
	}
	public void setCod_IATA(String cod_IATA) {
		Cod_IATA = cod_IATA;
	}
	public String getNomvbre() {
		return nomvbre;
	}
	public void setNomvbre(String nomvbre) {
		this.nomvbre = nomvbre;
	}

}
