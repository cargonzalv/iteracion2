package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import estructuras.Dijkstra;
import estructuras.GrafoDirigido;
import vos.Aeropuerto;
import vos.Avion;
import vos.AvionCarga;
import vos.AvionViajeros;
import vos.Carga;
import vos.CreacionReservaGrupalYCarga;
import vos.ProductoReservaGrupalYCarga;
import vos.Reserva;
import vos.ReservaCarga;
import vos.ReservaViajeros;
import vos.ReservaViajerosMultiple;
import vos.ReservaViajeros;
import vos.Viajeros;
import vos.Vuelo;
import vos.ViajeViajeros;

public class DAOTablaReservas {
	private ArrayList<Object> reservas;
	private Connection conn;
	public DAOTablaReservas() throws SQLException  {
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

	public Reserva getReserva(int idReserva) throws SQLException {

		Reserva reserva= null;
		String sql= "SELECT * FROM ISIS2304A131620.RESERVA WHERE ID = "+idReserva;
		PreparedStatement prepStmt= conn.prepareStatement(sql);
		ResultSet rs=prepStmt.executeQuery();
		if(rs.next()){
			String fecha = rs.getString("FECHA");
			String tipoReserva = rs.getString("TIPO_RESERVA");
			String aeroSal = rs.getString("AERO_SAL");
			String aeroLleg = rs.getString("AERO_LLEG");
			reserva= new Reserva(idReserva, fecha,tipoReserva,aeroSal, aeroLleg);
		}
		return reserva;
	}
	public ArrayList<Reserva> getReservas() throws SQLException {
		ArrayList<Reserva> reserva= new ArrayList<Reserva>();
		String sql= "SELECT * FROM ISIS2304A131620.RESERVA ORDER BY ID ASC";
		PreparedStatement prepStmt= conn.prepareStatement(sql);
		ResultSet rs=prepStmt.executeQuery();
		while(rs.next()){
			int id= rs.getInt("ID");
			String fecha = rs.getString("FECHA");
			String tipoReserva = rs.getString("TIPO_RESERVA");
			String aeroSal = rs.getString("AERO_SAL");
			String aeroLleg = rs.getString("AERO_LLEG");
			reserva.add(new Reserva(id, fecha,tipoReserva,aeroSal, aeroLleg));		
		}
		return reserva;
	}
	public ArrayList<ReservaViajeros> getReservasViajeros() throws SQLException {
		ArrayList<ReservaViajeros> reservas= new ArrayList<ReservaViajeros>();
		String sql= "SELECT * FROM ISIS2304A131620.RESERVA_VIAJEROS ORDER BY ID ASC";
		PreparedStatement prepStmt= conn.prepareStatement(sql);
		ResultSet rs=prepStmt.executeQuery();
		while(rs.next()){
			int id = rs.getInt("ID");
			int idReserva = rs.getInt("ID_RESERVA_GENERAL");
			Reserva reservaP = getReserva(idReserva);
			int idViajero=rs.getInt("ID_VIAJERO");
			String tipoId = rs.getString("TIPO_ID_VIAJERO");
			int idVuelo = rs.getInt("ID_VUELO");
			String tipo = rs.getString("TIPO");
			ReservaViajeros reserva = new ReservaViajeros(id, idReserva ,idVuelo, idViajero,tipoId, tipo, reservaP.getFecha(),Reserva.VIAJEROS, reservaP.getAeroSal(),reservaP.getAeroLleg());
			reservas.add(reserva);
		}
		return reservas;
	}
	public ArrayList<ReservaViajeros> getReservasViajerosPorReservaGeneral(int idReserva, DAOTablaVuelos daoVuelos) throws SQLException, ParseException {
		ArrayList<ReservaViajeros> reservas= new ArrayList<ReservaViajeros>();
		String sql= "SELECT * FROM ISIS2304A131620.RESERVA_VIAJEROS WHERE ID_RESERVA_GENERAL = "+idReserva;
		PreparedStatement prepStmt= conn.prepareStatement(sql);
		ResultSet rs=prepStmt.executeQuery();
		while(rs.next()){
			int id = rs.getInt("ID");
			Reserva reservaP = getReserva(idReserva);
			int idViajero=rs.getInt("ID_VIAJERO");
			String tipoId = rs.getString("TIPO_ID_VIAJERO");
			int idVuelo = rs.getInt("ID_VUELO");
			ViajeViajeros vuelo = daoVuelos.getVueloViajeros(idVuelo);
			String tipo = rs.getString("TIPO");
			ReservaViajeros reserva = new ReservaViajeros(id, idReserva ,idVuelo, idViajero,tipoId, tipo, reservaP.getFecha(),Reserva.VIAJEROS, vuelo.getAeropuertoSA().getCodigo(),vuelo.getAeropuertoLL().getCodigo());
			reservas.add(reserva);
		}
		return reservas;
	}
	public ReservaViajeros getReservaViajeros(int id) throws SQLException {
		ReservaViajeros reserva= null;
		String sql= "SELECT * FROM ISIS2304A131620.RESERVA_VIAJEROS";
		PreparedStatement prepStmt= conn.prepareStatement(sql);
		ResultSet rs=prepStmt.executeQuery();
		if(rs.next()){
			int idReserva = rs.getInt("ID_RESERVA_GENERAL");
			Reserva reservaP = getReserva(idReserva);
			int idViajero=rs.getInt("ID_VIAJERO");
			int idVuelo = rs.getInt("ID_VUELO");
			String tipoId = rs.getString("TIPO_ID_VIAJERO");
			String tipo = rs.getString("TIPO");
			reserva = new ReservaViajeros(id, idReserva ,idVuelo, idViajero,tipoId, tipo, reservaP.getFecha(),Reserva.VIAJEROS, reservaP.getAeroSal(),reservaP.getAeroLleg());
		}
		return reserva;
	}


	public int getIdVueloReserva(int idReserva) throws SQLException
	{
		String sql= "SELECT * FROM ISIS2304A131620.RESERVA_VIAJEROS WHERE ID ="+idReserva;
		PreparedStatement prepStmt= conn.prepareStatement(sql);
		ResultSet rs=prepStmt.executeQuery();
		if(rs.next()){
			int idVuelo=rs.getInt("ID_VUELO");
			return idVuelo;
		}
		return -1;
	}
	public ArrayList<ReservaViajeros> getReservasViajerosPorVuelo(int idVuelo) throws SQLException {
		ArrayList<ReservaViajeros> reserva= new ArrayList<ReservaViajeros>();
		String sql= "SELECT * FROM ISIS2304A131620.RESERVA_VIAJEROS WHERE ID_VUELO ="+idVuelo;
		PreparedStatement prepStmt= conn.prepareStatement(sql);
		ResultSet rs=prepStmt.executeQuery();
		while(rs.next()){
			int id= rs.getInt("ID");
			int idReserva = rs.getInt("ID_RESERVA_GENERAL");
			String clase = rs.getString("TIPO");
			int idViajero = rs.getInt("ID_VIAJERO");
			String tipoId = rs.getString("TIPO_ID_VIAJERO");

			Reserva reservaP = getReserva(idReserva);
			reserva.add(new ReservaViajeros(id,idReserva, idVuelo, idViajero,tipoId, clase, reservaP.getFecha(),Reserva.VIAJEROS, reservaP.getAeroSal(),reservaP.getAeroLleg()));

		}
		return reserva;
	}



	public ReservaCarga getReservaCarga(int idReserva) throws SQLException {
		Reserva reservaP = getReserva(idReserva);
		ReservaCarga reserva= null;
		String sql= "SELECT * FROM ISIS2304A131620.RESERVA_CARGA WHERE ID ='"+idReserva+"'";
		PreparedStatement prepStmt= conn.prepareStatement(sql);
		ResultSet rs=prepStmt.executeQuery();
		if(rs!=null){
			int id= rs.getInt("ID");
			int idVuelo=rs.getInt("ID_VUELO");
			int idViajero=rs.getInt("ID_REMITENTE");
			int idCarga = rs.getInt("ID_CARGA");
			String tipoId = rs.getString("TIPO_ID_REMITENTE");

			reserva =new ReservaCarga(id, idVuelo, idViajero,tipoId, idCarga,reservaP.getFecha(),Reserva.CARGA, reservaP.getAeroSal(),reservaP.getAeroLleg());
		}
		return reserva;
	}



	public ArrayList<ViajeViajeros> getVuelosReservaMultiple(int idReservaGeneral) throws Exception
	{
		DAOTablaVuelos daoVuelos = new DAOTablaVuelos();
		daoVuelos.setConn(conn);
		ArrayList<ViajeViajeros> vuelos = new ArrayList<ViajeViajeros>();
		String sql= "SELECT * FROM ISIS2304A131620.RESERVA_VIAJEROS WHERE ID_RESERVA_GENERAL ="+idReservaGeneral+" ORDER BY ORDEN ASC";
		PreparedStatement prepStmt= conn.prepareStatement(sql);
		ResultSet rs=prepStmt.executeQuery();
		while(rs.next()){
			int idVuelo= rs.getInt("ID_VUELO");


			vuelos.add(daoVuelos.getVueloViajeros(idVuelo));
		}
		return vuelos;
	}

	public ArrayList<ReservaViajeros> getReservasViajerosPorVueloYViajero(int idViajero, String tipoIdViajero, int idVuelo) throws SQLException
	{
		String sql= "SELECT * FROM (SELECT * FROM RESERVA_VIAJEROS WHERE ID_VIAJERO = "+idViajero+" AND "
				+ "TIPO_ID_VIAJERO = '"+tipoIdViajero+"')T1 JOIN "
				+ "(SELECT * FROM  VUELO_VIAJEROS WHERE ID = "+idVuelo+")T2 ON T1.ID_VUELO = T2.ID";
		PreparedStatement prepStmt= conn.prepareStatement(sql);
		ResultSet rs = prepStmt.executeQuery();
		ArrayList<ReservaViajeros> reservas= new ArrayList<ReservaViajeros>();
		while(rs.next()){
			int id = rs.getInt("ID");
			ReservaViajeros res = getReservaViajeros(id);

			reservas.add(res);

		}
		return reservas;
	}

	public ArrayList<ReservaCarga> getReservasCargaPorVuelo(int idVuelo) throws SQLException {
		ArrayList<ReservaCarga> reserva= new ArrayList<ReservaCarga>();
		String sql= "SELECT * FROM ISIS2304A131620.RESERVA_CARGA WHERE ID_VUELO ="+idVuelo;
		PreparedStatement prepStmt= conn.prepareStatement(sql);
		ResultSet rs=prepStmt.executeQuery();
		while(rs.next()){
			int id= rs.getInt("ID");
			int idViajero=rs.getInt("ID_REMITENTE");
			int idCarga = rs.getInt("ID_CARGA");
			String tipoId = rs.getString("TIPO_ID_REMITENTE");
			Reserva reservaP = getReserva(id);

			reserva.add(new ReservaCarga(id, idVuelo, idViajero,tipoId, idCarga,reservaP.getFecha(),Reserva.CARGA,reservaP.getAeroSal(),reservaP.getAeroLleg()));
		}
		return reserva;
	}
	public Reserva createReservaViajero(ReservaViajeros reserva) throws Exception 
	{
		DAOTablaVuelos daoVuelos = new DAOTablaVuelos();
		daoVuelos.setConn(conn);
		DAOTablaAeropuertos daoAeropuertos = new DAOTablaAeropuertos();
		daoAeropuertos.setConn(conn);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
		SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yyyy");
		Reserva resp = null;
		try {
			format2.parse(reserva.getFecha().toString());
		} catch (ParseException ex) {
			String fecha = format2.format(format.parse(reserva.getFecha()));
			reserva.setFecha(fecha);

		}

		String sql2= "Insert into RESERVA(FECHA,TIPO_RESERVA,AERO_SAL,AERO_LLEG) values(TO_DATE('"+ reserva.getFecha()+"','DD/MM/YYYY'),'"+reserva.getTipoReserva()+"','"+reserva.getAeroSal()+"','"+reserva.getAeroLleg()+"')";
		PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
		prepStmt2.executeUpdate();
		ViajeViajeros vuelo = daoVuelos.getVueloViajeros(reserva.getIdVuelo());
		boolean esMultiple = daoVuelos.getVueloViajerosAeropuertos(reserva.getAeroSal(), reserva.getAeroLleg())==null;
		if(!esMultiple & vuelo == null)
		{
			throw new Exception("Se ha escogido un vuelo nulo");
		}
		int idReservaGen = 0;

		if(!esMultiple)
		{
			if(verificarReservaVuelo(reserva, vuelo))
			{
				if(vuelo == null)
				{
					throw new Exception("Se ha escogido un vuelo inexistente");
				}
				if(reserva.getAeroSal().equals(vuelo.getAeropuertoSA().getCodigo()) && reserva.getAeroLleg().equals(vuelo.getAeropuertoLL().getCodigo()))
				{

					ArrayList<Reserva> reservas2 = getReservas();
					idReservaGen = reservas2.get(reservas2.size()-1).getId();
					String sql= "Insert into RESERVA_VIAJEROS(ID_RESERVA_GENERAL,ID_VUELO,ID_VIAJERO, TIPO_ID_VIAJERO,TIPO) values("+idReservaGen+","+reserva.getIdVuelo()+","+reserva.getIdViajero()+",'"+reserva.getTipoIdViajero()+"','"+reserva.getClase()+"')";
					PreparedStatement prepStmt=conn.prepareStatement(sql);
					prepStmt.executeUpdate();
					ArrayList<ReservaViajeros> reservasViajeros = getReservasViajeros();
					int id = reservasViajeros.get(reservasViajeros.size()-1).getId();
					reserva.setIdReservaGeneral(idReservaGen);
					reserva.setId(id);
					resp = reserva;

				}
				else
				{
					throw new Exception("El vuelo seleccionado no hace el viaje desde "+reserva.getAeroSal()+" hasta "+reserva.getAeroLleg()+", intente con otro vuelo .");
				}

			}
		}
		else
		{
			resp = createReservaVuelosMultiples(reserva, daoVuelos);
		}
		return resp;

	}

	public ArrayList<ViajeViajeros> darVuelosReservaMultiple(DAOTablaVuelos daoVuelos, String aeroSal, String aeroLleg) throws Exception
	{


		DAOTablaAeropuertos daoAeropuertos = new DAOTablaAeropuertos();
		daoAeropuertos.setConn(conn);
		Aeropuerto aeroSAL = daoAeropuertos.getAeropuerto(aeroSal);
		GrafoDirigido grafo = new GrafoDirigido(daoAeropuertos.getAeropuertos(),daoVuelos.getVuelosViajeros());
		Dijkstra dijkstra = new Dijkstra(grafo, daoVuelos);
		dijkstra.execute(aeroSAL);
		ArrayList<ViajeViajeros> escalas = dijkstra.getEscalas(aeroSal, aeroLleg);
		if(escalas == null)
		{
			throw new Exception("No hay vuelos que conecten estos dos aeropuertos");
		}
		else
		{
			return escalas;
		}

	}

	public ReservaViajerosMultiple createReservaVuelosMultiples(ReservaViajeros reserva, DAOTablaVuelos daoVuelos) throws Exception
	{

		ArrayList<Reserva> reservas = getReservas();

		int idReserva = reservas.get(reservas.size()-1).getId();
		ArrayList<ViajeViajeros> vuelosReservaMultiple = darVuelosReservaMultiple(daoVuelos, reserva.getAeroSal(), reserva.getAeroLleg());

		ArrayList<ReservaViajeros> resp = new ArrayList<>();
		for(int i = 0; i<vuelosReservaMultiple.size();i++)
		{
			ViajeViajeros vueloActual = vuelosReservaMultiple.get(i);
			if(verificarReservaVuelo(reserva, vueloActual))
			{
				String sql= "Insert into RESERVA_VIAJEROS(ID_RESERVA_GENERAL,ID_VUELO,ID_VIAJERO,TIPO_ID_VIAJERO,TIPO) values("+idReserva+","+vueloActual.getId()+","+reserva.getIdViajero()+",'"+reserva.getTipoIdViajero()+"','"+reserva.getClase()+"')";

				PreparedStatement prepStmt=conn.prepareStatement(sql);
				prepStmt.executeQuery();
				ArrayList<ReservaViajeros> reservasViajeros = getReservasViajeros();
				ReservaViajeros res = reservasViajeros.get(reservasViajeros.size()-1);
				resp.add(res);
			}
		}
		return new ReservaViajerosMultiple(idReserva, reserva.getIdViajero(),reserva.getTipoIdViajero(), reserva.getClase(),reserva.getFecha(),reserva.getTipoReserva(), reserva.getAeroSal(), reserva.getAeroLleg(), resp);

	}

	public boolean verificarReservaVuelo(ReservaViajeros reserva, ViajeViajeros vuelo) throws Exception
	{
		DAOTablaAviones daoAviones = new DAOTablaAviones();
		daoAviones.setConn(conn);
		String idAvion = vuelo.getAvion();
		Avion avion = daoAviones.darAvion(idAvion);
		int puestosEconomica = 0;
		int puestosEjecutiva = 0;
		if(idAvion == null)
		{
			return true;

		}
		else
		{
			ArrayList<ReservaViajeros> reservas = getReservasViajerosPorVuelo(reserva.getIdVuelo());
			if(reserva.getTipoReserva().equals(avion.getTipo()))
			{
				AvionViajeros avionViajero = daoAviones.darAvionViajeros(idAvion);
				for(int i=0;i<reservas.size();i++)
				{
					ReservaViajeros reservaActual = reservas.get(i);
					if(reservaActual.getTipoReserva().equals(ReservaViajeros.ECONOMICA))
					{
						puestosEconomica++;
					}
					else if(reservaActual.getTipoReserva().equals(ReservaViajeros.EJECUTIVA))
					{
						puestosEjecutiva++;

					}
				}


				if(puestosEconomica < avionViajero.getSillasEconomicas() && puestosEjecutiva <avionViajero.getSillasEjecutivas())
				{
					return true;
				}
				else
				{
					throw new Exception("El avión está lleno");
				}
			}
			else
			{
				throw new Exception("El avión con el que se quiere vincular la reserva no es del tipo viajero");
			}



		}
	}

	public void createReservaCarga(ReservaCarga reserva) throws Exception 
	{
		DAOTablaVuelos daoVuelos = new DAOTablaVuelos();
		DAOTablaAviones daoAviones = new DAOTablaAviones();
		DAOTablaCargas daoCargas = new DAOTablaCargas();
		ArrayList<Reserva> reservass = getReservas();
		int idUltimo = reservass.get(reservass.size()-1).getId();
		int idReserva = idUltimo+1;
		String sql= "Insert into RESERVA_CARGA(ID,ID_VUELO,ID_CARGA,ID_REMITENTE,TIPO_ID_REMITENTE) values("+idReserva+","+reserva.getIdVuelo()+","+reserva.getIdCarga()+","+reserva.getIdRemitente()+","+reserva.getTipoIdRemitente()+")";
		String sql2= "Insert into RESERVA(FECHA,TIPO_RESERVA,AERO_SAL,AERO_LLEG) values(TO_DATE('"+ reserva.getFecha()+"','DD/MM/YYYY'),'"+reserva.getTipoReserva()+"','"+reserva.getAeroSal()+"','"+reserva.getAeroLleg()+"')";
		Vuelo vuelo = daoVuelos.getVuelo(reserva.getIdVuelo());
		String idAvion = vuelo.getAvion();
		Avion avion = daoAviones.darAvion(idAvion);
		if(idAvion == null)
		{
			PreparedStatement prepStmt=conn.prepareStatement(sql);
			PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
			prepStmt2.executeUpdate();
			prepStmt.executeUpdate();

		}
		else
		{
			ArrayList<ReservaCarga> reservas = getReservasCargaPorVuelo(reserva.getIdVuelo());
			if(reserva.getTipoReserva().equals(avion.getTipo()))
			{
				AvionCarga avionCarga = daoAviones.darAvionCarga(idAvion);
				int cargaTotal = 0;
				for(int i=0;i<reservas.size();i++)
				{
					ReservaCarga reservaActual = reservas.get(i);
					Carga carga = daoCargas.getCarga(reservaActual.getIdCarga());
					cargaTotal += carga.getPeso();
				}


				if(cargaTotal + daoCargas.getCarga(reserva.getIdCarga()).getPeso() <= avionCarga.getCargaMax())
				{
					PreparedStatement prepStmt=conn.prepareStatement(sql);
					PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
					prepStmt2.executeUpdate();
					prepStmt.executeUpdate();
				}
				else
				{
					throw new Exception("El avión está lleno");
				}
			}
			else
			{
				throw new Exception("El avión con el que se quiere vincular la reserva no es del tipo carga");
			}

		}
	}

	public ProductoReservaGrupalYCarga crearReservaGruposYCargas(CreacionReservaGrupalYCarga reservaGrupal) throws Exception
	{
		DAOTablaViajeros daoViajeros = new DAOTablaViajeros();
		daoViajeros.setConn(conn);
		DAOTablaCargas daoCargas = new DAOTablaCargas();
		daoCargas.setConn(conn);
		ArrayList<Integer> idsViajeros = reservaGrupal.getIdViajeros();
		ArrayList<String> tipoIdsViajeros = reservaGrupal.getTipoIdViajeros();
		Viajeros primerViajero = daoViajeros.getViajero(idsViajeros.get(0), tipoIdsViajeros.get(0));
		ArrayList<ReservaCarga> reservasCargas = new ArrayList<ReservaCarga>();
		for(int i = 0; i<reservaGrupal.getIdCargas().size(); i++)
		{

			int idCarga = reservaGrupal.getIdCargas().get(i);
			ReservaCarga reservaC = new ReservaCarga(0, reservaGrupal.getIdVuelo(), primerViajero.getId(), primerViajero.getTipo(),idCarga, reservaGrupal.getFecha(), reservaGrupal.getTipoReserva(), reservaGrupal.getAeroSal(), reservaGrupal.getAeroLleg());
			createReservaCarga(reservaC);
			reservasCargas.add(reservaC);

		}
		ArrayList<Reserva> reservasViajeros = new ArrayList<Reserva>();
		for(int i = 1; i<idsViajeros.size(); i++)
		{
			int idActual = idsViajeros.get(i);
			String tipoIdActual = tipoIdsViajeros.get(i);
			ReservaViajeros reserva = new ReservaViajeros(0,0, reservaGrupal.getIdVuelo(), idActual, tipoIdActual, reservaGrupal.getClase(), reservaGrupal.getFecha(), reservaGrupal.getTipoReserva(),reservaGrupal.getAeroSal(), reservaGrupal.getAeroLleg());
			Reserva rta = createReservaViajero(reserva);

			reservasViajeros.add(rta);
		}

		return new ProductoReservaGrupalYCarga(reservasViajeros,reservaGrupal.getIdVuelo(),reservaGrupal.getClase(),reservaGrupal.getFecha(),reservaGrupal.getTipoReserva(),reservaGrupal.getAeroSal(),reservaGrupal.getAeroLleg(),reservasCargas, primerViajero);

	}
	public Reserva cancelarReservaViajeroVuelo(int idReserva) throws Exception
	{
		DAOTablaVuelos daoVuelos = new DAOTablaVuelos();
		daoVuelos.setConn(conn);
		Reserva reservaGen = getReserva(idReserva);
		Reserva resp = null;
		ArrayList<ReservaViajeros> reservas = getReservasViajerosPorReservaGeneral(idReserva, daoVuelos);
		if(reservas.size() > 1)
		{
			ArrayList<ReservaViajeros> reservasViajeros = new ArrayList<>();
			ReservaViajeros primRes = reservas.get(0);
			for(int i = 0; i<reservas.size(); i++)
			{
				ReservaViajeros res = cancelarReservaVueloUnico(daoVuelos, reservas.get(i), idReserva);
				reservasViajeros.add(res);
			}
			resp =  new ReservaViajerosMultiple(idReserva , primRes.getIdViajero(),primRes.getTipoIdViajero(), primRes.getClase(),reservaGen.getFecha(),reservaGen.getTipoReserva(), reservaGen.getAeroSal(), reservaGen.getAeroLleg(), reservasViajeros);
		}
		else
		{
			ReservaViajeros reservaViajeros = reservas.get(0);
			resp =  cancelarReservaVueloUnico(daoVuelos, reservaViajeros, idReserva);

		}
		String sql2 ="DELETE ISIS2304A131620.RESERVA WHERE ID ="+idReserva;
		PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
		conn.setSavepoint();
		prepStmt2.executeUpdate();
		return resp;
	}


	public ReservaViajeros cancelarReservaVueloUnico(DAOTablaVuelos daoVuelos, ReservaViajeros reserva, int idReserva) throws Exception
	{
		int id = reserva.getId();
		Vuelo vuelo =daoVuelos.getVuelo(getIdVueloReserva(id));
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR, 24);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(vuelo.getHoraSalida());
		conn.setSavepoint("reserva");
		if( cal.compareTo(cal2) <= 0 )
		{
			String sql = "DELETE ISIS2304A131620.RESERVA_VIAJEROS  WHERE ID = " + id;



			PreparedStatement prepStmt = conn.prepareStatement(sql);
			conn.setSavepoint();
			prepStmt.executeUpdate();

		}
		else
		{
			throw new Exception ("El vuelo es muy pronto para poder cancelar la reserva");
		}
		return reserva;
	}

	public void updateReservaViaje(ReservaViajeros reserva) throws SQLException {
		String sql ="UPDATE ISIS2304A131620.RESERVAS_VIAJEROS SET ID_VIAJERO="+reserva.getIdViajero()+"WHERE ID="+reserva.getId();
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




}
