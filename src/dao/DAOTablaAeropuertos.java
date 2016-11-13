package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Aeropuerto;

public class DAOTablaAeropuertos {
	ArrayList<Aeropuerto> aeropuertos;
	private Connection conn;
	public DAOTablaAeropuertos(){
		aeropuertos= new ArrayList<>();
	}

	public void cerrarAeropuertos() {
		for(Object ob : aeropuertos){
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
	
	public ArrayList<Aeropuerto> getAeropuertos() throws SQLException {
		Aeropuerto aeropuerto=null;
		String sql="SELECT * FROM ISIS2304A131620.AEROPUERTO";
		PreparedStatement prepStmt= conn.prepareStatement(sql);
		ResultSet rs=prepStmt.executeQuery();
		while(rs.next()){
			String id= rs.getString("COD_IATA");
			String nombre = rs.getString("NOMBRE");
			String tipo = rs.getString("TIPO");
			String nombreCiudad = rs.getString("NOMBRE_CIUDAD");
			aeropuerto = new Aeropuerto(id,nombre,tipo,nombreCiudad);
			aeropuertos.add(aeropuerto);
					
		}
		return aeropuertos;
	}
	
	
}
