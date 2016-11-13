
package vos;

import java.util.List;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Clase que representa una arreglo de Video
 * @author Juan
 */
public class ListaVuelos {
	
	/**
	 * List con los vuelos
	 */
	@JsonProperty(value="vuelos")
	private List<Vuelo> vuelos;
	
	/**
	 * Constructor de la clase Listavuelos
	 * @param vuelos - vuelos para agregar al arreglo de la clase
	 */
	public ListaVuelos( @JsonProperty(value="vuelos")List<Vuelo> vuelos){
		this.vuelos = vuelos;
	}

	/**
	 * Método que retorna la lista de vuelos
	 * @return  List - List con los vuelos
	 */
	public List<Vuelo> getvuelos() {
		return vuelos;
	}

	/**
	 * Método que asigna la lista de vuelos que entra como parametro
	 * @param  vuelos - List con los vuelos ha agregar
	 */
	public void setVideo(List<Vuelo> vuelos) {
		this.vuelos = vuelos;
	}
	
}