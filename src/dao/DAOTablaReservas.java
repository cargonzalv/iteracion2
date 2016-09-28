package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.ReservaCarga;
import vos.ReservaViaje;

public class DAOTablaReservas {
	private ArrayList<Object> reservas;
	private Connection conn;
	public DAOTablaReservas(){
		reservas= new ArrayList<>();
	}
	public void cerrarReservas() {
		for(Object ob : reservas){
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
	public ReservaViaje getReservaViaje(int idReserva) throws SQLException {
		ReservaViaje reserva= null;
		String sql= "SELECT * FROM ISIS2304A131620.RESERVA_VIAJEROS WHERE ID ="+idReserva;
		PreparedStatement prepStmt= conn.prepareStatement(sql);
		ResultSet rs=prepStmt.executeQuery();
		if(rs!=null){
			int id= rs.getInt("ID");
			int idVuelo=rs.getInt("ID_VUELO");
			int idViajero=rs.getInt("ID_VIAJEROS");
			reserva=new ReservaViaje(id, idVuelo, idViajero);
		}
		return reserva;
	}
	public void asociarViajero(int idReserva, int idViajero) throws SQLException {
		ReservaViaje reserva= getReservaViaje(idReserva);
		reserva.setIdViajero(idViajero);
		updateReservaViaje(reserva);
		
	}
	public void updateReservaViaje(ReservaViaje reserva) throws SQLException {
		String sql ="UPDATE ISIS2304A131620.RESERVAS_VIAJEROS SET ID_VIAJEROS="+reserva.getIdViajero()+"WHERE ID="+reserva.getId();
		PreparedStatement prepStmt=conn.prepareStatement(sql);
		prepStmt.executeQuery();
		
	}
	public void asociarCarga(int idReserva, int idCarga) throws SQLException {
		ReservaCarga reserva= getReservaCarga(idReserva);
		reserva.setIdCarga(idCarga);
		updateReservaCarga(reserva);
		
	}
	public void updateReservaCarga(ReservaCarga reserva) throws SQLException {
		String sql ="UPDATE ISIS2304A131620.RESERVAS_VIAJEROS SET ID_CARGA"+reserva.getIdCarga()+"WHERE ID="+reserva.getId();
		PreparedStatement prepStmt=conn.prepareStatement(sql);
		prepStmt.executeQuery();
		
	}
	public ReservaCarga getReservaCarga(int idReserva) throws SQLException {
		ReservaCarga reserva= null;
		String sql= "SELECT * FROM ISIS2304A131620.RESERVA_CARGA WHERE ID ="+idReserva;
		PreparedStatement prepStmt= conn.prepareStatement(sql);
		ResultSet rs=prepStmt.executeQuery();
		if(rs!=null){
			int id= rs.getInt("ID");
			int idVuelo=rs.getInt("ID_VUELO");
			int idRemitente=rs.getInt("ID_REMITENTE");
			int idCarga= rs.getInt("ID_CARGA");
			reserva=new ReservaCarga(id, idVuelo, idRemitente,idCarga);
		}
		return reserva;
	}
	
	

}
