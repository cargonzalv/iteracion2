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
			int id= rs.getInt("ID");
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
			vuelos.add(new Vuelo(id, frecuencia, aerolinea, aeropuertoSalida, aeropuertoLLegada, distancia, duracion, tipoVuelo));
		}
		return vuelos;
	}


	public ArrayList<ViajeViajeros> getVuelosViajeros() throws SQLException, ParseException {
		ArrayList<ViajeViajeros> vueloViajeros = new ArrayList<ViajeViajeros>();
		String sql="SELECT * FROM ISIS2304A131620.VUELO_VIAJEROS";
		PreparedStatement prepStmt= conn.prepareStatement(sql);
		ResultSet rs=prepStmt.executeQuery();
		while(rs.next()){
			int id = rs.getInt("ID");
			double costoEjec = rs.getDouble("COSTO_EJECUTIVO");
			double costoEcon = rs.getDouble("COSTO_ECONOMICO");
			Vuelo vueloT = getVuelo(id);

			vueloViajeros.add(new ViajeViajeros(vueloT.getId(), vueloT.getHoraSalida(), vueloT.getHoraLlegada(), vueloT.getFrecuencia(), vueloT.getTipoViaje(),vueloT.getAerolinea(), vueloT.getAvion(), vueloT.getAeropuertoSA(), vueloT.getAeropuertoLL(), vueloT.getDistancia(), vueloT.getDuracion(), vueloT.isRealizado(), costoEjec, costoEcon));					
		}
		return vueloViajeros;
	}

	public void updateVuelo(Vuelo vuelo) throws SQLException{
		String sql ="UPDATE ISIS2304A131620.VUELO SET AVION="+vuelo.getAvion()+"WHERE ID="+vuelo.getId();

		PreparedStatement prepStmt=conn.prepareStatement(sql);
		prepStmt.executeQuery();

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


	public void deleteVuelo(Vuelo vuelo) throws SQLException, Exception {

		String sql = "DELETE FROM ISIS2304A131620.VUELO";
		sql += " WHERE id = " + vuelo.getId();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);

		prepStmt.executeQuery();

	}



	public ViajeViajeros deleteVueloViajeros(int vueloViajeros) throws Exception
	{
		ViajeViajeros vuelo = getVueloViajeros(vueloViajeros);
		DAOTablaReservas daoReservas = new DAOTablaReservas();
		daoReservas.setConn(conn);
		DAOTablaAeropuertos daoAeropuertos = new DAOTablaAeropuertos();
		daoAeropuertos.setConn(conn);
		ArrayList<ReservaViajeros> reservas =  daoReservas.getReservasViajerosPorVuelo(vuelo.getId());
		ArrayList<ViajeViajeros> vuelos = getVuelosViajeros();
		ArrayList<ReservaViajeros> reservasACrear = new ArrayList<>();
		for(int i = 0; i<reservas.size(); i++)
		{
			ReservaViajeros reservaActual = reservas.get(i);
			if((reservaActual.getAeroSal().equals(vuelo.getAeropuertoSA().getCodigo()) && reservaActual.getAeroLleg().equals(vuelo.getAeropuertoLL().getCodigo())))
			{
				daoReservas.cancelarReservaViajeroVuelo(reservaActual.getIdReservaGeneral());
				Date min = new Date(Long.MAX_VALUE);
				SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
				min = format.parse(format.format(min));
				Vuelo ganador = null;
				for(int j =0; j< vuelos.size(); j++)
				{
					ViajeViajeros actual = vuelos.get(j);
					if( actual.getAeropuertoSA().getCodigo().equals(vuelo.getAeropuertoSA().getCodigo())&& actual.getAeropuertoLL().getCodigo().equals(vuelo.getAeropuertoLL().getCodigo())&& actual.getHoraSalida().after(vuelo.getHoraSalida())&& actual.getHoraSalida().before(min) )
					{
						min = actual.getHoraSalida();
						ganador = actual;
					}
				}
				if(ganador == null)
				{
					throw new Exception("No se ha podido encontrar otro vuelo para asignar las reservas");
				}

				conn.setSavepoint();


				reservaActual.setIdVuelo(ganador.getId());

				daoReservas.createReservaViajero(reservaActual);
			}
			else
			{
				conn.setSavepoint();
				daoReservas.cancelarReservaViajeroVuelo(reservaActual.getIdReservaGeneral());
				conn.setSavepoint();

				reservasACrear.add(reservaActual);
			}
		}

		String sql = "DELETE ISIS2304A131620.VUELO_VIAJEROS WHERE ID = " + vuelo.getId();


		PreparedStatement prepStmt = conn.prepareStatement(sql);

		prepStmt.executeUpdate();

		conn.setSavepoint();
		String sql2 = "DELETE ISIS2304A131620.VUELO WHERE ID = " + vuelo.getId();
		PreparedStatement prepStmt2 = conn.prepareStatement(sql2);

		prepStmt2.executeUpdate();

		for(int i = 0; i< reservasACrear.size(); i++)
		{
			ReservaViajeros actual = reservasACrear.get(i);
			daoReservas.createReservaVuelosMultiples(actual, this);
		}
		return vuelo;

	}
	public Vuelo getVuelo(int idVuelo) throws SQLException, ParseException {
		Vuelo vuelo=null;
		String sql="SELECT * FROM ISIS2304A131620.VUELO WHERE ID ="+idVuelo;
		PreparedStatement prepStmt= conn.prepareStatement(sql);
		ResultSet rs=prepStmt.executeQuery();
		if(rs.next()){
			int id= rs.getInt("ID");
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
			int duracion =rs.getInt("DURACION");
			double distancia= rs.getDouble("DISTANCIA");
			int frecuencia =rs.getInt("FRECUENCIA");
			String tipoViaje= rs.getString("TIPO_VIAJE");
			String aerolinea = rs.getString("AEROLINEA");
			String avion = rs.getString("AVION");
			String aeropuertoSA= rs.getString("AEROPUERTO_SALIDA");
			String aeropuertoLL= rs.getString("AEROPUERTO_LLEGADA");
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
			vuelo = new Vuelo(id, frecuencia, aerolinea, aeropuertoSalida, aeropuertoLLegada, distancia, duracion, tipoVuelo);
		
		}
		return vuelo;
	}

	public ViajeCarga getVueloCarga(int idVuelo) throws SQLException, ParseException {
		ViajeCarga vueloCarga=null;
		String sql="SELECT * FROM ISIS2304A131620.VUELO WHERE ID ="+idVuelo;
		PreparedStatement prepStmt= conn.prepareStatement(sql);
		ResultSet rs=prepStmt.executeQuery();
		if(rs.next()){

			Vuelo vueloT = getVuelo(idVuelo);

			double costo = rs.getDouble("COSTO_DENSIDAD");

			vueloCarga = new ViajeCarga(idVuelo, vueloT.getHoraSalida(), vueloT.getHoraLlegada(), vueloT.getFrecuencia(), vueloT.getTipoViaje(), vueloT.getAerolinea(), vueloT.getAvion(), vueloT.getAeropuertoSA(), vueloT.getAeropuertoLL(), vueloT.getDistancia(), vueloT.getDuracion(), vueloT.isRealizado(), costo);					
		}
		return vueloCarga;
	}

	public ViajeViajeros getVueloViajerosAeropuertos(String aeroSal, String aeroLleg) throws SQLException, ParseException {
		ViajeViajeros vuelo=null;
		String sql="SELECT * FROM ISIS2304A131620.VUELO WHERE (AEROPUERTO_SALIDA ='" + aeroSal+ "' AND AEROPUERTO_LLEGADA = '" + aeroLleg+ "')";
		PreparedStatement prepStmt= conn.prepareStatement(sql);
		ResultSet rs=prepStmt.executeQuery();
		if(rs.next()){
			int id= rs.getInt("ID");


			vuelo = getVueloViajeros(id);
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
				+ "(SELECT * FROM  VUELO_VIAJEROS)T2 ON T1.ID_VUELO = T2.ID) GROUP BY ID_VUELO";
		PreparedStatement prepStmt= conn.prepareStatement(sql);
		return prepStmt.executeQuery();
		
	}
	
	public ArrayList<ViajeViajeros> getVuelosViajerosPorViajero(ResultSet rs) throws SQLException, ParseException
	{
		ArrayList<ViajeViajeros> vuelos= new ArrayList<ViajeViajeros>();
		while(rs.next())
		{
			int id = rs.getInt("ID_VUELO");
			ViajeViajeros vuelo = getVueloViajeros(id);
	

				vuelos.add(vuelo);

		}
		return vuelos;
	}

	public ArrayList<ConsultaViajes> consultarViajesViajero(int idViajero, String tipoIdViajero) throws Exception
	{
		DAOTablaAerolina daoAerolineas = new DAOTablaAerolina();
		daoAerolineas.setConn(conn);
		DAOTablaReservas daoReservas = new DAOTablaReservas();
		daoReservas.setConn(conn);
		ResultSet rs = queryVuelosViajerosPorViajero(idViajero, tipoIdViajero);
		ArrayList<ViajeViajeros> vuelos = getVuelosViajerosPorViajero(rs);
		
		ArrayList<ConsultaViajes> consultas = new ArrayList<>();

		for(int i = 0; i< vuelos.size(); i++)
		{
			ViajeViajeros vuelo = vuelos.get(i);
			String aerolineaa = vuelo.getAerolinea();
			Aerolinea aerolinea = daoAerolineas.getAerolinea(aerolineaa);

			ArrayList<ReservaViajeros> reservas = daoReservas.getReservasViajerosPorVueloYViajero(idViajero,tipoIdViajero,vuelo.getId());

			
			String aeroLleg = vuelo.getAeropuertoLL().getCodigo();
			String aeroSal = vuelo.getAeropuertoSA().getCodigo();
			int idVuelo = vuelo.getId();

			double costo = generarReporte(vuelo.getId(), daoReservas);
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			String fechaLlegada = format.format(vuelo.getHoraLlegada());
			String fechaSalida =format.format(vuelo.getHoraSalida());
			Double millas = vuelo.getDistancia();

			consultas.add(new ConsultaViajes(reservas, idVuelo, aerolinea, fechaSalida, fechaLlegada, aeroSal, aeroLleg, millas, costo));
		}
		return consultas;
		
	}




	public ViajeViajeros getVueloViajeros(int idVuelo) throws SQLException, ParseException {
		ViajeViajeros vueloViajeros=null;
		String sql="SELECT * FROM ISIS2304A131620.VUELO_VIAJEROS WHERE ID ="+idVuelo;
		PreparedStatement prepStmt= conn.prepareStatement(sql);
		conn.setSavepoint();
		ResultSet rs=prepStmt.executeQuery();
		if(rs.next()){

			Vuelo vueloT = getVuelo(idVuelo);

			double costoEjec = rs.getDouble("COSTO_EJECUTIVO");
			double costoEcon = rs.getDouble("COSTO_ECONOMICO");
			vueloViajeros = new ViajeViajeros(idVuelo, vueloT.getHoraSalida(), vueloT.getHoraLlegada(), vueloT.getFrecuencia(), vueloT.getTipoViaje(), vueloT.getAerolinea(), vueloT.getAvion(), vueloT.getAeropuertoSA(), vueloT.getAeropuertoLL(), vueloT.getDistancia(), vueloT.getDuracion(), vueloT.isRealizado(), costoEjec, costoEcon);	
		}
		return vueloViajeros;
	}



	public ArrayList<ViajeViajeros> getVuelosViajerosRealizadosAvion(String numSerieAvion) throws SQLException, ParseException
	{
		ArrayList<ViajeViajeros> vuelos = new ArrayList<ViajeViajeros>();
		String sql="SELECT * FROM ISIS2304A131620.VUELO_VIAJEROS WHERE AVION = '"+numSerieAvion+"' AND REALIZADO = 'Y'";
		PreparedStatement prepStmt= conn.prepareStatement(sql);
		ResultSet rs=prepStmt.executeQuery();
		while(rs.next()){

			int idVuelo = rs.getInt("ID");
			Vuelo vueloT = getVuelo(idVuelo);
			double costoEjec = rs.getDouble("COSTO_EJECUTIVO");
			double costoEcon = rs.getDouble("COSTO_ECONOMICO");
			ViajeViajeros vuelo = new ViajeViajeros(idVuelo, vueloT.getHoraSalida(), vueloT.getHoraLlegada(), vueloT.getFrecuencia(), vueloT.getTipoViaje(), vueloT.getAerolinea(), vueloT.getAvion(), vueloT.getAeropuertoSA(), vueloT.getAeropuertoLL(), vueloT.getDistancia(), vueloT.getDuracion(), vueloT.isRealizado(), costoEjec, costoEcon);			
			vuelos.add(vuelo);
		}
		return vuelos;
	}



	public ArrayList<ViajeCarga> getVuelosCargaRealizadosAvion(String numSerieAvion) throws SQLException, ParseException
	{
		ArrayList<ViajeCarga> vuelos = new ArrayList<ViajeCarga>();
		String sql="SELECT * FROM ISIS2304A131620.VUELO_CARGA WHERE AVION = '"+numSerieAvion+"' AND REALIZADO = 'Y'";
		PreparedStatement prepStmt= conn.prepareStatement(sql);
		ResultSet rs=prepStmt.executeQuery();
		while(rs.next()){

			int idVuelo = rs.getInt("ID");
			Vuelo vueloT = getVuelo(idVuelo);
			double costo = rs.getDouble("COSTO_DENSIDAD");
			ViajeCarga vuelo = new ViajeCarga(idVuelo, vueloT.getHoraSalida(), vueloT.getHoraLlegada(), vueloT.getFrecuencia(), vueloT.getTipoViaje(), vueloT.getAerolinea(), vueloT.getAvion(), vueloT.getAeropuertoSA(), vueloT.getAeropuertoLL(), vueloT.getDistancia(), vueloT.getDuracion(), vueloT.isRealizado(), costo);			
			vuelos.add(vuelo);
		}
		return vuelos;
	}

	public ArrayList<Vuelo> getVuelosAeropuerto(String aeropuerto) throws SQLException {
		Vuelo vuelo=null;
		ArrayList<Vuelo> vuelos = new ArrayList<Vuelo>();
		String sql="SELECT * FROM ISIS2304A131620.VUELO WHERE (ISIS2304A131620.VUELO.AEROPUERTO_SALIDA ='" + aeropuerto+ "' OR ISIS2304A131620.VUELO.AEROPUERTO_LLEGADA = '" + aeropuerto+ "')";
		PreparedStatement prepStmt= conn.prepareStatement(sql);
		ResultSet rs=prepStmt.executeQuery();
		while(rs.next()){
			int id= rs.getInt("ID");
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

			vuelo = new Vuelo(id, frecuencia, aerolinea, aeropuertoSalida, aeropuertoLLegada, distancia, duracion, tipoVuelo);					
			vuelos.add(vuelo);
		}
		return vuelos;
	}
	public ArrayList<Vuelo> getVuelosAeropuertoRangoFechas(String aeropuerto, Date fechaMin, Date fechaMax) throws SQLException {
		Vuelo vuelo=null;
		ArrayList<Vuelo> vuelos = new ArrayList<Vuelo>();
		String sql="SELECT * FROM ISIS2304A131620.VUELO WHERE ((ISIS2304A131620.VUELO.AEROPUERTO_SALIDA =" + aeropuerto+ "OR ISIS2304A131620.VUELO.AEROPUERTO_LLEGADA = "+aeropuerto+")AND "+fechaMin+" <= ISIS2304A131620.VUELO.HORA_SALIDA AND "+fechaMax+" > ISIS2304A131620.VUELO.HORA_SALIDA);";
		PreparedStatement prepStmt= conn.prepareStatement(sql);
		ResultSet rs=prepStmt.executeQuery();
		while(rs.next()){
			int id= rs.getInt("ID");
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

			vuelo = new Vuelo(id, frecuencia, aerolinea, aeropuertoSalida, aeropuertoLLegada, distancia, duracion, tipoVuelo);		
			vuelos.add(vuelo);
		}
		return vuelos;
	}

	public ArrayList<Vuelo> getVuelosAeropuertoAerolinea(String aeropuerto, String aerolinea) throws SQLException {
		Vuelo vuelo=null;
		ArrayList<Vuelo> vuelos = new ArrayList<Vuelo>();
		String sql="SELECT * FROM ISIS2304A131620.VUELO WHERE ((ISIS2304A131620.VUELO.AEROPUERTO_SALIDA =" + aeropuerto+ "OR ISIS2304A131620.VUELO.AEROPUERTO_LLEGADA = "+aeropuerto+")AND ISIS2304A131620.VUELO.AEROLINEA = "+aerolinea+");";
		PreparedStatement prepStmt= conn.prepareStatement(sql);
		ResultSet rs=prepStmt.executeQuery();
		while(rs.next()){
			int id= rs.getInt("ID");
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

			vuelo = new Vuelo(id, frecuencia, aerolinea, aeropuertoSalida, aeropuertoLLegada, distancia, duracion, tipoVuelo);		
			vuelos.add(vuelo);
		}
		return vuelos;
	}
	public ArrayList<Vuelo> getVuelosAeropuertoHoraSalida(String aeropuerto, Date horaSalida) throws SQLException {
		Vuelo vuelo=null;
		ArrayList<Vuelo> vuelos = new ArrayList<Vuelo>();
		String sql="SELECT * FROM ISIS2304A131620.VUELO WHERE ((ISIS2304A131620.VUELO.AEROPUERTO_SALIDA =" + aeropuerto+ "OR ISIS2304A131620.VUELO.AEROPUERTO_LLEGADA = "+aeropuerto+")AND "+horaSalida+" = ISIS2304A131620.VUELO.HORA_SALIDA);";
		PreparedStatement prepStmt= conn.prepareStatement(sql);
		ResultSet rs=prepStmt.executeQuery();
		while(rs.next()){
			int id= rs.getInt("ID");
			horaSalida= rs.getDate("HORA_SALIDA");
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

			vuelo = new Vuelo(id, frecuencia, aerolinea, aeropuertoSalida, aeropuertoLLegada, distancia, duracion, tipoVuelo);		
			vuelos.add(vuelo);
		}
		return vuelos;
	}

	public ArrayList<Vuelo> getVuelosAeropuertoHoraLlegada(String aeropuerto, Date horaLlegada) throws SQLException {
		Vuelo vuelo=null;
		ArrayList<Vuelo> vuelos = new ArrayList<Vuelo>();
		String sql="SELECT * FROM ISIS2304A131620.VUELO WHERE ((ISIS2304A131620.VUELO.AEROPUERTO_SALIDA =" + aeropuerto+ "OR ISIS2304A131620.VUELO.AEROPUERTO_LLEGADA = "+aeropuerto+")AND "+horaLlegada+" = ISIS2304A131620.VUELO.HORA_LLEGADA);";
		PreparedStatement prepStmt= conn.prepareStatement(sql);
		ResultSet rs=prepStmt.executeQuery();
		while(rs.next()){
			int id= rs.getInt("ID");
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

			vuelo = new Vuelo(id, frecuencia, aerolinea, aeropuertoSalida, aeropuertoLLegada, distancia, duracion, tipoVuelo);		
			vuelos.add(vuelo);
		}
		return vuelos;
	}



	public ArrayList<Vuelo> getVuelosAeropuertoViajeros(String aeropuerto) throws SQLException {
		Vuelo vuelo=null;
		ArrayList<Vuelo> vuelos = new ArrayList<Vuelo>();
		String sql="SELECT * FROM(SELECT * FROM ISIS2304A131620.VUELO WHERE(ISIS2304A131620.VUELO.AEROPUERTO_SALIDA = "+aeropuerto+" OR ISIS2304A131620.VUELO.AEROPUERTO_LLEGADA = "+aeropuerto+"))c1 INNER JOIN ISIS2304A131620.AVION_VIAJEROS ON (c1.AVION = ISIS2304A131620.AVION_VIAJEROS.NUM_SERIE);";
		PreparedStatement prepStmt= conn.prepareStatement(sql);
		ResultSet rs=prepStmt.executeQuery();
		while(rs.next()){
			int id= rs.getInt("ID");
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

			vuelo = new Vuelo(id, frecuencia, aerolinea, aeropuertoSalida, aeropuertoLLegada, distancia, duracion, tipoVuelo);
			vuelos.add(vuelo);
		}
		return vuelos;
	}

	public ArrayList<ViajeCarga> getVuelosAeropuertoCarga(String aeropuerto) throws SQLException {
		ViajeCarga vuelo=null;
		ArrayList<ViajeCarga> vuelos = new ArrayList<ViajeCarga>();
		String sql="SELECT * FROM(SELECT * FROM ISIS2304A131620.VUELO WHERE(ISIS2304A131620.VUELO.AEROPUERTO_SALIDA = "+aeropuerto+" OR ISIS2304A131620.VUELO.AEROPUERTO_LLEGADA = "+aeropuerto+"))c1 INNER JOIN ISIS2304A131620.AVION_VIAJEROS ON (c1.AVION = ISIS2304A131620.AVION_CARGA.NUM_SERIE);";
		PreparedStatement prepStmt= conn.prepareStatement(sql);
		ResultSet rs=prepStmt.executeQuery();
		while(rs.next()){
			int id= rs.getInt("ID");
			Date horaSalida= rs.getDate("HORA_SALIDA");
			Date horaLlegada=rs.getDate("HORA_LLEGADA");
			String duracion =rs.getString("DURACION");
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

			vuelos.add(vuelo);
		}
		return vuelos;
	}


	public void asociarVuelo(int idVuelo, String idAvion) throws SQLException, Exception
	{
		DAOTablaReservas daoReservas = new DAOTablaReservas();
		DAOTablaAviones daoAviones = new DAOTablaAviones();
		DAOTablaCargas daoCargas = new DAOTablaCargas();
		Vuelo vuelo = vuelos.get(idVuelo);
		Avion avion = daoAviones.darAvion(idAvion);

		if(avion.getTipo().equals(vuelo.getTipoViaje()))
		{
			if(avion.getTipo() == Avion.CARGA)
			{
				ArrayList<ReservaCarga> reservas = daoReservas.getReservasCargaPorVuelo(vuelo.getId());
				AvionCarga avionCarga = daoAviones.darAvionCarga(idAvion);
				int cargaTotal = 0;
				for(int i = 0; i<reservas.size();i++)
				{
					ReservaCarga reserva = reservas.get(i);
					Carga carga = daoCargas.getCarga(reserva.getIdCarga());
					cargaTotal += carga.getPeso();
				}
				if(avionCarga.getCargaMax() >= cargaTotal )
				{
					vuelo.setAvion(avion.getNumSerie());
					updateVuelo(vuelo);
				}
			}
			else if (avion.getTipo() == Avion.VIAJEROS)
			{
				ArrayList<ReservaViajeros> reservas = daoReservas.getReservasViajerosPorVuelo(idVuelo);
				AvionViajeros avionViaje = daoAviones.darAvionViajeros(idAvion);
				int puestosEconomica = 0;
				int puestosEjecutiva = 0;
				for(int i = 0; i<reservas.size();i++)
				{
					ReservaViajeros reserva = reservas.get(i);
					if(reserva.getClase().equals(ReservaViajeros.ECONOMICA))
					{
						puestosEconomica++;
					}
					else if(reserva.getClase().equals(ReservaViajeros.EJECUTIVA))
					{
						puestosEjecutiva++;
					}
				}
				if(puestosEconomica <= avionViaje.getSillasEconomicas() && puestosEjecutiva <= avionViaje.getSillasEjecutivas())
				{
					vuelo.setAvion(avion.getNumSerie());
					updateVuelo(vuelo);
				}
			}
		}
		else
		{
			throw new Exception("El avi�n con el que se quiere vincular la reserva no es del tipo "+ vuelo.getTipoViaje());
		}






	}

	public double generarReporte(int idVuelo, DAOTablaReservas daoReservas) throws SQLException, ParseException
	{
		DAOTablaCargas daoCargas = new DAOTablaCargas();
		daoCargas.setConn(conn);
		Vuelo vuelo = getVuelo(idVuelo);
		double costo = 0;
		if(vuelo.getTipoVuelo().equals(Vuelo.CARGA))
		{
			ViajeCarga vueloC = getVueloCarga(idVuelo);
			double densidadTotal = 0;
			ArrayList<ReservaCarga> reservasC = daoReservas.getReservasCargaPorVuelo(idVuelo);
			for(int i = 0; i < reservasC.size();i++)
			{
				ReservaCarga reservaActual = reservasC.get(i);
				Carga carga = daoCargas.getCarga(reservaActual.getIdCarga());
				double densidad = (double)(carga.getPeso()/carga.getVolumen());

				densidadTotal+= densidad;
			}
			costo = vueloC.getCostoDensidad()*densidadTotal;
		}
		else if(vuelo.getTipoVuelo().equals(Vuelo.VIAJEROS))
		{
			ViajeViajeros vueloV = getVueloViajeros(idVuelo);
			int pasajerosEconomicos = 0;
			int pasajerosEjecutivos = 0;
			ArrayList<ReservaViajeros> reservasV = daoReservas.getReservasViajerosPorVuelo(idVuelo);
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
	public void registrarVuelo(int idVuelo) throws SQLException{
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
	
	public ArrayList<Vuelo> getViajesAeropuertoAerolineaOrganizado(String aeropuerto, String aerolinea, String organizacion) throws SQLException {
		Vuelo vuelo=null;
		ArrayList<Vuelo> vuelos = new ArrayList<Vuelo>();
		String sql="SELECT * FROM VIAJE JOIN (SELECT * FROM VUELO WHERE ((VUELO.AEROPUERTO_SALIDA =" + aeropuerto+ "OR ISIS2304A131620.VUELO.AEROPUERTO_LLEGADA = "+aeropuerto+")AND VUELO.AEROLINEA = "+aerolinea+") ORDER BY "+organizacion+")t1 ON VIAJE.ID_VUELO = t1.ID";
		PreparedStatement prepStmt= conn.prepareStatement(sql);
		ResultSet rs=prepStmt.executeQuery();
		while(rs.next()){
			int id= rs.getInt("ID");
			Date horaSalida= rs.getDate("HORA_SALIDA");
			Date horaLlegada=rs.getDate("HORA_LLEGADA");
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

			vuelo = new Viaje(id, horaSalida, horaLlegada, frecuencia, tipoViaje, aerolinea, avion, aeropuertoSalida, aeropuertoLLegada, distancia, duracion, realizadoo,tipoVuelo);		
			vuelos.add(vuelo);
		}
		return vuelos;
	}
	
	public Vuelo darVueloDeViaje(int idViaje) throws SQLException
	{
		String sql = "SELECT * FROM VUELO WHERE ID = (SELECT ID_VUELO FROM VIAJE WHERE ID = "+idViaje+")";
		PreparedStatement prepStmt= conn.prepareStatement(sql);
		ResultSet rs=prepStmt.executeQuery();
		if(rs.next()){
			int id= rs.getInt("ID");
			int frecuencia =rs.getInt("FRECUENCIA");
			String aerolinea = rs.getString("AEROLINEA");
			String aeropuertoLL= rs.getString("AEROPUERTO_SALIDA");
			String aeropuertoSA= rs.getString("AEROPUERTO_LLEGADA");
			Aeropuerto aeropuertoLLegada = getAeropuerto(aeropuertoLL);
			Aeropuerto aeropuertoSalida= getAeropuerto(aeropuertoSA);
			String tipoVuelo = rs.getString("TIPO_VUELO");
			int distancia = rs.getInt("DISTANCIA");
			int duracion = rs.getInt("DURACION");
			return new Vuelo(id,frecuencia,aerolinea,aeropuertoSalida,aeropuertoLLegada,distancia,duracion,tipoVuelo);

		}
		return null;
	}
	
	public ArrayList<Viaje> getViajesAeropuertoAeronaveOrganizado(String aeropuerto, String aeronave, String organizacion) throws SQLException {
		Viaje viaje=null;
		ArrayList<Viaje> viajes = new ArrayList<Viaje>();
		String sql="SELECT * FROM ISIS2304A131620.VUELO JOIN ISIS2304A131620.VIAJE ON ISIS2304A131620.VUELO.ID= ISIS2304A131620.VIAJE.ID_VUELO WHERE ((ISIS2304A131620.VUELO.AEROPUERTO_SALIDA =" + aeropuerto+ "OR ISIS2304A131620.VUELO.AEROPUERTO_LLEGADA = "+aeropuerto+")AND ISIS2304A131620.VIAJE.AVION = "+aeronave+") ORDER BY"+organizacion+";";
		PreparedStatement prepStmt= conn.prepareStatement(sql);
		ResultSet rs=prepStmt.executeQuery();
		while(rs.next()){
			int id= rs.getInt("ID");
			Vuelo vuelo = darVueloDeViaje(id);
			int idVuelo = vuelo.getId();
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

			viaje = new Viaje(id,idVuelo,horaSalida,horaLlegada,frecuencia,tipoViaje,aerolinea,avion,aeropuertoSA,aeropuertoLL,distancia,duracion,realizadoo,tipoVuelo);
			viajes.add(viaje);
		}
		return viajes;
	}
	public ArrayList<Vuelo> getVuelosAeropuertoRangoFechasOrganizado(String aeropuerto, Date fechaMin, Date fechaMax, String organizacion) throws SQLException {
		Vuelo vuelo=null;
		ArrayList<Vuelo> vuelos = new ArrayList<Vuelo>();
		//TODO fecha llegada
		String sql="SELECT * FROM ISIS2304A131620.VUELO JOIN ISIS2304A131620.VIAJE ON ISIS2304A131620.VUELO.ID= ISIS2304A131620.VIAJE.ID_VUELO WHERE ((ISIS2304A131620.VUELO.AEROPUERTO_SALIDA =" + aeropuerto+ "OR ISIS2304A131620.VUELO.AEROPUERTO_LLEGADA = "+aeropuerto+")AND "+fechaMin+" <= ISIS2304A131620.VUELO.HORA_SALIDA AND "+fechaMax+" > ISIS2304A131620.VUELO.HORA_SALIDA) ORDER BY"+organizacion+";";
		PreparedStatement prepStmt= conn.prepareStatement(sql);
		ResultSet rs=prepStmt.executeQuery();
		while(rs.next()){
			int id= rs.getInt("ID");
			Date horaSalida= rs.getDate("HORA_SALIDA");
			Date horaLlegada=rs.getDate("HORA_LLEGADA");
			String duracion =rs.getString("DURACION");
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

			vuelo = new Vuelo(id, horaSalida, horaLlegada, frecuencia, tipoViaje, aerolinea, avion, aeropuertoSalida, aeropuertoLLegada, distancia, duracion, realizadoo,tipoVuelo);		
			vuelos.add(vuelo);
		}
		return vuelos;
	}
	public ArrayList<Vuelo> getVuelosAeropuertoHoraSalidaLlegadaOrganizado(String aeropuerto, Date fechaLlgada, Date fechaSalida, String organizacion) throws SQLException {
		Vuelo vuelo=null;
		ArrayList<Vuelo> vuelos = new ArrayList<Vuelo>();
		//TODO hora LLegada
		String sql="SELECT * FROM ISIS2304A131620.VUELO JOIN ISIS2304A131620.VIAJE ON ISIS2304A131620.VUELO.ID= ISIS2304A131620.VIAJE.ID_VUELO WHERE ((ISIS2304A131620.VUELO.AEROPUERTO_SALIDA =" + aeropuerto+ "OR ISIS2304A131620.VUELO.AEROPUERTO_LLEGADA = "+aeropuerto+")AND "+fechaLlgada+"= ISIS2304A131620.VUELO.HORA_LLEGADA OR "+fechaSalida+"= ISIS2304A131620.VUELO.HORA_SALIDA) ORDER BY"+organizacion+";";
		PreparedStatement prepStmt= conn.prepareStatement(sql);
		ResultSet rs=prepStmt.executeQuery();
		while(rs.next()){
			int id= rs.getInt("ID");
			Date horaSalida= rs.getDate("HORA_SALIDA");
			Date horaLlegada=rs.getDate("HORA_LLEGADA");
			String duracion =rs.getString("DURACION");
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

			vuelo = new Vuelo(id, horaSalida, horaLlegada, frecuencia, tipoViaje, aerolinea, avion, aeropuertoSalida, aeropuertoLLegada, distancia, duracion, realizadoo,tipoVuelo);		
			vuelos.add(vuelo);
		}
		return vuelos;
	}

	public int cantidadViajerosViaje(int idViaje) throws SQLException
	{
		int i = 0;
		String sql = "SELECT * FROM M_VIAJES_M_VIAJEROS WHERE ID_VIAJE = "+idViaje;
		PreparedStatement prepStmt= conn.prepareStatement(sql);
		ResultSet rs=prepStmt.executeQuery();
		while(rs.next()){
			i ++;
		}
		return i;
	}
	public double cantidadCargaViaje(int idViaje) throws SQLException
	{
		DAOTablaCargas daoCargas = new DAOTablaCargas();
		daoCargas.setConn(conn);
		String sql = "SELECT * FROM M_VIAJES_M_CARGAS WHERE ID_VIAJE = "+idViaje;
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

	public ArrayList<Vuelo> getVuelosAeropuertoNoParametro(String idAeropuerto, Date fecha1, Date fecha2,
			String aerolinea, String aeronave, Date fechaSalida, Date fechaLlegada) throws Exception{
		Vuelo vuelo=null;
		ArrayList<Vuelo> vuelos = new ArrayList<Vuelo>();
		//TODO Fecha Llegada
		String sql="SELECT * FROM ISIS2304A131620.VUELO JOIN ISIS2304A131620.VIAJE ON ISIS2304A131620.VUELO.ID= ISIS2304A131620.VIAJE.ID_VUELO WHERE (((ISIS2304A131620.VUELO.AEROPUERTO_SALIDA =" + idAeropuerto+ "OR ISIS2304A131620.VUELO.AEROPUERTO_LLEGADA = "+idAeropuerto+")AND "+fecha1+">= ISIS2304A131620.VIAJE.HORA_SALIDA OR "+fecha2+"= ISIS2304A131620.VIAJE.HORA_SALIDA) AND ISIS2304A131620.VUELO.AEROLINEA!="+aerolinea+" AND ISIS2304A131620.VIAJE.AVION!="+aeronave+"AND ISIS2304A131620.VIAJE.HORA_SALIDA!="+fechaSalida+";";
		PreparedStatement prepStmt= conn.prepareStatement(sql);
		ResultSet rs=prepStmt.executeQuery();
		while(rs.next()){
			int id= rs.getInt("ID");
			Date horaSalida= rs.getDate("HORA_SALIDA");
			Date horaLlegada=rs.getDate("HORA_LLEGADA");
			String duracion =rs.getString("DURACION");
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

			vuelo = new Vuelo(id, horaSalida, horaLlegada, frecuencia, tipoViaje, aerolinea, avion, aeropuertoSalida, aeropuertoLLegada, distancia, duracion, realizadoo,tipoVuelo);		
			vuelos.add(vuelo);
		}
		return vuelos;
	}
	public ArrayList<ConsultaViajes> consultarViajesViajeroParametros(int idViajero, String tipoIdViajero, String clase, double millas) throws Exception
	{
		DAOTablaAerolina daoAerolineas = new DAOTablaAerolina();
		daoAerolineas.setConn(conn);
		DAOTablaReservas daoReservas = new DAOTablaReservas();
		daoReservas.setConn(conn);
		ResultSet rs = queryVuelosViajerosPorViajeroConParametros(idViajero, tipoIdViajero, clase, millas);
		ArrayList<VueloViajeros> vuelos = getVuelosViajerosPorViajero(rs);
		
		ArrayList<ConsultaViajes> consultas = new ArrayList<>();

		for(int i = 0; i< vuelos.size(); i++)
		{
			VueloViajeros vuelo = vuelos.get(i);
			String aerolineaa = vuelo.getAerolinea();
			Aerolinea aerolinea = daoAerolineas.getAerolinea(aerolineaa);

			ArrayList<ReservaViajeros> reservas = daoReservas.getReservasViajerosPorVueloYViajero(idViajero,tipoIdViajero,vuelo.getId());

			
			String aeroLleg = vuelo.getAeropuertoLL().getCodigo();
			String aeroSal = vuelo.getAeropuertoSA().getCodigo();
			int idVuelo = vuelo.getId();

			double costo = generarReporte(vuelo.getId(), daoReservas);
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			String fechaLlegada = format.format(vuelo.getHoraLlegada());
			String fechaSalida =format.format(vuelo.getHoraSalida());
			millas = vuelo.getDistancia();

			consultas.add(new ConsultaViajes(reservas, idVuelo, aerolinea, fechaSalida, fechaLlegada, aeroSal, aeroLleg, millas, costo));
		}
		return consultas;
		
	}


	public ConsultaTraficoAereo getTraficoAereoCiudades(String ciudad1, String ciudad2, int dia1, String mes1, int anio1,
			int dia2, String mes2, int anio2) {
		String sql = "SELECT * FROM (select * FROM VIAJE WHERE TO_DATE(HORA_SALIDA) >= TO_DATE('"+dia1+"-"+mes1+"-"+anio1+"') AND TO_DATE(HORA_LLEGADA) <= TO_DATE('"+dia2+"-"+mes2+"-"+anio2+"'))t6 "
				+ "JOIN (SELECT * FROM (SELECT * FROM VUELO JOIN (SELECT COD_IATA FROM AEROPUERTO WHERE NOMBRE_CIUDAD = 'Caracas')t2 "
				+ "ON VUELO.AEROPUERTO_SALIDA = t2.COD_IATA)t4 JOIN (SELECT COD_IATA FROM AEROPUERTO WHERE NOMBRE_CIUDAD = 'New York')t3 ON "
				+ "t3.COD_IATA = t4.AEROPUERTO_LLEGADA)t5 ON t5.ID = t6.ID_VUELO";
		PreparedStatement prp =conn.prepareStatement(sql);
		ResultSet rs = prp.executeQuery();
		while(rs.next()){
			int idViaje= rs.getInt("ID");
			Date horaSalida= rs.getDate("HORA_SALIDA");
			Date horaLlegada=rs.getDate("HORA_LLEGADA");
			String duracion =rs.getString("DURACION");
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

			vuelo = new Vuelo(id, horaSalida, horaLlegada, frecuencia, tipoViaje, aerolinea, avion, aeropuertoSalida, aeropuertoLLegada, distancia, duracion, realizadoo,tipoVuelo);		
			vuelos.add(vuelo);
		}
		return vuelos;
		
      
	}




}