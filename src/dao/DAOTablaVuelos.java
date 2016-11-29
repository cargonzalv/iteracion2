package dao;


import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import vos.Aerolinea;
import vos.Aeropuerto;
import vos.Avion;
import vos.AvionCarga;
import vos.AvionViajeros;
import vos.Carga;
import vos.ConsultaAvionViajeros;
import vos.ConsultaTraficoAereo;
import vos.ConsultaViajes;
import vos.Reserva;
import vos.ReservaCarga;
import vos.ReservaViajeros;
import vos.ReservaViajerosMultiple;
import vos.Usuarios;
import vos.Viaje;
import vos.Viajeros;
import vos.ReservaViajeros;
import vos.Vuelo;
import vos.ViajeCarga;
import vos.ViajeViajeros;




public class DAOTablaVuelos {



	private ArrayList<Vuelo> vuelos;

	private ArrayList<Aeropuerto> aeropuertos;

	private ArrayList<Avion> aviones;




	private Connection conn;


	public DAOTablaVuelos()  {
		vuelos = new ArrayList<Vuelo>();
		aeropuertos = new ArrayList<Aeropuerto>();
		aviones = new ArrayList<Avion>();

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


	public ArrayList<Avion> darAviones() throws SQLException, Exception {
		aviones = new ArrayList<Avion>();

		String sql = "SELECT * FROM ISIS2304A131620.AVION";

		PreparedStatement prepStmt = conn.prepareStatement(sql);

		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String id= rs.getString("NUM_SERIE");
			String modelo = rs.getString("MODELO");
			String marca = rs.getString("MARCA");
			int anioFab = rs.getInt("ANIO_FABRICACION");
			String tipo = rs.getString("TIPO");
			aviones.add(new Avion(id,modelo,marca,anioFab, tipo));
		}
		return aviones;
	}


	public ArrayList<Aeropuerto> darAeropuertos() throws SQLException, Exception {
		aeropuertos = new ArrayList<Aeropuerto>();

		String sql = "SELECT * FROM ISIS2304A131620.AEROPUERTO";

		PreparedStatement prepStmt = conn.prepareStatement(sql);

		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String id= rs.getString("COD_IATA");
			String nombre = rs.getString("NOMBRE");
			String tipo = rs.getString("TIPO");
			String nombreCiudad = rs.getString("NOMBRE_CIUDAD");
			aeropuertos.add(new Aeropuerto(id,nombre,tipo,nombreCiudad));
		}
		return aeropuertos;
	}

	public ArrayList<Vuelo> darVuelos() throws SQLException, Exception {
		ArrayList<Vuelo> vuelos = new ArrayList<Vuelo>();

		String sql = "SELECT * FROM ISIS2304A131620.VUELO";

		PreparedStatement prepStmt = conn.prepareStatement(sql);

		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String id= rs.getString("ID");
			int duracion =rs.getInt("DURACION");
			double distancia= rs.getDouble("DISTANCIA");
			int frecuencia =rs.getInt("FRECUENCIA");
			String aerolinea = rs.getString("AEROLINEA");
			String aeropuertoLL= rs.getString("AEROPUERTO_SALIDA");
			String aeropuertoSA= rs.getString("AEROPUERTO_LLEGADA");
			Aeropuerto aeropuertoLLegada = getAeropuerto(aeropuertoLL);
			Aeropuerto aeropuertoSalida= getAeropuerto(aeropuertoSA);
			
			String tipoVuelo = rs.getString("TIPO_VUELO");
			vuelos.add(new Vuelo(id,frecuencia,aerolinea,aeropuertoSalida,aeropuertoLLegada,distancia,duracion,tipoVuelo));
		}
		return vuelos;
	}


	public ArrayList<ViajeViajeros> getViajesViajeros() throws SQLException, ParseException {
		ArrayList<ViajeViajeros> vueloViajeros = new ArrayList<ViajeViajeros>();
		String sql="SELECT * FROM ISIS2304A131620.VIAJE_VIAJEROS";
		PreparedStatement prepStmt= conn.prepareStatement(sql);
		ResultSet rs=prepStmt.executeQuery();
		while(rs.next()){
			String id = rs.getString("ID");
			Date horaSalida = null;
			Date horaLlegada = null;
			Timestamp timestamp = rs.getTimestamp("HORA_SALIDA");
			if (timestamp != null)
				horaSalida = new Date(timestamp.getTime());
			Timestamp timestamp2 = rs.getTimestamp("HORA_LLEGADA");
			if(timestamp2 != null)
			{
				horaLlegada = new Date(timestamp2.getTime());
			}
			SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
			horaLlegada = format.parse(format.format(horaLlegada));
			horaSalida = format.parse(format.format(horaSalida));
			String tipoViaje= rs.getString("TIPO_VIAJE");
			String avion = rs.getString("AVION");
			String realizado = rs.getString("REALIZADO");
			boolean realizadoo = false;
			if (realizado.equals("N"))
			{}
			else if(realizado.equals("S"))
			{
				realizadoo = true;
			}			double costoEjec = rs.getDouble("COSTO_EJECUTIVO");
			double costoEcon = rs.getDouble("COSTO_ECONOMICO");
			Vuelo vueloT = getVuelo(id);

			vueloViajeros.add(new ViajeViajeros(vueloT.getId(), horaSalida, horaLlegada, vueloT.getFrecuencia(), tipoViaje,vueloT.getAerolinea(), avion, vueloT.getAeropuertoSA(), vueloT.getAeropuertoLL(), vueloT.getDistancia(), vueloT.getDuracion(), realizadoo, costoEjec, costoEcon));					
		}
		return vueloViajeros;
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
		rs.close();
		prepStmt.close();
		return aeropuerto;
	}


	public void deleteVuelo(Vuelo vuelo) throws SQLException, Exception {

		String sql = "DELETE FROM ISIS2304A131620.VUELO";
		sql += " WHERE id = " + vuelo.getId();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);

		prepStmt.executeQuery();

	}



