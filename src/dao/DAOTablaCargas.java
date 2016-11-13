package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Carga;

public class DAOTablaCargas {
	private ArrayList<Object> cargas;
	private Connection conn;
	public DAOTablaCargas(){
		cargas= new ArrayList<>();
	}
	public void cerrarReservas() {
		for(Object ob : cargas){
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


	public Carga getCarga(int idCarga) throws SQLException
	{
		Carga carga=null;
		String sql="SELECT * FROM ISIS2304A131620.CARGA WHERE TRACKING_NUM ="+idCarga;
		PreparedStatement prepStmt= conn.prepareStatement(sql);
		ResultSet rs=prepStmt.executeQuery();
		if(rs.next()){
			int id= rs.getInt("TRACKING_NUM");
			String contenido = rs.getString("CONTENIDO");
			double peso = rs.getFloat("PESO");
			double volumen = rs.getFloat("VOLUMEN");
			carga = new Carga(id, contenido, peso, volumen);					
		}
		return carga;
	}






}
