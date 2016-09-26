
package vos;

import org.codehaus.jackson.annotate.*;

/**
 * Clase que representa un Usuario
 */
public class Usuarios {

	
	@JsonProperty(value="id")
	private int id;

	
	@JsonProperty(value="name")
	private String name;

	
	@JsonProperty(value="duration")
	private int duration;

	

}