//	public ViajeViajeros deleteVueloViajeros(String vueloViajeros) throws Exception
//	{
//		ViajeViajeros vuelo = getViajeViajeros(vueloViajeros, new Date());
//		DAOTablaReservas daoReservas = new DAOTablaReservas();
//		daoReservas.setConn(conn);
//		DAOTablaAeropuertos daoAeropuertos = new DAOTablaAeropuertos();
//		daoAeropuertos.setConn(conn);
//		ArrayList<ReservaViajeros> reservas =  daoReservas.getReservasViajerosPorVuelo(vuelo.getId());
//		ArrayList<ViajeViajeros> vuelos = getViajesViajeros();
//		ArrayList<ReservaViajeros> reservasACrear = new ArrayList<>();
//		for(int i = 0; i<reservas.size(); i++)
//		{
//			ReservaViajeros reservaActual = reservas.get(i);
//			if((reservaActual.getAeroSal().equals(vuelo.getAeropuertoSA().getCodigo()) && reservaActual.getAeroLleg().equals(vuelo.getAeropuertoLL().getCodigo())))
//			{
//				daoReservas.cancelarReservaViajeroVuelo(reservaActual.getIdReservaGeneral());
//				Date min = new Date(Long.MAX_VALUE);
//				SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
//				min = format.parse(format.format(min));
//				Vuelo ganador = null;
//				for(int j =0; j< vuelos.size(); j++)
//				{
//					ViajeViajeros actual = vuelos.get(j);
//					if( actual.getAeropuertoSA().getCodigo().equals(vuelo.getAeropuertoSA().getCodigo())&& actual.getAeropuertoLL().getCodigo().equals(vuelo.getAeropuertoLL().getCodigo())&& actual.getHoraSalida().after(vuelo.getHoraSalida())&& actual.getHoraSalida().before(min) )
//					{
//						min = actual.getHoraSalida();
//						ganador = actual;
//					}
//				}
//				if(ganador == null)
//				{
//					throw new Exception("No se ha podido encontrar otro vuelo para asignar las reservas");
//				}
//
//				conn.setSavepoint();
//
//
//				reservaActual.setIdVuelo(ganador.getId());
//
//				daoReservas.createReservaViajero(reservaActual);
//			}
//			else
//			{
//				conn.setSavepoint();
//				daoReservas.cancelarReservaViajeroVuelo(reservaActual.getIdReservaGeneral());
//				conn.setSavepoint();
//
//				reservasACrear.add(reservaActual);
//			}
//		}
//
//		String sql = "DELETE ISIS2304A131620.VUELO_VIAJEROS WHERE ID = " + vuelo.getId();
//
//
//		PreparedStatement prepStmt = conn.prepareStatement(sql);
//
//		prepStmt.executeUpdate();
//
//		conn.setSavepoint();
//		String sql2 = "DELETE ISIS2304A131620.VUELO WHERE ID = " + vuelo.getId();
//		PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
//
//		prepStmt2.executeUpdate();
//
//		for(int i = 0; i< reservasACrear.size(); i++)
//		{
//			ReservaViajeros actual = reservasACrear.get(i);
//			daoReservas.createReservaVuelosMultiples(actual, this);
//		}
//		return vuelo;
//
//	}
	public Vuelo getVuelo(String idVuelo) throws SQLException {
		Vuelo vuelo=null;
		String sql="SELECT * FROM ISIS2304A131620.VUELO WHERE ID ='"+idVuelo+"'";
		PreparedStatement prepStmt= conn.prepareStatement(sql);
		ResultSet rs=prepStmt.executeQuery();
		if(rs.next()){
			String id= rs.getString("ID");
			
			int duracion =rs.getInt("DURACION");
			double distancia= rs.getDouble("DISTANCIA");
			int frecuencia =rs.getInt("FRECUENCIA");
			String aerolinea = rs.getString("AEROLINEA");
			String aeropuertoSA= rs.getString("AEROPUERTO_SALIDA");
			String aeropuertoLL= rs.getString("AEROPUERTO_LLEGADA");
			Aeropuerto aeropuertoLLegada = getAeropuerto(aeropuertoLL);
			Aeropuerto aeropuertoSalida= getAeropuerto(aeropuertoSA);
			
			String tipoVuelo = rs.getString("TIPO_VUELO");
			vuelo = new Vuelo(id, frecuencia, aerolinea, aeropuertoSalida, aeropuertoLLegada, distancia, duracion, tipoVuelo);
		
		}
		rs.close();
		prepStmt.close();
		return vuelo;
	}

	
	public Viaje getViaje(String idViaje, Date horaSalida) throws SQLException, ParseException {
		SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");

		Viaje viaje=null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(horaSalida);
		int dia1 = cal.get(Calendar.DAY_OF_MONTH);
		int mes1 = cal.get(Calendar.MONTH)+1;
		int anio1 = cal.get(Calendar.YEAR);
		
		String sql="SELECT * FROM ISIS2304A131620.VIAJE WHERE ID_VUELO = '"+idViaje+"' AND EXTRACT (DAY FROM HORA_SALIDA) = "+dia1+" AND EXTRACT(MONTH FROM HORA_SALIDA) = "+mes1+" AND EXTRACT(YEAR FROM HORA_SALIDA) = "+anio1; 
		PreparedStatement prepStmt= conn.prepareStatement(sql);
		ResultSet rs=prepStmt.executeQuery();
		if(rs.next()){
			horaSalida = rs.getDate("HORA_SALIDA");
			Date horaLlegada = rs.getDate("HORA_LLEGADA");

			horaLlegada = format.parse(format.format(horaLlegada));
			horaSalida = format.parse(format.format(horaSalida));
			String tipoViaje= rs.getString("TIPO_VIAJE");
			String avion = rs.getString("AVION");
			String realizado = rs.getString("REALIZADO");
			boolean realizadoo = false;
			if (realizado.equals("N"))
			{}
			else if(realizado.equals("S"))
			{
				realizadoo = true;
			}
			Vuelo vuelo = getVuelo(idViaje);
			viaje = new Viaje(idViaje, horaSalida, horaLlegada, vuelo.getFrecuencia(), tipoViaje, vuelo.getAerolinea(), avion, vuelo.getAeropuertoSA(), vuelo.getAeropuertoLL(), vuelo.getDistancia(), vuelo.getDuracion(), realizadoo, vuelo.getTipoVuelo());

		}
		rs.close();
		prepStmt.close();
		return viaje;
	}

	public ViajeCarga getViajeCarga(String idVuelo, Date horaSalida) throws SQLException, ParseException {
		ViajeCarga vueloCarga=null;
		String sql="SELECT * FROM ISIS2304A131620.VIAJE_CARGA WHERE ID ='"+idVuelo+"'";
		PreparedStatement prepStmt= conn.prepareStatement(sql);
		ResultSet rs=prepStmt.executeQuery();
		if(rs.next()){
			
			Vuelo vueloT = getVuelo(idVuelo);
			Viaje viaje = getViaje(idVuelo, horaSalida);
			double costo = rs.getDouble("COSTO_DENSIDAD");

			vueloCarga = new ViajeCarga(idVuelo, horaSalida, viaje.getHoraLlegada(), vueloT.getFrecuencia(), viaje.getTipoViaje(), vueloT.getAerolinea(), viaje.getAvion(), vueloT.getAeropuertoSA(), vueloT.getAeropuertoLL(), vueloT.getDistancia(), vueloT.getDuracion(), viaje.isRealizado(), costo);					
		}
		return vueloCarga;
	}

	public ViajeViajeros getViajeViajerosAeropuertos(String aeroSal, String aeroLleg) throws SQLException, ParseException {
		ViajeViajeros vuelo=null;
		String sql="SELECT * FROM ISIS2304A131620.VUELO WHERE (AEROPUERTO_SALIDA ='" + aeroSal+ "' AND AEROPUERTO_LLEGADA = '" + aeroLleg+ "')";
		PreparedStatement prepStmt= conn.prepareStatement(sql);
		ResultSet rs=prepStmt.executeQuery();
		if(rs.next()){
			String id= rs.getString("ID");
			Date horaSalida = rs.getDate("HORA_SALIDA");

			vuelo = getViajeViajeros(id,horaSalida);
		}
		return vuelo;
	}
	public ResultSet queryVuelosViajerosPorViajero(int idViajero, String tipoIdViajero) throws SQLException, ParseException {
		String sql= "SELECT ID_VUELO FROM ((SELECT * FROM RESERVA_VIAJEROS WHERE ID_VIAJERO = "+idViajero+" AND "
				+ "TIPO_ID_VIAJERO = '"+tipoIdViajero+"')T1 JOIN "
				+ "(SELECT * FROM  VUELO_VIAJEROS)T2 ON T1.ID_VUELO = T2.ID) GROUP BY ID_VUELO";
		PreparedStatement prepStmt= conn.prepareStatement(sql);
		return prepStmt.executeQuery();
		
	}
	public ResultSet queryVuelosViajerosPorViajeroConParametros(int idViajero, String tipoIdViajero, String clase, double millas) throws SQLException, ParseException {
		String sql= "SELECT ID_VUELO FROM (((SELECT * FROM RESERVA_VIAJEROS WHERE ID_VIAJERO = "+idViajero+" AND "
				+ "TIPO_ID_VIAJERO = '"+tipoIdViajero+"')T1 JOIN "
				+"( SELECT * FROM VIAJEROS WHERE VIAJEROS.MILLAS_RECORRIDAS >"+millas+")T3 ON T3.ID=T1.ID_VIAJERO) JOIN"
				+ "(SELECT * FROM  VIAJE_VIAJEROS)T2 ON T1.ID_VUELO = T2.ID) GROUP BY ID_VUELO";
		PreparedStatement prepStmt= conn.prepareStatement(sql);
		return prepStmt.executeQuery();
		
	}
	
	public ArrayList<ViajeViajeros> getViajesViajerosPorViajero(ResultSet rs) throws SQLException, ParseException
	{
		ArrayList<ViajeViajeros> vuelos= new ArrayList<ViajeViajeros>();
		while(rs.next())
		{
			String id = rs.getString("ID_VUELO");
			Date horaSalida = rs.getDate("HORA_SALIDA");
			ViajeViajeros vuelo = getViajeViajeros(id,horaSalida);
	

				vuelos.add(vuelo);

		}
		return vuelos;
	}

