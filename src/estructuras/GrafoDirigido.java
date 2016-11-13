package estructuras;

import java.util.List;

import vos.Aeropuerto;
import vos.VueloViajeros;

public class GrafoDirigido {

	private final List<Aeropuerto> vertices;
	private final List<VueloViajeros> arcos;

	public GrafoDirigido(List<Aeropuerto> vertices, List<VueloViajeros> arcos) {
		this.vertices = vertices;
		this.arcos = arcos;
	}

	public List<Aeropuerto> getVertexes() {
		return vertices;
	}

	public List<VueloViajeros> getEdges() {
		return arcos;
	}



}
