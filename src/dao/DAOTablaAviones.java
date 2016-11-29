package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import vos.Aeropuerto;
import vos.Avion;
import vos.AvionCarga;
import vos.AvionViajeros;
import vos.ConsultaAvionCarga;
import vos.ConsultaAvionViajeros;
import vos.Usuarios;
import vos.Vuelo;
import vos.ViajeCarga;
import vos.ViajeViajeros;

public class DAOTablaAviones {
	ArrayList<Avion> aviones;

	private Connection conn;
	public DAOTablaAviones(){
		aviones= new ArrayList<>();
		try {
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void cerrarAviones() {
		for(Object ob : aviones){
			if(ob instanceof PreparedStatement)
				try {
					((PreparedStatement) ob).close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
		}
	}
	
	public void setConn(Connection con){
		this.conn = con;
	}
	public Aeropuerto getAeropuerto(String idAeropuerto) throws SQLException {
		Aeropuerto aeropuerto=null;
		String sql="SELECT * FROM ISIS2304A131620.AEROPUERTO WHERE COD_IATA ='"+idAeropuerto+"'";
		PreparedStatement prepStmt= conn.prepareStatement(sql);
		ResultSet rs=prepStmt.executeQuery();
		if(rs.next()){
			String id= rs.getString("COD_IATA");
			String nombre = rs.getString("NOMBRE");
			String tipo = rs.getString("TIPO");
			String nombreCiudad = rs.getString("NOMBRE_CIUDAD");
			aeropuerto = new Aeropuerto(id,nombre,tipo,nombreCiudad);
					
		}
		return aeropuerto;
	}
	public Avion darAvion(String codigo) throws SQLException, Exception {
		Avion avion = null;

		String sql = "SELECT * FROM ISIS2304A131620.AVION WHERE NUM_SERIE = '"+codigo+"'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);

		ResultSet rs = prepStmt.executeQuery();

		if (rs.next()) {
			String id= rs.getString("NUM_SERIE");
			String modelo = rs.getString("MODELO");
			String marca = rs.getString("MARCA");
			String tipo = rs.getString("TIPO");
			int anioFab = rs.getInt("ANIO_FABRICACION");
			avion = new Avion(id,modelo,marca,anioFab, tipo);
		}
		return avion;
	}

	public AvionCarga darAvionCarga(String codigo) throws SQLException, Exception {
		AvionCarga avionCarga = null;

		String sql = "SELECT * FROM ISIS2304A131620.AVION_CARGA WHERE NUM_SERIE = '"+codigo+"'";
		Avion avion = darAvion(codigo);
		PreparedStatement prepStmt = conn.prepareStatement(sql);

		ResultSet rs = prepStmt.executeQuery();

		if (rs.next()) {
			int capacidad = rs.getInt("CAPACIDAD");
			avionCarga = new AvionCarga(avion.getNumSerie(), avion.getModelo(), avion.getMarca(), avion.getAnioFabrica(), avion.getTipo(),capacidad);
		}
		return avionCarga;
	}
	
//	public ConsultaAvionViajeros consultarAvionViajeros(AvionViajeros avion) throws Exception
//	{
//		DAOTablaVuelos daoVuelos = new DAOTablaVuelos();
//		ArrayList<ViajeViajeros> vuelos = daoVuelos.getViajesViajerosRealizadosAvion(avion.getNumSerie());
//		double distancia = 0;
//		for(int i = 0; i < vuelos.size(); i++)
//		{
//			distancia+= vuelos.get(i).getDistancia();
//		}
//		return new ConsultaAvionViajeros(avion.getNumSerie(), avion.getModelo(), avion.getMarca(), avion.getAnioFabrica(), avion.getTipo(), vuelos, distancia, avion.getSillasEconomicas(), avion.getSillasEjecutivas() );
//	}
//	
//	public ConsultaAvionCarga consultarAvionCarga(AvionCarga avion) throws SQLException, ParseException
//	{
//		DAOTablaVuelos daoVuelos = new DAOTablaVuelos();
//		ArrayList<ViajeCarga> vuelos = daoVuelos.getVuelosCargaRealizadosAvion(avion.getNumSerie());
//		double distancia = 0;
//		for(int i = 0; i < vuelos.size(); i++)
//		{
//			distancia+= vuelos.get(i).getDistancia();
//		}
//		return new ConsultaAvionCarga(avion.getNumSerie(), avion.getModelo(), avion.getMarca(), avion.getAnioFabrica(), avion.getTipo(), vuelos, distancia, avion.getCargaMax());
//	}

	public AvionViajeros darAvionViajeros(String codigo) throws SQLException, Exception {
		AvionViajeros avionViajeros = null;

		String sql = "SELECT * FROM ISIS2304A131620.AVION_VIAJEROS WHERE NUM_SERIE = '"+codigo+"'";
		Avion avion = darAvion(codigo);
		PreparedStatement prepStmt = conn.prepareStatement(sql);

		ResultSet rs = prepStmt.executeQuery();

		if (rs.next()) {
			int sillEjec = rs.getInt("SILLAS_EJECUTIVAS");
			int sillEcon = rs.getInt("SILLAS_ECONOMICAS");
			avionViajeros = new AvionViajeros(avion.getNumSerie(), avion.getModelo(), avion.getMarca(), avion.getAnioFabrica(),avion.getTipo(),sillEjec, sillEcon);
		}
		return avionViajeros;
	}


//	
//	public ArrayList<Object> darAerolinasVuelosUsuarios(String idAerolinea) throws SQLException{
//		ArrayList<Vuelo> listaVuelos= new ArrayList<>();
//		ArrayList<Object> lista = new ArrayList<Object>();
//		String sql= "SELECT * FROM ISIS2304A131620.VUELOS WHERE AEROLINEA="+idAerolinea;
//		PreparedStatement pst= conn.prepareStatement(sql);
//		ResultSet res = pst.executeQuery();
//		while (res.next()) {
//			int id= res.getInt("ID");
//			Date horaSalida= res.getDate("HORA_SALIDA");
//			Date horaLlegada= res.getDate("HORA_LLEGADA");
//			String duracion =res.getString("DURACIOM");
//			double distancia=res.getDouble("DISTANCIA");
//			int frecuencia =res.getInt("FRECUENCIA");
//			String tipoViaje= res.getString("TIPO_VIAJE");
//			String aerolina= res.getString("AEROLINEA");
//			String avion= res.getString("Avion");
//			String realizado=res.getString("REALIZADO");
//			String aeropuertoLL=res.getString("AEROPUERTO_LLEGADA");
//			String aeropuertoSA=res.getString("AEROPUERTO_SALIDA");
//			Aeropuerto aeropuertoLLegada = getAeropuerto(aeropuertoLL);
//			Aeropuerto aeropuertoSalida= getAeropuerto(aeropuertoSA);
//			boolean realizadoo = false;
//			if (realizado.equals("N"))
//			{}
//			else if(realizado.equals("S"))
//			{
//				realizadoo = true;
//			}
//			String tipoVuelo = res.getString("TIPO_VUELO");
//			listaVuelos.add(new Vuelo(id, horaSalida, horaLlegada, frecuencia, tipoViaje, aerolina, avion, aeropuertoLLegada, aeropuertoSalida, distancia, duracion, realizadoo,tipoVuelo));
//			lista.add(new Vuelo(id, horaSalida, horaLlegada, frecuencia, tipoViaje, aerolina, avion, aeropuertoLLegada, aeropuertoSalida, distancia, duracion, realizadoo,tipoVuelo));
//
//		}
//		for(Vuelo ob: listaVuelos)
//		{
//			ArrayList<Integer> listaUsuarios=new ArrayList<>();
//			sql="SELECT * FROM ISIS2304A131620.RESERVA_VIAJES WHERE ID_VUELO"+ ob.getId()+";";
//			pst=conn.prepareStatement(sql);
//			res=pst.executeQuery();
//			while(res.next()){
//				int idUsuario = res.getInt("ID_VIAJEROS");
//				listaUsuarios.add(idUsuario);
//			}
//			for(Integer ob2: listaUsuarios){
//				sql = "SELECT * FROM ISIS2304A131620.USUARIOS WHERE ID = "+ob2+";";
//				pst = conn.prepareStatement(sql);
//				res = pst.executeQuery();
//				if(res!= null)
//				{
//					int id = res.getInt("ID");
//					String tipoId = res.getString("TIPO_ID");
//					String nombre = res.getString("NOMBRE");
//					String rol = res.getString("ROL");
//					String clave = res.getString("CLAVE");
//					String aerolinea = res.getString("AEROLINEA_FRECUENTE");
//					lista.add(new Usuarios(id, tipoId, nombre, rol, clave, aerolinea));
//
//
//
//				}
//			} 
//		}
//		return lista;
//
//
//	}
}