//	public ArrayList<ConsultaViajes> consultarViajesViajero(int idViajero, String tipoIdViajero) throws Exception
//	{
//		DAOTablaAerolina daoAerolineas = new DAOTablaAerolina();
//		daoAerolineas.setConn(conn);
//		DAOTablaReservas daoReservas = new DAOTablaReservas();
//		daoReservas.setConn(conn);
//		ResultSet rs = queryVuelosViajerosPorViajero(idViajero, tipoIdViajero);
//		ArrayList<ViajeViajeros> vuelos = getVuelosViajerosPorViajero(rs);
//		
//		ArrayList<ConsultaViajes> consultas = new ArrayList<>();
//
//		for(int i = 0; i< vuelos.size(); i++)
//		{
//			ViajeViajeros vuelo = vuelos.get(i);
//			String aerolineaa = vuelo.getAerolinea();
//			Aerolinea aerolinea = daoAerolineas.getAerolinea(aerolineaa);
//
//			ArrayList<ReservaViajeros> reservas = daoReservas.getReservasViajerosPorVueloYViajero(idViajero,tipoIdViajero,vuelo.getId());
//
//			
//			String aeroLleg = vuelo.getAeropuertoLL().getCodigo();
//			String aeroSal = vuelo.getAeropuertoSA().getCodigo();
//			int idVuelo = vuelo.getId();
//
//			double costo = generarReporte(vuelo.getId(), daoReservas);
//			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//			String fechaLlegada = format.format(vuelo.getHoraLlegada());
//			String fechaSalida =format.format(vuelo.getHoraSalida());
//			Double millas = vuelo.getDistancia();
//
//			consultas.add(new ConsultaViajes(reservas, idVuelo, aerolinea, fechaSalida, fechaLlegada, aeroSal, aeroLleg, millas, costo));
//		}
//		return consultas;
//		
//	}




	public ViajeViajeros getViajeViajeros(String idVuelo, Date horaSalida) throws SQLException, ParseException {
		ViajeViajeros vueloViajeros=null;
		String sql="SELECT * FROM ISIS2304A131620.VIAJE_VIAJEROS WHERE ID ='"+idVuelo+"'";
		PreparedStatement prepStmt= conn.prepareStatement(sql);
		conn.setSavepoint();
		ResultSet rs=prepStmt.executeQuery();
		if(rs.next()){
			 
			Vuelo vueloT = getVuelo(idVuelo);
			Viaje viaje = getViaje(idVuelo, horaSalida);
			double costoEjec = rs.getDouble("COSTO_EJECUTIVO");
			double costoEcon = rs.getDouble("COSTO_ECONOMICO");
			vueloViajeros = new ViajeViajeros(idVuelo, horaSalida, viaje.getHoraLlegada(), vueloT.getFrecuencia(), viaje.getTipoViaje(), vueloT.getAerolinea(), viaje.getAvion(), vueloT.getAeropuertoSA(), vueloT.getAeropuertoLL(), vueloT.getDistancia(), vueloT.getDuracion(),viaje.isRealizado(), costoEjec, costoEcon);	
		}
		rs.close();
		prepStmt.close();
		return vueloViajeros;
	}



//	public ArrayList<ViajeViajeros> getViajesViajerosRealizadosAvion(String numSerieAvion) throws SQLException, ParseException
//	{
//		ArrayList<ViajeViajeros> vuelos = new ArrayList<ViajeViajeros>();
//		String sql="SELECT * FROM ISIS2304A131620.VUELO_VIAJEROS WHERE AVION = '"+numSerieAvion+"' AND REALIZADO = 'Y'";
//		PreparedStatement prepStmt= conn.prepareStatement(sql);
//		ResultSet rs=prepStmt.executeQuery();
//		while(rs.next()){
//
//			int idVuelo = rs.getInt("ID");
//			Vuelo vueloT = getVuelo(idVuelo);
//			double costoEjec = rs.getDouble("COSTO_EJECUTIVO");
//			double costoEcon = rs.getDouble("COSTO_ECONOMICO");
//			ViajeViajeros vuelo = new ViajeViajeros(idVuelo, vueloT.getHoraSalida(), vueloT.getHoraLlegada(), vueloT.getFrecuencia(), vueloT.getTipoViaje(), vueloT.getAerolinea(), vueloT.getAvion(), vueloT.getAeropuertoSA(), vueloT.getAeropuertoLL(), vueloT.getDistancia(), vueloT.getDuracion(), vueloT.isRealizado(), costoEjec, costoEcon);			
//			vuelos.add(vuelo);
//		}
//		return vuelos;
//	}



//	public ArrayList<ViajeCarga> getVuelosCargaRealizadosAvion(String numSerieAvion) throws SQLException, ParseException
//	{
//		ArrayList<ViajeCarga> vuelos = new ArrayList<ViajeCarga>();
//		String sql="SELECT * FROM ISIS2304A131620.VUELO_CARGA WHERE AVION = '"+numSerieAvion+"' AND REALIZADO = 'Y'";
//		PreparedStatement prepStmt= conn.prepareStatement(sql);
//		ResultSet rs=prepStmt.executeQuery();
//		while(rs.next()){
//
//			int idVuelo = rs.getInt("ID");
//			Vuelo vueloT = getVuelo(idVuelo);
//			double costo = rs.getDouble("COSTO_DENSIDAD");
//			ViajeCarga vuelo = new ViajeCarga(idVuelo, vueloT.getHoraSalida(), vueloT.getHoraLlegada(), vueloT.getFrecuencia(), vueloT.getTipoViaje(), vueloT.getAerolinea(), vueloT.getAvion(), vueloT.getAeropuertoSA(), vueloT.getAeropuertoLL(), vueloT.getDistancia(), vueloT.getDuracion(), vueloT.isRealizado(), costo);			
//			vuelos.add(vuelo);
//		}
//		return vuelos;
//	}

