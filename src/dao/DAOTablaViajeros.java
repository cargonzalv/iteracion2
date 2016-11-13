package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Carga;
import vos.Usuarios;
import vos.Viajeros;

public class DAOTablaViajeros {
	private ArrayList<Object> cargas;
	private Connection conn;
	public DAOTablaViajeros(){
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


	public Viajeros getViajero(int idViajero, String tipoIdViajero) throws SQLException
	{
		Viajeros viajero=null;
		String sql="SELECT * FROM ISIS2304A131620.VIAJEROS WHERE ID ="+idViajero+"AND TIPO_ID = "+tipoIdViajero;
		PreparedStatement prepStmt= conn.prepareStatement(sql);
		ResultSet rs=prepStmt.executeQuery();
		if(rs.next()){
			Usuarios usuario = getUsuario(idViajero, tipoIdViajero);
			viajero = new Viajeros(idViajero, tipoIdViajero, usuario.getNombre(), usuario.getRol(), usuario.getClave(), usuario.getAerloneaF())	;			
		}
		return viajero;
	}
	
	public Usuarios getUsuario(int idUsuario, String tipoIdUsuario) throws SQLException
	{
		Usuarios usuario = null;
		String sql="SELECT * FROM ISIS2304A131620.USUARIO WHERE ID ="+idUsuario+"AND TIPO_ID = "+tipoIdUsuario;
		PreparedStatement prepStmt= conn.prepareStatement(sql);
		ResultSet rs=prepStmt.executeQuery();
		if(rs.next()){
			String nombre = rs.getString("NOMBRE");
			String rol = rs.getString("ROL");
			String clave = rs.getString("CLAVE");
			String aerolineaF = rs.getString("AEROLINEA_FRECUENTE");
			usuario = new Usuarios(idUsuario, tipoIdUsuario, nombre, rol, clave, aerolineaF);				
		}
		return usuario;
	}






}
