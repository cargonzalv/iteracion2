package estructuras;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import dao.DAOTablaVuelos;
import vos.Aeropuerto;
import vos.Vuelo;
import vos.ViajeViajeros;

public class Dijkstra {

	private final List<ViajeViajeros> Vuelos;
	private Set<Aeropuerto> settledNodes;
	private Set<Aeropuerto> unSettledNodes;
	private Map<String, String> predecessors;
	private Map<Aeropuerto, Integer> escalas;
	private DAOTablaVuelos daoVuelos;

	public Dijkstra(GrafoDirigido graph, DAOTablaVuelos daoVuelos) {
		// create a copy of the array so that we can operate on this array
		this.Vuelos = new ArrayList<ViajeViajeros>(graph.getEdges());
		this.daoVuelos = daoVuelos;

	}

	public void execute(Aeropuerto source) {
		settledNodes = new HashSet<Aeropuerto>();
		unSettledNodes = new HashSet<Aeropuerto>();
		escalas = new HashMap<Aeropuerto, Integer>();
		predecessors = new HashMap<String, String>();
		escalas.put(source, 0);
		unSettledNodes.add(source);
		while (unSettledNodes.size() > 0) {
			Aeropuerto node = getMinimum(unSettledNodes);
			settledNodes.add(node);
			unSettledNodes.remove(node);
			findMinimalDistances(node);
		}
	}

	private void findMinimalDistances(Aeropuerto node) {
		List<Aeropuerto> adjacentNodes = getNeighbors(node);
		for (Aeropuerto target : adjacentNodes) {
			if (getShortestEscalas(target) > getShortestEscalas(node)
					+ getDistance(node, target)) {
				escalas.put(target, getShortestEscalas(node)
						+ getDistance(node, target));
				predecessors.put(target.getCodigo(), node.getCodigo());
				unSettledNodes.add(target);
			}
		}

	}

	private Integer getDistance(Aeropuerto node, Aeropuerto target) {
		for (Vuelo Vuelo : Vuelos) {
			if (Vuelo.getAeropuertoSA().getCodigo().equals(node.getCodigo())
					&& Vuelo.getAeropuertoLL().getCodigo().equals(target.getCodigo())) {
				return 1;
			}
		}
		throw new RuntimeException("Should not happen");
	}



	private List<Aeropuerto> getNeighbors(Aeropuerto node) {
		List<Aeropuerto> neighbors = new ArrayList<Aeropuerto>();
		for (Vuelo Vuelo : Vuelos) {
			if (Vuelo.getAeropuertoSA().getCodigo().equals(node.getCodigo())
					&& !isSettled(Vuelo.getAeropuertoLL())) {
				neighbors.add(Vuelo.getAeropuertoLL());
			}
		}
		return neighbors;
	}

	private Aeropuerto getMinimum(Set<Aeropuerto> Aeropuertoes) {
		Aeropuerto minimum = null;
		for (Aeropuerto Aeropuerto : Aeropuertoes) {
			if (minimum == null) {
				minimum = Aeropuerto;
			} else {
				if (getShortestEscalas(Aeropuerto) < getShortestEscalas(minimum)) {
					minimum = Aeropuerto;
				}
			}
		}
		return minimum;
	}

	private boolean isSettled(Aeropuerto Aeropuerto) {
		return settledNodes.contains(Aeropuerto);
	}

	private int getShortestEscalas(Aeropuerto destination) {
		Integer d = escalas.get(destination);
		if (d == null) {
			return Integer.MAX_VALUE;
		} else {
			return d;
		}
	}

	/*
	 * This method returns the path from the source to the selected target and
	 * NULL if no path exists
	 * */
	public LinkedList<String> getPath(Aeropuerto target) {
		LinkedList<String> path = new LinkedList<String>();
		String step = target.getCodigo();
		// check if a path exists
		if (predecessors.get(step) == null) {
			return null;
		}
		path.add(step);
		while (predecessors.get(step) != null) {
			step = predecessors.get(step);
			path.add(step);
		}
		// Put it into the correct order
		Collections.reverse(path);
		return path;
	}
//	public ArrayList<ViajeViajeros> getEscalas(String source, String target) throws SQLException, ParseException {
//		ArrayList<ViajeViajeros> path = new ArrayList<ViajeViajeros>();
//		String step = target;
//		// check if a path exists
//		if (predecessors.get(step) == null) {
//			return null;
//		}
//		while (predecessors.get(step) != null && !predecessors.get(step).equals(source)) {
//			String step1 = step;
//			String step2 = predecessors.get(step);
//
//			path.add(daoVuelos.getVueloViajerosAeropuertos(step2, step1));
//
//			step = predecessors.get(step);
//		}
//		path.add(daoVuelos.getVueloViajerosAeropuertos(source, step));
//		// Put it into the correct order
//		Collections.reverse(path);
//		return path;
//	}


}

