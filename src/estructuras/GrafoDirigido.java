package estructuras;

import java.util.List;

import vos.Aeropuerto;
import vos.ViajeViajeros;

public class GrafoDirigido {

	private final List<Aeropuerto> vertices;
	private final List<ViajeViajeros> arcos;

	public GrafoDirigido(List<Aeropuerto> vertices, List<ViajeViajeros> arcos) {
		this.vertices = vertices;
		this.arcos = arcos;
	}

	public List<Aeropuerto> getVertexes() {
		return vertices;
	}

	public List<ViajeViajeros> getEdges() {
		return arcos;
	}



}