//	public ArrayList<Vuelo> getVuelosAeropuerto(String aeropuerto) throws SQLException {
//		Vuelo vuelo=null;
//		ArrayList<Vuelo> vuelos = new ArrayList<Vuelo>();
//		String sql="SELECT * FROM ISIS2304A131620.VUELO WHERE (ISIS2304A131620.VUELO.AEROPUERTO_SALIDA ='" + aeropuerto+ "' OR ISIS2304A131620.VUELO.AEROPUERTO_LLEGADA = '" + aeropuerto+ "')";
//		PreparedStatement prepStmt= conn.prepareStatement(sql);
//		ResultSet rs=prepStmt.executeQuery();
//		while(rs.next()){
//			int id= rs.getInt("ID");
//			Date horaSalida= rs.getDate("HORA_SALIDA");
//			Date horaLlegada=rs.getDate("HORA_LLEGADA");
//			int duracion =rs.getInt("DURACION");
//			double distancia= rs.getDouble("DISTANCIA");
//			int frecuencia =rs.getInt("FRECUENCIA");
//			String tipoViaje= rs.getString("TIPO_VIAJE");
//			String aerolinea = rs.getString("AEROLINEA");
//			String avion = rs.getString("AVION");
//			String aeropuertoLL= rs.getString("AEROPUERTO_SALIDA");
//			String aeropuertoSA= rs.getString("AEROPUERTO_LLEGADA");
//			Aeropuerto aeropuertoLLegada = getAeropuerto(aeropuertoLL);
//			Aeropuerto aeropuertoSalida= getAeropuerto(aeropuertoSA);
//			String realizado = rs.getString("REALIZADO");
//			boolean realizadoo = false;
//			if (realizado.equals("N"))
//			{}
//			else if(realizado.equals("S"))
//			{
//				realizadoo = true;
//			}
//			String tipoVuelo = rs.getString("TIPO_VUELO");
//
//			vuelo = new Vuelo(id, frecuencia, aerolinea, aeropuertoSalida, aeropuertoLLegada, distancia, duracion, tipoVuelo);					
//			vuelos.add(vuelo);
//		}
//		return vuelos;
//	}
	public ArrayList<Viaje> getViajesAeropuertoRangoFechas(String aeropuerto, Date fechaMin, Date fechaMax) throws SQLException {
		Viaje viaje=null;
		ArrayList<Viaje> viajes = new ArrayList<Viaje>();
		String sql="SELECT * FROM ISIS2304A131620.VUELO WHERE ((ISIS2304A131620.VUELO.AEROPUERTO_SALIDA =" + aeropuerto+ "OR ISIS2304A131620.VUELO.AEROPUERTO_LLEGADA = "+aeropuerto+")AND "+fechaMin+" <= ISIS2304A131620.VUELO.HORA_SALIDA AND "+fechaMax+" > ISIS2304A131620.VUELO.HORA_SALIDA);";
		PreparedStatement prepStmt= conn.prepareStatement(sql);
		ResultSet rs=prepStmt.executeQuery();
		while(rs.next()){
			String id= rs.getString("ID");
			Date horaSalida= rs.getDate("HORA_SALIDA");
			Date horaLlegada=rs.getDate("HORA_LLEGADA");
			int duracion =rs.getInt("DURACION");
			double distancia= rs.getDouble("DISTANCIA");
			int frecuencia =rs.getInt("FRECUENCIA");
			String tipoViaje= rs.getString("TIPO_VIAJE");
			String aerolinea = rs.getString("AEROLINEA");
			String avion = rs.getString("AVION");
			String aeropuertoLL= rs.getString("AEROPUERTO_SALIDA");
			String aeropuertoSA= rs.getString("AEROPUERTO_LLEGADA");
			Aeropuerto aeropuertoLLegada = getAeropuerto(aeropuertoLL);
			Aeropuerto aeropuertoSalida= getAeropuerto(aeropuertoSA);
			String realizado = rs.getString("REALIZADO");
			boolean realizadoo = false;
			if (realizado.equals("N"))
			{}
			else if(realizado.equals("S"))
			{
				realizadoo = true;
			}
			String tipoVuelo = rs.getString("TIPO_VUELO");

			viaje = new Viaje(id, horaSalida, horaLlegada, frecuencia, tipoViaje, aerolinea, avion, aeropuertoSalida, aeropuertoLLegada, distancia, duracion, realizadoo, tipoVuelo)	;	
			viajes.add(viaje);
		}
		return viajes;
	}

//	public ArrayList<Vuelo> getVuelosAeropuertoAerolinea(String aeropuerto, String aerolinea) throws SQLException {
//		Vuelo vuelo=null;
//		ArrayList<Vuelo> vuelos = new ArrayList<Vuelo>();
//		String sql="SELECT * FROM ISIS2304A131620.VUELO WHERE ((ISIS2304A131620.VUELO.AEROPUERTO_SALIDA =" + aeropuerto+ "OR ISIS2304A131620.VUELO.AEROPUERTO_LLEGADA = "+aeropuerto+")AND ISIS2304A131620.VUELO.AEROLINEA = "+aerolinea+");";
//		PreparedStatement prepStmt= conn.prepareStatement(sql);
//		ResultSet rs=prepStmt.executeQuery();
//		while(rs.next()){
//			int id= rs.getInt("ID");
//			Date horaSalida= rs.getDate("HORA_SALIDA");
//			Date horaLlegada=rs.getDate("HORA_LLEGADA");
//			int duracion =rs.getInt("DURACION");
//			double distancia= rs.getDouble("DISTANCIA");
//			int frecuencia =rs.getInt("FRECUENCIA");
//			String tipoViaje= rs.getString("TIPO_VIAJE");
//			aerolinea = rs.getString("AEROLINEA");
//			String avion = rs.getString("AVION");
//			String aeropuertoLL= rs.getString("AEROPUERTO_SALIDA");
//			String aeropuertoSA= rs.getString("AEROPUERTO_LLEGADA");
//			Aeropuerto aeropuertoLLegada = getAeropuerto(aeropuertoLL);
//			Aeropuerto aeropuertoSalida= getAeropuerto(aeropuertoSA);
//			String realizado = rs.getString("REALIZADO");
//			boolean realizadoo = false;
//			if (realizado.equals("N"))
//			{}
//			else if(realizado.equals("S"))
//			{
//				realizadoo = true;
//			}
//			String tipoVuelo = rs.getString("TIPO_VUELO");
//
//			vuelo = new Vuelo(id, frecuencia, aerolinea, aeropuertoSalida, aeropuertoLLegada, distancia, duracion, tipoVuelo);		
//			vuelos.add(vuelo);
//		}
//		return vuelos;
//	}
	public ArrayList<Viaje> getViajesAeropuertoHoraSalida(String aeropuerto, Date horaSalida) throws SQLException, ParseException {
		Viaje viaje=null;
		ArrayList<Viaje> viajes = new ArrayList<Viaje>();
		String sql="SELECT * FROM ISIS2304A131620.VUELO WHERE ((ISIS2304A131620.VUELO.AEROPUERTO_SALIDA =" + aeropuerto+ "OR ISIS2304A131620.VUELO.AEROPUERTO_LLEGADA = "+aeropuerto+")AND "+horaSalida+" = ISIS2304A131620.VUELO.HORA_SALIDA);";
		PreparedStatement prepStmt= conn.prepareStatement(sql);
		ResultSet rs=prepStmt.executeQuery();
		while(rs.next()){
			String id= rs.getString("ID");
			horaSalida= rs.getDate("HORA_SALIDA");
			Date horaLlegada=rs.getDate("HORA_LLEGADA");
			String tipoViaje= rs.getString("TIPO_VIAJE");
			String avion = rs.getString("AVION");
			String aeropuertoLL= rs.getString("AEROPUERTO_SALIDA");
			String aeropuertoSA= rs.getString("AEROPUERTO_LLEGADA");
			String realizado = rs.getString("REALIZADO");
			boolean realizadoo = false;
			if (realizado.equals("N"))
			{}
			else if(realizado.equals("S"))
			{
				realizadoo = true;
			}
			Vuelo vuelo = getVuelo(id);
			viaje = new Viaje(id, horaSalida, horaLlegada, vuelo.getFrecuencia(), tipoViaje, vuelo.getAerolinea(), avion, vuelo.getAeropuertoSA(), vuelo.getAeropuertoLL(), vuelo.getDistancia(), vuelo.getDuracion(), realizadoo, vuelo.getTipoVuelo());	
			viajes.add(viaje);
		}
		return viajes;
	}

	public ArrayList<Viaje> getVuelosAeropuertoHoraLlegada(String aeropuerto, Date horaLlegada) throws SQLException {
		Viaje viaje=null;
		ArrayList<Viaje> viajes = new ArrayList<Viaje>();
		String sql="SELECT * FROM ISIS2304A131620.VUELO WHERE ((ISIS2304A131620.VUELO.AEROPUERTO_SALIDA =" + aeropuerto+ "OR ISIS2304A131620.VUELO.AEROPUERTO_LLEGADA = "+aeropuerto+")AND "+horaLlegada+" = ISIS2304A131620.VUELO.HORA_LLEGADA);";
		PreparedStatement prepStmt= conn.prepareStatement(sql);
		ResultSet rs=prepStmt.executeQuery();
		while(rs.next()){
			String id= rs.getString("ID");
			Date horaSalida= rs.getDate("HORA_SALIDA");
			horaLlegada=rs.getDate("HORA_LLEGADA");
			int duracion =rs.getInt("DURACION");
			double distancia= rs.getDouble("DISTANCIA");
			int frecuencia =rs.getInt("FRECUENCIA");
			String tipoViaje= rs.getString("TIPO_VIAJE");
			String aerolinea = rs.getString("AEROLINEA");
			String avion = rs.getString("AVION");
			String aeropuertoLL= rs.getString("AEROPUERTO_SALIDA");
			String aeropuertoSA= rs.getString("AEROPUERTO_LLEGADA");
			Aeropuerto aeropuertoLLegada = getAeropuerto(aeropuertoLL);
			Aeropuerto aeropuertoSalida= getAeropuerto(aeropuertoSA);
			String realizado = rs.getString("REALIZADO");
			boolean realizadoo = false;
			if (realizado.equals("N"))
			{}
			else if(realizado.equals("S"))
			{
				realizadoo = true;
			}
			String tipoVuelo = rs.getString("TIPO_VUELO");

			Vuelo vuelo = getVuelo(id);
			viaje = new Viaje(id, horaSalida, horaLlegada, vuelo.getFrecuencia(), tipoViaje, vuelo.getAerolinea(), avion, vuelo.getAeropuertoSA(), vuelo.getAeropuertoLL(), vuelo.getDistancia(), vuelo.getDuracion(), realizadoo, vuelo.getTipoVuelo());	
			viajes.add(viaje);
		}
		return viajes;
	}



	public ArrayList<Viaje> getViajesAeropuertoViajeros(String aeropuerto) throws SQLException, ParseException {
		Viaje viaje=null;
		ArrayList<Viaje> viajes = new ArrayList<Viaje>();
		String sql="SELECT * FROM(SELECT * FROM ISIS2304A131620.VUELO WHERE(ISIS2304A131620.VUELO.AEROPUERTO_SALIDA = "+aeropuerto+" OR ISIS2304A131620.VUELO.AEROPUERTO_LLEGADA = "+aeropuerto+"))c1 INNER JOIN ISIS2304A131620.AVION_VIAJEROS ON (c1.AVION = ISIS2304A131620.AVION_VIAJEROS.NUM_SERIE);";
		PreparedStatement prepStmt= conn.prepareStatement(sql);
		ResultSet rs=prepStmt.executeQuery();
		while(rs.next()){
			String id= rs.getString("ID");
			Date horaSalida= rs.getDate("HORA_SALIDA");
			Date horaLlegada=rs.getDate("HORA_LLEGADA");
			int duracion =rs.getInt("DURACION");
			double distancia= rs.getDouble("DISTANCIA");
			int frecuencia =rs.getInt("FRECUENCIA");
			String tipoViaje= rs.getString("TIPO_VIAJE");
			String aerolinea = rs.getString("AEROLINEA");
			String avion = rs.getString("AVION");
			String aeropuertoLL= rs.getString("AEROPUERTO_SALIDA");
			String aeropuertoSA= rs.getString("AEROPUERTO_LLEGADA");
			Aeropuerto aeropuertoLLegada = getAeropuerto(aeropuertoLL);
			Aeropuerto aeropuertoSalida= getAeropuerto(aeropuertoSA);
			String realizado = rs.getString("REALIZADO");
			boolean realizadoo = false;
			if (realizado.equals("N"))
			{}
			else if(realizado.equals("S"))
			{
				realizadoo = true;
			}
			String tipoVuelo = rs.getString("TIPO_VUELO");

			Vuelo vuelo = getVuelo(id);
			viaje = new Viaje(id, horaSalida, horaLlegada, vuelo.getFrecuencia(), tipoViaje, vuelo.getAerolinea(), avion, vuelo.getAeropuertoSA(), vuelo.getAeropuertoLL(), vuelo.getDistancia(), vuelo.getDuracion(), realizadoo, vuelo.getTipoVuelo());
			viajes.add(viaje);
		}
		return viajes;
	}

