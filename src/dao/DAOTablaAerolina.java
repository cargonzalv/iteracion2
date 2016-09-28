package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import vos.Aerolinea;
import vos.Usuarios;
import vos.Vuelo;

public class DAOTablaAerolina {
	ArrayList<Aerolinea> aerolineas;
	private Connection conn;
	public DAOTablaAerolina(){
		aerolineas= new ArrayList<>();
	}
	
	public void cerrarReservas() {
		for(Object ob : aerolineas){
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
	public ArrayList<Object> darAerolinasVuelosUsuarios(String idAerolinea) throws SQLException{
		ArrayList<Object> lista= new ArrayList<>();
		String sql= "SELECT * FROM ISIS2304A131620.VUELOS WHERE AEROLINEA="+idAerolinea;
		PreparedStatement pst= conn.prepareStatement(sql);
		ResultSet res = pst.executeQuery();
		while (res.next()) {
			int id= res.getInt("ID");
			Date horaSalida= res.getDate("HORA_SALIDA");
			Date horaLlegada= res.getDate("HORA_LLEGADA");
			String duracion =res.getString("DURACIOM");
			double distancia=res.getDouble("DISTANCIA");
			int frecuencia =res.getInt("FRECUENCIA");
			String tipoViaje= res.getString("TIPO_VIAJE");
			String aerolina= res.getString("AEROLINEA");
			String avion= res.getString("Avion");
			String realizado=res.getString("REALIZADO");
			String aeropuertoLL=res.getString("AEROPUERTO_LLEGADA");
			String aeropuertoSA=res.getString("AEROPUERTO_SALIDA");
			lista.add(new Vuelo(id, horaSalida, horaLlegada, frecuencia, tipoViaje, aerolina, avion, aeropuertoLL, aeropuertoSA, distancia, duracion));
			
		}
		for(Object ob: lista){
			ArrayList<int> listaUsuarios=new ArrayList<>();
			sql="SELEC * FROM ISIS2304A131620.RESERVA_VIAJES WHERE ID_VUELO"+(Vuelo)ob.getId();
			pst=conn.prepareStatement(sql);
			res=pst.executeQuery();
			while(res.next()){
				
			}
		
		}
		
		
	}
}
