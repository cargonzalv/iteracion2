package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Aerolinea;

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
	
	public Aerolinea getAerolinea(String idAeropuerto) throws SQLException {
		Aerolinea aeropuerto=null;
		String sql="SELECT * FROM ISIS2304A131620.AEROLINEA WHERE COD_IATA ='"+idAeropuerto+"'";
		PreparedStatement prepStmt= conn.prepareStatement(sql);
		ResultSet rs=prepStmt.executeQuery();
		if(rs.next()){
			String id= rs.getString("COD_IATA");
			String nombre = rs.getString("NOMBRE");
			aeropuerto = new Aerolinea(id,nombre);
					
		}
		return aeropuerto;
	}
//	public ArrayList<Object> darAerolinasVuelosUsuarios(String idAerolinea) throws SQLException{
//		ArrayList<Vuelo> listaVuelos= new ArrayList<>();
//		ArrayList<Object> lista = new ArrayList();
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
//					lista.add(new Usuarios(id, tipoId, nombre, rol, clave, "", aerolinea));
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