//	public ArrayList<ViajeCarga> getVuelosAeropuertoCarga(String aeropuerto) throws SQLException {
//		ViajeCarga vuelo=null;
//		ArrayList<ViajeCarga> vuelos = new ArrayList<ViajeCarga>();
//		String sql="SELECT * FROM(SELECT * FROM ISIS2304A131620.VUELO_CARGA WHERE(ISIS2304A131620.VUELO.AEROPUERTO_SALIDA = "+aeropuerto+" OR ISIS2304A131620.VUELO.AEROPUERTO_LLEGADA = "+aeropuerto+"))c1 INNER JOIN ISIS2304A131620.AVION_VIAJEROS ON (c1.AVION = ISIS2304A131620.AVION_CARGA.NUM_SERIE);";
//		PreparedStatement prepStmt= conn.prepareStatement(sql);
//		ResultSet rs=prepStmt.executeQuery();
//		while(rs.next()){
//			int id= rs.getInt("ID");
//			Date horaSalida= rs.getDate("HORA_SALIDA");
//			Date horaLlegada=rs.getDate("HORA_LLEGADA");
//			String duracion =rs.getString("DURACION");
//			double distancia= rs.getDouble("DISTANCIA");
//			int frecuencia =rs.getInt("FRECUENCIA");
//			String tipoViaje= rs.getString("TIPO_VIAJE");
//			String aerolinea = rs.getString("AEROLINEA");
//			String avion = rs.getString("AVION");
//			String aeropuertoLL= rs.getString("AEROPUERTO_SALIDA");
//			String aeropuertoSA= rs.getString("AEROPUERTO_LLEGADA");
//			Aeropuerto aeropuertoLLegada = getAeropuerto(aeropuertoLL);
//			Aeropuerto aeropuertoSalida= getAeropuerto(aeropuertoSA);
//			String realizado = rs.getString("REALIZADO");
//			boolean realizadoo = false;
//			if (realizado.equals("N"))
//			{}
//			else if(realizado.equals("S"))
//			{
//				realizadoo = true;
//			}
//			String tipoVuelo = rs.getString("TIPO_VUELO");
//
//			Vuelo vuelo = darVueloDeViaje(id);
//			viaje = new Viaje(id, horaSalida, horaLlegada, vuelo.getFrecuencia(), tipoViaje, vuelo.getAerolinea(), avion, vuelo.getAeropuertoSA(), vuelo.getAeropuertoLL(), vuelo.getDistancia(), vuelo.getDuracion(), realizadoo, vuelo.getTipoVuelo());	
//			vuelos.add(vuelo);
//		}
//		return vuelos;
//	}

//
//	public void asociarVuelo(int idVuelo, String idAvion) throws SQLException, Exception
//	{
//		DAOTablaReservas daoReservas = new DAOTablaReservas();
//		DAOTablaAviones daoAviones = new DAOTablaAviones();
//		DAOTablaCargas daoCargas = new DAOTablaCargas();
//		Vuelo vuelo = vuelos.get(idVuelo);
//		Avion avion = daoAviones.darAvion(idAvion);
//
//		if(avion.getTipo().equals(vuelo.getTipoViaje()))
//		{
//			if(avion.getTipo() == Avion.CARGA)
//			{
//				ArrayList<ReservaCarga> reservas = daoReservas.getReservasCargaPorVuelo(vuelo.getId());
//				AvionCarga avionCarga = daoAviones.darAvionCarga(idAvion);
//				int cargaTotal = 0;
//				for(int i = 0; i<reservas.size();i++)
//				{
//					ReservaCarga reserva = reservas.get(i);
//					Carga carga = daoCargas.getCarga(reserva.getIdCarga());
//					cargaTotal += carga.getPeso();
//				}
//				if(avionCarga.getCargaMax() >= cargaTotal )
//				{
//					vuelo.setAvion(avion.getNumSerie());
//					updateVuelo(vuelo);
//				}
//			}
//			else if (avion.getTipo() == Avion.VIAJEROS)
//			{
//				ArrayList<ReservaViajeros> reservas = daoReservas.getReservasViajerosPorVuelo(idVuelo);
//				AvionViajeros avionViaje = daoAviones.darAvionViajeros(idAvion);
//				int puestosEconomica = 0;
//				int puestosEjecutiva = 0;
//				for(int i = 0; i<reservas.size();i++)
//				{
//					ReservaViajeros reserva = reservas.get(i);
//					if(reserva.getClase().equals(ReservaViajeros.ECONOMICA))
//					{
//						puestosEconomica++;
//					}
//					else if(reserva.getClase().equals(ReservaViajeros.EJECUTIVA))
//					{
//						puestosEjecutiva++;
//					}
//				}
//				if(puestosEconomica <= avionViaje.getSillasEconomicas() && puestosEjecutiva <= avionViaje.getSillasEjecutivas())
//				{
//					vuelo.setAvion(avion.getNumSerie());
//					updateVuelo(vuelo);
//				}
//			}
//		}
//		else
//		{
//			throw new Exception("El avi�n con el que se quiere vincular la reserva no es del tipo "+ vuelo.getTipoViaje());
//		}

