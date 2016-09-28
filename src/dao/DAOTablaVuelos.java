package dao;


import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;


import vos.Vuelo;




public class DAOTablaVuelos {


	
	private ArrayList<Object> vuelos;

	private Connection conn;

	
	public DAOTablaVuelos() {
		vuelos = new ArrayList<Object>();
	}

	
	public void cerrarVuelos() {
		for(Object ob : vuelos){
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


	public ArrayList<Vuelo> darVuelos() throws SQLException, Exception {
		ArrayList<Vuelo> vuelos = new ArrayList<Vuelo>();

		String sql = "SELECT * FROM ISIS2304A131620.VUELOS";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			int id= rs.getInt("ID");
			Date horaSalida= rs.getDate("HORA_SALIDA");
			Date horaLlegada=rs.getDate("HORA_LLEGADA");
			String duracion =rs.getString("DURACION");
			double distancia= rs.getDouble("DISTANCIA");
			int frecuencia =rs.getInt("FRECUENCIA");
			String tipoViaje= rs.getString("TIPO_VIAJE");
			String aerolinea = rs.getString("AEROLINEA");
			String avion = rs.getString("AVION");
			String aeropuertoLL= rs.getString("AEROPUESTO_SALIDA");
			String aeropuertoSA= rs.getString("AEROPUERTO_LLEGADA");
			vuelos.add(new Vuelo(id, horaSalida, horaLlegada, frecuencia, tipoViaje, aerolinea, avion, aeropuertoLL, aeropuertoSA, distancia, duracion));
		}
		return vuelos;
	}


	public void updateVuelo(Vuelo vuelo) throws SQLException{
		String sql ="UPDATE ISIS2304A131620.VUELOS SET AVION="+vuelo.getAvion()+"WHERE ID="+vuelo.getId();
	
		PreparedStatement prepStmt=conn.prepareStatement(sql);
		prepStmt.executeQuery();
		
	}


	
	public void deleteVuelo(Vuelo vuelo) throws SQLException, Exception {

		String sql = "DELETE FROM ISIS2304A131620.VUELOS";
		sql += " WHERE id = " + vuelo.getId();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		
		prepStmt.executeQuery();
	}

	public Vuelo getVuelo(int idVuelo) throws SQLException {
		Vuelo vuelo=null;
		String sql="SELECT * FROM ISIS2304A131620.VUELOS WHERE ID ="+idVuelo;
		PreparedStatement prepStmt= conn.prepareStatement(sql);
		ResultSet rs=prepStmt.executeQuery();
		if(rs!=null){
			int id= rs.getInt("ID");
			Date horaSalida= rs.getDate("HORA_SALIDA");
			Date horaLlegada=rs.getDate("HORA_LLEGADA");
			String duracion =rs.getString("DURACION");
			double distancia= rs.getDouble("DISTANCIA");
			int frecuencia =rs.getInt("FRECUENCIA");
			String tipoViaje= rs.getString("TIPO_VIAJE");
			String aerolinea = rs.getString("AEROLINEA");
			String avion = rs.getString("AVION");
			String aeropuertoLL= rs.getString("AEROPUESTO_SALIDA");
			String aeropuertoSA= rs.getString("AEROPUERTO_LLEGADA");
			vuelo=new Vuelo(id, horaSalida, horaLlegada, frecuencia, tipoViaje, aerolinea, avion, aeropuertoLL, aeropuertoSA, distancia, duracion);
					
		}
		return vuelo;
	}


	public void asociarVuelo(int idVuelo, String Avion) throws SQLException {
		Vuelo vuelo=null;
		vuelo=getVuelo(idVuelo);
		vuelo.setAvion(Avion);
		updateVuelo(vuelo);
		
		
	}
	public void viajeRealizado(int idVuelo) throws SQLException{
		String sql ="UPDATE ISIS2304A131620.VUELOS SET REALIZADO= Y WHERE ID="+idVuelo;
		
		PreparedStatement prepStmt=conn.prepareStatement(sql);
		prepStmt.executeQuery();
	}
	
	public ArrayList<Vuelo> darVuelosEnFechas(Date d1, Date d2){
		return null;
	}


	public ArrayList<Vuelo> getVuelosMasPopulares() {
		return null;
		
	}

	
	

}