//
//
//
//
//
//	}

	public double generarReporte(String idViaje, Date horaSalida, DAOTablaReservas daoReservas) throws SQLException, ParseException
	{
		DAOTablaCargas daoCargas = new DAOTablaCargas();
		daoCargas.setConn(conn);
		Viaje viaje = getViaje(idViaje, horaSalida);
		double costo = 0;
		if(viaje.getTipoViaje().equals(Vuelo.CARGA))
		{
			ViajeCarga vueloC = getViajeCarga(idViaje, horaSalida);
			double densidadTotal = 0;
			ArrayList<ReservaCarga> reservasC = daoReservas.getReservasCargaPorVuelo(idViaje);
			for(int i = 0; i < reservasC.size();i++)
			{
				ReservaCarga reservaActual = reservasC.get(i);
				Carga carga = daoCargas.getCarga(reservaActual.getIdCarga());
				double densidad = (double)(carga.getPeso()/carga.getVolumen());

				densidadTotal+= densidad;
			}
			costo = vueloC.getCostoDensidad()*densidadTotal;
		}
		else if(viaje.getTipoVuelo().equals(Vuelo.VIAJEROS))
		{
			ViajeViajeros vueloV = getViajeViajeros(idViaje,horaSalida);
			int pasajerosEconomicos = 0;
			int pasajerosEjecutivos = 0;
			ArrayList<ReservaViajeros> reservasV = daoReservas.getReservasViajerosPorVuelo(idViaje);
			for(int i = 0; i < reservasV.size();i++)
			{
				ReservaViajeros reservaActual = reservasV.get(i);
				if(reservaActual.getClase().equals(ReservaViajeros.ECONOMICA))
				{
					pasajerosEconomicos++;
				}
				else if(reservaActual.getClase().equals(ReservaViajeros.EJECUTIVA))
				{
					pasajerosEjecutivos++;
				}
			}
			costo= (pasajerosEjecutivos*vueloV.getCostoEjec()) + (pasajerosEconomicos*vueloV.getCostoEcon());
		}
		return costo;
	}
	public void registrarVuelo(String idVuelo) throws SQLException{
		String sql ="UPDATE ISIS2304A131620.VUELO SET REALIZADO= Y WHERE ID="+idVuelo;

		PreparedStatement prepStmt=conn.prepareStatement(sql);
		prepStmt.executeQuery();
	}

	public ArrayList<Vuelo> darVuelosEnFechas(Date d1, Date d2){
		return null;
	}


	public ArrayList<Vuelo> getVuelosMasPopulares() {
		return null;

	}
	
	
	public ArrayList<Viaje> getViajesAeropuertoAerolineaOrganizado(String aeropuerto, String aerolinea, String organizacion) throws SQLException {
		Viaje viaje=null;
		ArrayList<Viaje> viajes = new ArrayList<Viaje>();
		String sql="SELECT * FROM VIAJE JOIN (SELECT * FROM VUELO WHERE ((VUELO.AEROPUERTO_SALIDA ='" + aeropuerto+ "' OR ISIS2304A131620.VUELO.AEROPUERTO_LLEGADA = '"+aeropuerto+"')AND VUELO.AEROLINEA = '"+aerolinea+"') ORDER BY "+organizacion+")t1 ON VIAJE.ID_VUELO = t1.ID";
		PreparedStatement prepStmt= conn.prepareStatement(sql);
		ResultSet rs=prepStmt.executeQuery();
		while(rs.next()){
			String id= rs.getString("ID");
			Date horaSalida= rs.getDate("HORA_SALIDA");
			Date horaLlegada=rs.getDate("HORA_LLEGADA");
			int distancia = rs.getInt("DISTANCIA");
			int duracion = rs.getInt("DURACION");
			int frecuencia =rs.getInt("FRECUENCIA");
			String tipoViaje= rs.getString("TIPO_VIAJE");
			aerolinea = rs.getString("AEROLINEA");
			String avion = rs.getString("AVION");
			String aeropuertoLL= rs.getString("AEROPUERTO_SALIDA");
			String aeropuertoSA= rs.getString("AEROPUERTO_LLEGADA");
			Aeropuerto aeropuertoLLegada = getAeropuerto(aeropuertoLL);
			Aeropuerto aeropuertoSalida= getAeropuerto(aeropuertoSA);
			String realizado = rs.getString("REALIZADO");
			boolean realizadoo = false;
			if (realizado.equals("N"))
			{}
			else if(realizado.equals("S"))
			{
				realizadoo = true;
			}
			String tipoVuelo = rs.getString("TIPO_VUELO");

			viaje = new Viaje(id, horaSalida, horaLlegada, frecuencia, tipoViaje, aerolinea, avion, aeropuertoSalida, aeropuertoLLegada, distancia, duracion, realizadoo,tipoVuelo);		
			viajes.add(viaje);
		}
		return viajes;
	}
	
	
	public ArrayList<Viaje> getViajesAeropuertoAeronaveOrganizado(String aeropuerto, String aeronave, String organizacion) throws SQLException {
		Viaje viaje=null;
		ArrayList<Viaje> viajes = new ArrayList<Viaje>();
		String sql="SELECT * FROM ISIS2304A131620.VUELO JOIN ISIS2304A131620.VIAJE ON ISIS2304A131620.VUELO.ID= ISIS2304A131620.VIAJE.ID_VUELO WHERE ((ISIS2304A131620.VUELO.AEROPUERTO_SALIDA ='" + aeropuerto+ "' OR ISIS2304A131620.VUELO.AEROPUERTO_LLEGADA = '"+aeropuerto+"')AND ISIS2304A131620.VIAJE.AVION = '"+aeronave+"') ORDER BY "+organizacion;
		PreparedStatement prepStmt= conn.prepareStatement(sql);
		ResultSet rs=prepStmt.executeQuery();
		while(rs.next()){
			String id= rs.getString("ID");
			Vuelo vuelo = getVuelo(id);
			Date horaSalida= rs.getDate("HORA_SALIDA");
			Date horaLlegada=rs.getDate("HORA_LLEGADA");
			int duracion =vuelo.getDuracion();
			double distancia= vuelo.getDistancia();
			int frecuencia =vuelo.getFrecuencia();
			String tipoViaje= rs.getString("TIPO_VIAJE");
			String aerolinea = vuelo.getAerolinea();
			String avion = rs.getString("AVION");
			Aeropuerto aeropuertoLL= vuelo.getAeropuertoLL();
			Aeropuerto aeropuertoSA= vuelo.getAeropuertoSA();
			String realizado = rs.getString("REALIZADO");
			boolean realizadoo = false;
			if (realizado.equals("N"))
			{}
			else if(realizado.equals("S"))
			{
				realizadoo = true;
			}
			String tipoVuelo = vuelo.getTipoVuelo();

			viaje = new Viaje(id,horaSalida,horaLlegada,frecuencia,tipoViaje,aerolinea,avion,aeropuertoSA,aeropuertoLL,distancia,duracion,realizadoo,tipoVuelo);
			viajes.add(viaje);
		}
		return viajes;
	}
	public ArrayList<Viaje> getViajesAeropuertoRangoFechasOrganizado(String aeropuerto, Date fechaMin, Date fechaMax, String organizacion) throws SQLException {
		Viaje viaje=null;
		ArrayList<Viaje> viajes = new ArrayList<Viaje>();
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(fechaMin);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(fechaMax);
		int dia1 = cal1.get(Calendar.DAY_OF_MONTH);
		int mes1 = cal1.get(Calendar.MONTH);
		int anio1 = cal1.get(Calendar.YEAR);
		int dia2 = cal2.get(Calendar.DAY_OF_MONTH);
		int mes2 = cal2.get(Calendar.MONTH);
		int anio2 = cal2.get(Calendar.YEAR);
		String sql="SELECT * FROM ISIS2304A131620.VUELO JOIN ISIS2304A131620.VIAJE ON ISIS2304A131620.VUELO.ID= ISIS2304A131620.VIAJE.ID_VUELO WHERE ((ISIS2304A131620.VUELO.AEROPUERTO_SALIDA = '" + aeropuerto+ "' OR ISIS2304A131620.VUELO.AEROPUERTO_LLEGADA = '"+aeropuerto+"') AND TO_DATE('"+dia1+"-"+mes1+"-"+anio1+"','DD-MM-YYYY') <= ISIS2304A131620.VUELO.HORA_SALIDA AND TO_DATE('"+dia2+"-"+mes2+"-"+anio2+"','DD-MM-YYYY') > ISIS2304A131620.VUELO.HORA_SALIDA) ORDER BY "+organizacion+";";
		PreparedStatement prepStmt= conn.prepareStatement(sql);
		ResultSet rs=prepStmt.executeQuery();
		while(rs.next()){
			String id= rs.getString("ID");
			Date horaSalida= rs.getDate("HORA_SALIDA");
			Date horaLlegada=rs.getDate("HORA_LLEGADA");
			int duracion =rs.getInt("DURACION");
			double distancia= rs.getDouble("DISTANCIA");
			int frecuencia =rs.getInt("FRECUENCIA");
			String tipoViaje= rs.getString("TIPO_VIAJE");
			String aerolinea = rs.getString("AEROLINEA");
			String avion = rs.getString("AVION");
			String aeropuertoLL= rs.getString("AEROPUERTO_SALIDA");
			String aeropuertoSA= rs.getString("AEROPUERTO_LLEGADA");
			Aeropuerto aeropuertoLLegada = getAeropuerto(aeropuertoLL);
			Aeropuerto aeropuertoSalida= getAeropuerto(aeropuertoSA);
			String realizado = rs.getString("REALIZADO");
			boolean realizadoo = false;
			if (realizado.equals("N"))
			{}
			else if(realizado.equals("S"))
			{
				realizadoo = true;
			}
			String tipoVuelo = rs.getString("TIPO_VUELO");

			viaje = new Viaje(id, horaSalida, horaLlegada, frecuencia, tipoViaje, aerolinea, avion, aeropuertoSalida, aeropuertoLLegada, distancia, duracion, realizadoo,tipoVuelo);		
			viajes.add(viaje);
		}
		return viajes;
	}
	public ArrayList<Viaje> getViajesAeropuertoHoraSalidaLlegadaOrganizado(String aeropuerto, Date fechaLlgada, Date fechaSalida, String organizacion) throws SQLException {
		Viaje viaje=null;
		ArrayList<Viaje> viajes = new ArrayList<Viaje>();
		String sql="SELECT * FROM ISIS2304A131620.VUELO JOIN ISIS2304A131620.VIAJE ON ISIS2304A131620.VUELO.ID= ISIS2304A131620.VIAJE.ID_VUELO WHERE ((ISIS2304A131620.VUELO.AEROPUERTO_SALIDA =" + aeropuerto+ "OR ISIS2304A131620.VUELO.AEROPUERTO_LLEGADA = "+aeropuerto+")AND "+fechaLlgada+"= ISIS2304A131620.VUELO.HORA_LLEGADA OR "+fechaSalida+"= ISIS2304A131620.VUELO.HORA_SALIDA) ORDER BY "+organizacion+";";
		PreparedStatement prepStmt= conn.prepareStatement(sql);
		ResultSet rs=prepStmt.executeQuery();
		while(rs.next()){
			String id= rs.getString("ID");
			Date horaSalida= rs.getDate("HORA_SALIDA");
			Date horaLlegada=rs.getDate("HORA_LLEGADA");
			int duracion =rs.getInt("DURACION");
			double distancia= rs.getDouble("DISTANCIA");
			int frecuencia =rs.getInt("FRECUENCIA");
			String tipoViaje= rs.getString("TIPO_VIAJE");
			String aerolinea = rs.getString("AEROLINEA");
			String avion = rs.getString("AVION");
			String aeropuertoLL= rs.getString("AEROPUERTO_SALIDA");
			String aeropuertoSA= rs.getString("AEROPUERTO_LLEGADA");
			Aeropuerto aeropuertoLLegada = getAeropuerto(aeropuertoLL);
			Aeropuerto aeropuertoSalida= getAeropuerto(aeropuertoSA);
			String realizado = rs.getString("REALIZADO");
			boolean realizadoo = false;
			if (realizado.equals("N"))
			{}
			else if(realizado.equals("S"))
			{
				realizadoo = true;
			}
			String tipoVuelo = rs.getString("TIPO_VUELO");

			viaje = new Viaje(id, horaSalida, horaLlegada, frecuencia, tipoViaje, aerolinea, avion, aeropuertoSalida, aeropuertoLLegada, distancia, duracion, realizadoo,tipoVuelo);		
			viajes.add(viaje);
		}
		return viajes;
	}

	public int cantidadViajerosViaje(String idViaje, Date horaSalida) throws SQLException
	{
		int i = 0;
		Calendar cal = Calendar.getInstance();
		cal.setTime(horaSalida);
		int dia1 = cal.get(Calendar.DAY_OF_MONTH);
		int mes1 = cal.get(Calendar.MONTH)+1;
		int anio1 = cal.get(Calendar.YEAR);
		String sql = "SELECT * FROM M_VIAJES_M_VIAJEROS WHERE ID_VUELO = '"+idViaje+"' AND TO_DATE(HORA_SALIDA_VIAJE) = TO_DATE('"+dia1+"-"+mes1+"-"+anio1+"','DD-MM-YYYY')";
		PreparedStatement prepStmt= conn.prepareStatement(sql);
		ResultSet rs=prepStmt.executeQuery();
		while(rs.next()){
			i ++;
		}
		return i;
	}
	public double cantidadCargaViaje(String idViaje, Date horaSalida) throws SQLException
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(horaSalida);
		int dia1 = cal.get(Calendar.DAY_OF_MONTH);
		int mes1 = cal.get(Calendar.MONTH)+1;
		int anio1 = cal.get(Calendar.YEAR);
		DAOTablaCargas daoCargas = new DAOTablaCargas();
		daoCargas.setConn(conn);
		String sql = "SELECT * FROM M_VIAJES_M_CARGAS WHERE ID_VUELO = '"+idViaje+"'AND TO_DATE(HORA_SALIDA_VIAJE) = TO_DATE('"+dia1+"-"+mes1+"-"+anio1+"','DD-MM-YYYY')";
		PreparedStatement prepStmt= conn.prepareStatement(sql);
		ResultSet rs=prepStmt.executeQuery();
		double peso = 0;
		while(rs.next()){
			
			int idCarga = rs.getInt("ID_CARGA");
			Carga carga = daoCargas.getCarga(idCarga);
			peso += carga.getPeso();
		}
		return peso;
	}

	public ArrayList<Viaje> getViajesAeropuertoNoParametro(String idAeropuerto, int dia1, int mes1, int anio1, 
			int dia2,int mes2,int anio2,
			String aerolinea, String aeronave, String horaSal, String horaLleg) throws Exception{
		Viaje viaje=null;
		ArrayList<Viaje> viajes = new ArrayList<Viaje>();
		String sql="SELECT * FROM ISIS2304A131620.VUELO JOIN ISIS2304A131620.VIAJE ON ISIS2304A131620.VUELO.ID= ISIS2304A131620.VIAJE.ID_VUELO WHERE (((ISIS2304A131620.VUELO.AEROPUERTO_SALIDA ='" + idAeropuerto+ "' OR ISIS2304A131620.VUELO.AEROPUERTO_LLEGADA = '"+idAeropuerto+"') AND TO_DATE(ISIS2304A131620.VIAJE.HORA_SALIDA) >= TO_DATE('"+dia1+"-"+mes1+"-"+anio1+"','DD-MM-YYYY') AND TO_DATE(ISIS2304A131620.VIAJE.HORA_SALIDA) <= TO_DATE('"+dia2+"-"+mes2+"-"+anio2+"','DD-MM-YYYY')) AND ISIS2304A131620.VUELO.AEROLINEA!='"+aerolinea+"' AND ISIS2304A131620.VIAJE.AVION!='"+aeronave+"' AND "
				+ "TO_DATE(HORA_SALIDA)!= TO_DATE('"+horaSal+"','DD-MM-YYYY HH24:MI:SS') AND TO_DATE(HORA_LLEGADA)!= TO_DATE('"+horaLleg+"','DD-MM-YYYY HH24:MI:SS'))";
		PreparedStatement prepStmt= conn.prepareStatement(sql);
		ResultSet rs=prepStmt.executeQuery();
		while(rs.next()){
			String id= rs.getString("ID");
			Date horaSalida= rs.getDate("HORA_SALIDA");
			Date horaLlegada=rs.getDate("HORA_LLEGADA");
			int duracion =rs.getInt("DURACION");
			double distancia= rs.getDouble("DISTANCIA");
			int frecuencia =rs.getInt("FRECUENCIA");
			String tipoViaje= rs.getString("TIPO_VIAJE");
		    aerolinea = rs.getString("AEROLINEA");
			String avion = rs.getString("AVION");
			String aeropuertoLL= rs.getString("AEROPUERTO_SALIDA");
			String aeropuertoSA= rs.getString("AEROPUERTO_LLEGADA");
			Aeropuerto aeropuertoLLegada = getAeropuerto(aeropuertoLL);
			Aeropuerto aeropuertoSalida= getAeropuerto(aeropuertoSA);
			String realizado = rs.getString("REALIZADO");
			boolean realizadoo = false;
			if (realizado.equals("N"))
			{}
			else if(realizado.equals("S"))
			{
				realizadoo = true;
			}
			String tipoVuelo = rs.getString("TIPO_VUELO");

			viaje = new Viaje(id, horaSalida, horaLlegada, frecuencia, tipoViaje, aerolinea, avion, aeropuertoSalida, aeropuertoLLegada, distancia, duracion, realizadoo,tipoVuelo);		
			viajes.add(viaje);
		}
		return viajes;
	}

	public ArrayList<ConsultaViajes> consultarViajesViajeroParametros(String clase, double millas) throws Exception
	{
		DAOTablaViajeros daoViajeros = new DAOTablaViajeros();
		daoViajeros.setConn(conn);
		DAOTablaAerolina daoAerolineas = new DAOTablaAerolina();
		daoAerolineas.setConn(conn);
		DAOTablaReservas daoReservas = new DAOTablaReservas();
		daoReservas.setConn(conn);
		ArrayList<ConsultaViajes> consultas = new ArrayList<>();

		String sql="SELECT * FROM(SELECT * FROM((SELECT t1.ID, t1.TIPO_ID, SUM(t2.DURACION)as millas FROM "
				+ "((SELECT * FROM VIAJEROS JOIN (SELECT * FROM M_VIAJES_M_VIAJEROS) ON VIAJEROS.ID = ID_VIAJEROS AND VIAJEROS.TIPO_ID = TIPO_ID_VIAJEROS)t1 "
				+ "JOIN (SELECT * FROM VUELO)t2 ON t1.ID_VUELO = t2.ID) GROUP BY t1.ID, T1.TIPO_ID)t3  LEFT OUTER JOIN (SELECT * FROM M_VIAJES_M_VIAJEROS)t4  "
				+ "ON t3.ID = t4.ID_VIAJEROS AND t3.TIPO_ID = t4.TIPO_ID_VIAJEROS) WHERE MILLAS > "+millas+")t4 JOIN (SELECT * FROM VIAJE_VIAJEROS)t5 ON  t4.ID_VUELO = "
				+ "t5.ID AND t4.HORA_SALIDA_VIAJE = t5.HORA_SALIDA";



		PreparedStatement prepStmt= conn.prepareStatement(sql);
		ResultSet rs=prepStmt.executeQuery();
		while(rs.next()){
			int idViajero = rs.getInt("ID");
			String tipoIdViajero = rs.getString("TIPO_ID");
			Date horaSalida = rs.getDate("HORA_SALIDA_VIAJE");
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			String fechaSalida = format.format(horaSalida);
			double millasTotales = rs.getDouble("MILLAS");
			String idVuelo = rs.getString("ID_VUELO");
			
			ViajeViajeros viaje = getViajeViajeros(idVuelo, horaSalida );
			String aerolineaa = viaje.getAerolinea();
			Aerolinea aerolinea = daoAerolineas.getAerolinea(aerolineaa);
			String aeroLleg = viaje.getAeropuertoLL().getCodigo();
			String aeroSal = viaje.getAeropuertoSA().getCodigo();

			double costo = generarReporte(viaje.getId(),viaje.getHoraSalida(), daoReservas);
			String fechaLlegada = format.format(viaje.getHoraLlegada());
			millas = viaje.getDistancia();

			consultas.add(new ConsultaViajes(idViajero,tipoIdViajero, idVuelo, aerolinea, fechaSalida, fechaLlegada, aeroSal, aeroLleg, millasTotales, costo));
		}
		rs.close();
		prepStmt.close();
		return consultas;
	}
		

		
	


	public ConsultaTraficoAereo getTraficoAereoCiudades(String ciudad1, String ciudad2, int dia1, String mes1, int anio1,
			int dia2, String mes2, int anio2) throws SQLException, ParseException {
		String sql = "SELECT * FROM (select * FROM VIAJE WHERE TO_DATE(HORA_SALIDA) >= TO_DATE('"+dia1+"-"+mes1+"-"+anio1+"','DD-MM-YYYY') AND TO_DATE(HORA_LLEGADA) <= TO_DATE('"+dia2+"-"+mes2+"-"+anio2+"','DD-MM-YYYY'))t6 "
				+ "JOIN (SELECT * FROM (SELECT * FROM VUELO JOIN (SELECT COD_IATA FROM AEROPUERTO WHERE NOMBRE_CIUDAD = '"+ciudad1+"')t2 "
				+ "ON VUELO.AEROPUERTO_SALIDA = t2.COD_IATA)t4 JOIN (SELECT COD_IATA FROM AEROPUERTO WHERE NOMBRE_CIUDAD = '"+ciudad2+"')t3 ON "
				+ "t3.COD_IATA = t4.AEROPUERTO_LLEGADA)t5 ON t5.ID = t6.ID_VUELO";
		PreparedStatement prp =conn.prepareStatement(sql);
		ResultSet rs = prp.executeQuery();
		ViajeViajeros viaje =null;
		ViajeCarga viajec = null;
		ArrayList<ViajeViajeros> viajesV = new ArrayList<>();
		ArrayList<ViajeCarga> viajesC = new ArrayList<>();
		while(rs.next()){
			String idViaje= rs.getString("ID");
			Date horaSalida = null;
			horaSalida = rs.getDate("HORA_SALIDA");
			SimpleDateFormat format = new SimpleDateFormat("dd MM yyyy HH:mm:ss");
			String lala = format.format(horaSalida);
			horaSalida = format.parse(format.format(horaSalida));			
			String tipoViaje= rs.getString("TIPO_VIAJE");
			

			if(tipoViaje.equals(Viaje.VIAJEROS))
			{
				viaje = getViajeViajeros(idViaje,horaSalida);
				viaje.setCantidadViajeros(cantidadViajerosViaje(idViaje,horaSalida));
				viajesV.add(viaje);

			}
			else
			{
				viajec = getViajeCarga(idViaje,horaSalida);
				viajec.setPesoTotal(cantidadCargaViaje(idViaje,horaSalida));
				viajesC.add(viajec);

			}
		}
		return new ConsultaTraficoAereo(viajesV, viajesC);
		
      
	}




}