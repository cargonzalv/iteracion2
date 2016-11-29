
package tm;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import dao.DAOTablaAviones;
import dao.DAOTablaReservas;
import dao.DAOTablaVuelos;
import vos.Vuelo;
import vos.ViajeViajeros;
import vos.Avion;
import vos.AvionCarga;
import vos.AvionViajeros;
import vos.ConsultaAvion;
import vos.ConsultaAvionViajeros;
import vos.ConsultaTraficoAereo;
import vos.ConsultaViajes;
import vos.CreacionReservaGrupalYCarga;
import vos.ListaVuelos;
import vos.ProductoReservaGrupalYCarga;
import vos.Reserva;
import vos.ReservaCarga;
import vos.ReservaViajeros;
import vos.ReservaViajerosMultiple;
import vos.Viaje;
import vos.ReservaViajeros;


public class VuelosAndesMaster {



	private static final String CONNECTION_DATA_FILE_NAME_REMOTE = "/conexion.properties";


	private  String connectionDataPath;


	private String user;


	private String password;

	private String url;


	private String driver;


	private Connection conn;



	public VuelosAndesMaster(String contextPathP) {
		connectionDataPath = contextPathP + CONNECTION_DATA_FILE_NAME_REMOTE;
		initConnectionData();
	}


	private void initConnectionData() {
		try {
			File arch = new File(this.connectionDataPath);
			Properties prop = new Properties();
			FileInputStream in = new FileInputStream(arch);
			prop.load(in);
			in.close();
			this.url = prop.getProperty("url");
			this.user = prop.getProperty("usuario");
			this.password = prop.getProperty("clave");
			this.driver = prop.getProperty("driver");
			Class.forName(driver);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	private Connection darConexion() throws SQLException {
		System.out.println("Connecting to: " + url + " With user: " + user);
		return DriverManager.getConnection(url, user, password);
	}

	////////////////////////////////////////
	///////Transacciones////////////////////
	////////////////////////////////////////








	public void updateVuelo(Vuelo video) throws Exception {
		DAOTablaVuelos daoVuelos = new DAOTablaVuelos();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			conn.setAutoCommit(false);
			conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			daoVuelos.setConn(conn);
			//daoVuelos.updateVuelo(video);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoVuelos.cerrarVuelos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * Método que modela la transacción que elimina el video que entra como parámetro a la base de datos.
	 * <b> post: </b> se ha eliminado el video que entra como parámetro
	 * @param video - Video a eliminar. video != null
	 * @throws Exception - cualquier error que se genera actualizando los videos
	 */
	public void deleteVuelo(Vuelo vuelo) throws Exception {
		DAOTablaVuelos daoVuelos = new DAOTablaVuelos();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			conn.setAutoCommit(false);
			conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			daoVuelos.setConn(conn);
			daoVuelos.deleteVuelo(vuelo);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} 
		finally 
		{
			try 
			{
				daoVuelos.cerrarVuelos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}


	public ViajeViajeros deleteViaje(String idVuelo) throws Exception {
		DAOTablaVuelos daoVuelos = new DAOTablaVuelos();
		ViajeViajeros viaje = null;
		try 
		{
			//////Transacción
			this.conn = darConexion();
			conn.setAutoCommit(false);
			conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			daoVuelos.setConn(conn);
//			viaje = daoVuelos.deleteVueloViajeros(idVuelo);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();

			throw e;
		} 
		finally 
		{
			try 
			{
				daoVuelos.cerrarVuelos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return viaje;
	}

	public void asociarVuelo(String idVuelo, String Avion) throws Exception {
		DAOTablaVuelos daoVuelos= new DAOTablaVuelos();
		try{
			this.conn=darConexion();
			conn.setAutoCommit(false);
			conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			daoVuelos.setConn(conn);
			//daoVuelos.asociarVuelo(idVuelo,Avion);
			conn.commit();
		}
		catch (SQLException e) {
			System.err.println("generalException:"+e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		}
		catch (Exception e) {
			System.err.println("generalException:"+e.getMessage());
			e.printStackTrace();
			throw e;

		}
		finally {
			try{
				daoVuelos.cerrarVuelos();
				if(this.conn!=null){
					this.conn.close();
				}
			}catch (SQLException e2) {
				System.err.println("generalException:"+e2.getMessage());
				e2.printStackTrace();
				throw e2;
			}
		}

	}


	public ArrayList<ConsultaViajes> consultarViajesViajero(int idViajero, String tipoIdViajero)
	{			
		DAOTablaVuelos daoVuelos= null;
		ArrayList<ConsultaViajes> consultas= null;


		try{
			daoVuelos= new DAOTablaVuelos();
			this.conn=darConexion();
			conn.setAutoCommit(false);
			conn.setReadOnly(true);
			daoVuelos.setConn(conn);
			//consultas = daoVuelos.consultarViajesViajero(idViajero, tipoIdViajero);
		}
		catch (SQLException e) {
			System.err.println("generalException:"+e.getMessage());
			e.printStackTrace();

			try {
				conn.rollback();
				throw e;
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		catch (Exception e) {
			System.err.println("generalException:"+e.getMessage());
			e.printStackTrace();
			try {
				conn.rollback();
				throw e;
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
		finally {
			try{
				daoVuelos.cerrarVuelos();
				if(this.conn!=null){
					this.conn.close();
				}
			}catch (SQLException e2) {
				System.err.println("generalException:"+e2.getMessage());
				e2.printStackTrace();
				try {
					throw e2;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return consultas;
	}

	public ConsultaAvion consultarAvion(String numSerie)
	{			
		DAOTablaAviones daoAviones =  null;
		DAOTablaVuelos daoVuelos = null;
		ConsultaAvion consulta= null;


		try{
			daoVuelos = new DAOTablaVuelos();
			daoAviones = new DAOTablaAviones();
			this.conn=darConexion();
			conn.setAutoCommit(false);
			conn.setReadOnly(true);
			daoAviones.setConn(conn);
			daoVuelos.setConn(conn);
			Avion avion = daoAviones.darAvion(numSerie);
			if(avion.getTipo().equals(Avion.CARGA))
			{
				AvionCarga avionc = daoAviones.darAvionCarga(numSerie);
//				consulta = daoAviones.consultarAvionCarga(avionc);
			}
			else if(avion.getTipo().equals(Avion.VIAJEROS))
			{
				AvionViajeros avionv = daoAviones.darAvionViajeros(numSerie);
//				consulta = daoAviones.consultarAvionViajeros(avionv);
			}
		}
		catch (SQLException e) {
			System.err.println("generalException:"+e.getMessage());
			e.printStackTrace();

			try {
				throw e;
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		catch (Exception e) {
			System.err.println("generalException:"+e.getMessage());
			e.printStackTrace();
			try {
				throw e;
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
		finally {
			try{
				daoVuelos.cerrarVuelos();
				if(this.conn!=null){
					this.conn.close();
				}
			}catch (SQLException e2) {
				System.err.println("generalException:"+e2.getMessage());
				e2.printStackTrace();
				try {
					throw e2;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return consulta;
	}


	public ListaVuelos darVuelos() throws Exception {
		DAOTablaVuelos daoVuelos= new DAOTablaVuelos();
		ArrayList<Vuelo> vuelos= null;
		try{
			this.conn=darConexion();
			conn.setAutoCommit(false);
			conn.setReadOnly(true);
			daoVuelos.setConn(conn);
			vuelos=daoVuelos.darVuelos();
		}
		catch (SQLException e) {
			System.err.println("generalException:"+e.getMessage());
			e.printStackTrace();
			throw e;
		}
		catch (Exception e) {
			System.err.println("generalException:"+e.getMessage());
			e.printStackTrace();
			throw e;

		}
		finally {
			try{
				daoVuelos.cerrarVuelos();
				if(this.conn!=null){
					this.conn.close();
				}
			}catch (SQLException e2) {
				System.err.println("generalException:"+e2.getMessage());
				e2.printStackTrace();
				throw e2;
			}
		}
		return new ListaVuelos(vuelos);
	}

	public Vuelo darVuelo(String idVuelo) throws Exception {
		DAOTablaVuelos daoVuelos= new DAOTablaVuelos();
		Vuelo vuelo= null;
		try{
			this.conn=darConexion();
			conn.setAutoCommit(false);
			conn.setReadOnly(true);
			daoVuelos.setConn(conn);
			vuelo=daoVuelos.getVuelo(idVuelo);
		}
		catch (SQLException e) {
			System.err.println("generalException:"+e.getMessage());
			e.printStackTrace();
			throw e;
		}
		catch (Exception e) {
			System.err.println("generalException:"+e.getMessage());
			e.printStackTrace();
			throw e;

		}
		finally {
			try{
				daoVuelos.cerrarVuelos();
				if(this.conn!=null){
					this.conn.close();
				}
			}catch (SQLException e2) {
				System.err.println("generalException:"+e2.getMessage());
				e2.printStackTrace();
				throw e2;
			}
		}
		return vuelo;
	}

	public void asociarAvionVuelo(int idVuelo, String idAvion) throws Exception{
		DAOTablaVuelos daoVuelos= new DAOTablaVuelos();
		try{
			this.conn=darConexion();
			conn.setAutoCommit(false);
			conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			daoVuelos.setConn(conn);
			//daoVuelos.asociarVuelo(idVuelo,idAvion);
			conn.commit();
		}
		catch (SQLException e) {
			System.err.println("generalException:"+e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		}
		catch (Exception e) {
			System.err.println("generalException:"+e.getMessage());
			e.printStackTrace();
			throw e;

		}
		finally {
			try{
				daoVuelos.cerrarVuelos();
				if(this.conn!=null){
					this.conn.close();
				}
			}catch (SQLException e2) {
				System.err.println("generalException:"+e2.getMessage());
				e2.printStackTrace();
				throw e2;
			}
		}

	}



	public double generarReporteVuelo(String idVuelo,Date horaSalida) throws Exception {
		DAOTablaVuelos daoVuelos= new DAOTablaVuelos();
		double costo = 0;
		try{
			this.conn=darConexion();
			conn.setAutoCommit(false);
			conn.setReadOnly(true);
			daoVuelos.setConn(conn);
			DAOTablaReservas daoReservas = new DAOTablaReservas();
			daoReservas.setConn(conn);
			costo=daoVuelos.generarReporte(idVuelo,horaSalida, daoReservas);
		}
		catch (SQLException e) {
			System.err.println("generalException:"+e.getMessage());
			e.printStackTrace();
			throw e;
		}
		catch (Exception e) {
			System.err.println("generalException:"+e.getMessage());
			e.printStackTrace();
			throw e;

		}
		finally {
			try{
				daoVuelos.cerrarVuelos();
				if(this.conn!=null){
					this.conn.close();
				}
			}catch (SQLException e2) {
				System.err.println("generalException:"+e2.getMessage());
				e2.printStackTrace();
				throw e2;
			}
		}
		return costo;
	}

	public ReservaViajeros darReservaViajeros(int idReserva) throws Exception {
		DAOTablaReservas daoReservas= new DAOTablaReservas();
		ReservaViajeros reserva= null;
		try{
			this.conn=darConexion();
			conn.setAutoCommit(false);
			conn.setReadOnly(true);
			daoReservas.setConn(conn);
			reserva=daoReservas.getReservaViajeros(idReserva);
		}
		catch (SQLException e) {
			System.err.println("generalException:"+e.getMessage());
			e.printStackTrace();
			throw e;
		}
		catch (Exception e) {
			System.err.println("generalException:"+e.getMessage());
			e.printStackTrace();
			throw e;

		}
		finally {
			try{
				daoReservas.cerrarReservas();
				if(this.conn!=null){
					this.conn.close();
				}
			}catch (SQLException e2) {
				System.err.println("generalException:"+e2.getMessage());
				e2.printStackTrace();
				throw e2;
			}
		}
		return reserva;
	}

	public void registrarVuelo(String idVuelo) throws Exception{
		DAOTablaVuelos daoVuelos= new DAOTablaVuelos();
		try{
			this.conn=darConexion();
			conn.setAutoCommit(false);
			conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			daoVuelos.setConn(conn);
			daoVuelos.registrarVuelo(idVuelo);
			conn.commit();
		}
		catch (SQLException e) {
			System.err.println("generalException:"+e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		}
		catch (Exception e) {
			System.err.println("generalException:"+e.getMessage());
			e.printStackTrace();
			throw e;

		}
		finally {
			try{
				daoVuelos.cerrarVuelos();
				if(this.conn!=null){
					this.conn.close();
				}
			}catch (SQLException e2) {
				System.err.println("generalException:"+e2.getMessage());
				e2.printStackTrace();
				throw e2;
			}
		}
	}

	public void asociarCarga(int idReserva, int idCarga) throws Exception {
		DAOTablaReservas daoReserva= new DAOTablaReservas();
		try{
			this.conn=darConexion();
			conn.setAutoCommit(false);
			conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			daoReserva.setConn(conn);
			daoReserva.asociarCarga(idReserva,idCarga);
			conn.commit();
		}
		catch (SQLException e) {
			System.err.println("generalException:"+e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		}
		catch (Exception e) {
			System.err.println("generalException:"+e.getMessage());
			e.printStackTrace();
			throw e;

		}
		finally {
			try{
				daoReserva.cerrarReservas();
				if(this.conn!=null){
					this.conn.close();
				}
			}catch (SQLException e2) {
				System.err.println("generalException:"+e2.getMessage());
				e2.printStackTrace();
				throw e2;
			}
		}

	}



	public ReservaCarga darReservaCarga(int idReserva) throws Exception {
		DAOTablaReservas daoReservas= new DAOTablaReservas();
		ReservaCarga reserva= null;
		try{
			this.conn=darConexion();
			conn.setAutoCommit(false);
			conn.setReadOnly(true);
			daoReservas.setConn(conn);
			reserva=daoReservas.getReservaCarga(idReserva);
		}
		catch (SQLException e) {
			System.err.println("generalException:"+e.getMessage());
			e.printStackTrace();
			throw e;
		}
		catch (Exception e) {
			System.err.println("generalException:"+e.getMessage());
			e.printStackTrace();
			throw e;

		}
		finally {
			try{
				daoReservas.cerrarReservas();
				if(this.conn!=null){
					this.conn.close();
				}
			}catch (SQLException e2) {
				System.err.println("generalException:"+e2.getMessage());
				e2.printStackTrace();
				throw e2;
			}
		}
		return reserva;
	}

	public ProductoReservaGrupalYCarga crearReservaGruposYCargas(CreacionReservaGrupalYCarga reserva) throws Exception
	{
		DAOTablaReservas daoReservas= new DAOTablaReservas();
		ProductoReservaGrupalYCarga reservaGrupal = null;
		try{
			this.conn=darConexion();
			daoReservas.setConn(conn);
			conn.setAutoCommit(false);
			conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
//			reservaGrupal = daoReservas.crearReservaGruposYCargas(reserva);
			conn.commit();
		}
		catch (SQLException e) {
			System.err.println("generalException:"+e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		}
		catch (Exception e) {
			System.err.println("generalException:"+e.getMessage());
			e.printStackTrace();
			throw e;

		}
		finally {
			try{
				daoReservas.cerrarReservas();
				if(this.conn!=null){
					this.conn.close();
				}
			}catch (SQLException e2) {
				System.err.println("generalException:"+e2.getMessage());
				e2.printStackTrace();
				throw e2;
			}
		}
		return reservaGrupal;
	}





	public ArrayList<Vuelo> darVueloMasPopulares() throws Exception {
		DAOTablaVuelos daoVuelos= new DAOTablaVuelos();
		ArrayList<Vuelo> vuelos= null;
		try{
			this.conn=darConexion();
			daoVuelos.setConn(conn);
			conn.setAutoCommit(false);
			conn.setReadOnly(true);
			vuelos=daoVuelos.getVuelosMasPopulares();

		}
		catch (SQLException e) {
			System.err.println("generalException:"+e.getMessage());
			e.printStackTrace();
			throw e;
		}
		catch (Exception e) {
			System.err.println("generalException:"+e.getMessage());
			e.printStackTrace();
			throw e;

		}
		finally {
			try{
				daoVuelos.cerrarVuelos();
				if(this.conn!=null){
					this.conn.close();
				}
			}catch (SQLException e2) {
				System.err.println("generalException:"+e2.getMessage());
				e2.printStackTrace();
				throw e2;
			}
		}
		return vuelos;
	}

	public Reserva createReservaVueloViajero(ReservaViajeros reserva) throws Exception {
		Reserva res = null;
		DAOTablaReservas daoReservas = new DAOTablaReservas();
		try 
		{
			//////Transacci�n
			this.conn = darConexion();
			daoReservas.setConn(conn);
			conn.setAutoCommit(false);
			conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
//			res = daoReservas.createReservaViajero(reserva);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoReservas.cerrarReservas();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return res;
	}

	public Reserva cancelarReservaVueloViajero(int idReserva) throws Exception {
		DAOTablaReservas daoReservas = new DAOTablaReservas();
		Reserva r  = null;
		try 
		{
			//////Transacci�n
			this.conn = darConexion();
			daoReservas.setConn(conn);
			conn.setAutoCommit(false);
			conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			conn.setSavepoint();
			r = daoReservas.cancelarReservaViajeroVuelo(idReserva);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoReservas.cerrarReservas();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return r;
	}






	/**
	 * M�todo que modela la transacci�n que agrega unA solo reserva a la base de datos.
	 * <b> post: </b> se ha agregado el reserva que entra como par�metro
	 * @param reserva - el reserva a agregar. reserva != null
	 * @throws Exception - cualquier error que se genera agregando el reserva
	 */
	public void createReservaCarga(ReservaCarga reservaCarga) throws Exception {
		DAOTablaReservas daoReservasCarga = new DAOTablaReservas();
		try 
		{
			//////Transacci�n
			this.conn = darConexion();
			daoReservasCarga.setConn(conn);
			conn.setAutoCommit(false);
			conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			conn.setSavepoint("savepoint 0%");
			daoReservasCarga.createReservaCarga(reservaCarga);
			conn.commit();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			conn.rollback();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoReservasCarga.cerrarReservas();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}


	public ArrayList<Vuelo> darVuelosAeropuerto(String idAeropuerto)throws Exception {
		DAOTablaVuelos daoVuelos= new DAOTablaVuelos();
		ArrayList<Vuelo> vuelos= null;
		try{
			this.conn=darConexion();
			conn.setAutoCommit(false);
			conn.setReadOnly(true);
			daoVuelos.setConn(conn);
			//vuelos=daoVuelos.getVuelosAeropuerto(idAeropuerto);
		}
		catch (SQLException e) {
			System.err.println("generalException:"+e.getMessage());
			e.printStackTrace();
			throw e;
		}
		catch (Exception e) {
			System.err.println("generalException:"+e.getMessage());
			e.printStackTrace();
			throw e;

		}
		finally {
			try{
				daoVuelos.cerrarVuelos();
				if(this.conn!=null){
					this.conn.close();
				}
			}catch (SQLException e2) {
				System.err.println("generalException:"+e2.getMessage());
				e2.printStackTrace();
				throw e2;
			}
		}
		return vuelos;	}

	public ArrayList<Viaje> darVueloAeropuertoParametroOrganizado(String aeropuerto, String tipoParametro,String parametro, String organizacion) throws Exception {
		DAOTablaVuelos daoVuelos= new DAOTablaVuelos();
		ArrayList<Viaje> vuelos= null;
		try{
			this.conn=darConexion();
			daoVuelos.setConn(conn);
			conn.setAutoCommit(false);
			conn.setReadOnly(true);
			if(tipoParametro.equals("Aeronave")){
				vuelos=daoVuelos.getViajesAeropuertoAeronaveOrganizado(aeropuerto,parametro,organizacion);
			}
			else if(tipoParametro.equals("Aerolinea")){
				vuelos=daoVuelos.getViajesAeropuertoAerolineaOrganizado(aeropuerto, parametro, organizacion);
			}
			else if(tipoParametro.equals("Rango Fechas")){
				SimpleDateFormat spld=new SimpleDateFormat("dd/M/yy");
				String[] fechas= parametro.split(";");
				Date fechaMin=spld.parse(fechas[0]);
				Date fechaMax=spld.parse(fechas[1]);
				vuelos=daoVuelos.getViajesAeropuertoRangoFechasOrganizado(aeropuerto, fechaMin, fechaMax, organizacion);

			}
			else if(tipoParametro.equals("Fechas Salida LLegada")){
				SimpleDateFormat spld=new SimpleDateFormat("dd/M/yy");
				String[] fechas= parametro.split(";");
				Date fechaMin=spld.parse(fechas[0]);
				Date fechaMax=spld.parse(fechas[1]);
				vuelos=daoVuelos.getViajesAeropuertoHoraSalidaLlegadaOrganizado(aeropuerto, fechaMin, fechaMax, organizacion);

			}


		}
		catch (SQLException e) {
			System.err.println("generalException:"+e.getMessage());
			e.printStackTrace();
			throw e;
		}
		catch (Exception e) {
			System.err.println("generalException:"+e.getMessage());
			e.printStackTrace();
			throw e;

		}
		finally {
			try{
				daoVuelos.cerrarVuelos();
				if(this.conn!=null){
					this.conn.close();
				}
			}catch (SQLException e2) {
				System.err.println("generalException:"+e2.getMessage());
				e2.printStackTrace();
				throw e2;
			}
		}
		return vuelos;
	}

	public ArrayList<Viaje> darVuelosAeropuertoNoParametros(String idAeropuerto, int dia1, int mes1,int anio1, int dia2,int mes2, int anio2,  String aerolinea, String aeronave, String horaSal, String horaLleg)throws Exception {
		DAOTablaVuelos daoVuelos= new DAOTablaVuelos();
		ArrayList<Viaje> viajes= null;
		try{
			this.conn=darConexion();
			conn.setAutoCommit(false);
			conn.setReadOnly(true);
			daoVuelos.setConn(conn);
			viajes=daoVuelos.getViajesAeropuertoNoParametro(idAeropuerto, dia1,mes1,anio1,dia2,mes2,anio2, aerolinea, aeronave, horaSal, horaLleg);
		}
		catch (SQLException e) {
			System.err.println("generalException:"+e.getMessage());
			e.printStackTrace();
			throw e;
		}
		catch (Exception e) {
			System.err.println("generalException:"+e.getMessage());
			e.printStackTrace();
			throw e;

		}
		finally {
			try{
				daoVuelos.cerrarVuelos();
				if(this.conn!=null){
					this.conn.close();
				}
			}catch (SQLException e2) {
				System.err.println("generalException:"+e2.getMessage());
				e2.printStackTrace();
				throw e2;
			}
		}
		return viajes;	}
	
	public ArrayList<ConsultaViajes> consultarViajesViajeroParametros(String clase, double millas)
	{			
		DAOTablaVuelos daoVuelos= null;
		ArrayList<ConsultaViajes> consultas= null;

		
		try{
			daoVuelos= new DAOTablaVuelos();
			this.conn=darConexion();
			conn.setAutoCommit(false);
			conn.setReadOnly(true);
			daoVuelos.setConn(conn);
			consultas = daoVuelos.consultarViajesViajeroParametros( clase, millas);
		}
		catch (SQLException e) {
			System.err.println("generalException:"+e.getMessage());
			e.printStackTrace();
			
			try {
				conn.rollback();
				throw e;
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		catch (Exception e) {
			System.err.println("generalException:"+e.getMessage());
			e.printStackTrace();
			try {
				conn.rollback();
				throw e;
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
		finally {
			try{
				daoVuelos.cerrarVuelos();
				if(this.conn!=null){
					this.conn.close();
				}
			}catch (SQLException e2) {
				System.err.println("generalException:"+e2.getMessage());
				e2.printStackTrace();
				try {
					throw e2;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return consultas;
	}


	public ConsultaTraficoAereo consultarTraficoAereoCiudades(String ciudad1, String ciudad2, int dia1, String mes1,
			int anio1, int dia2, String mes2, int anio2) {
		DAOTablaVuelos daoVuelos= new DAOTablaVuelos();
		ConsultaTraficoAereo consulta = null;
		try{
			this.conn=darConexion();
			conn.setAutoCommit(false);
			conn.setReadOnly(true);
			daoVuelos.setConn(conn);
			consulta=daoVuelos.getTraficoAereoCiudades(ciudad1,ciudad2,dia1,mes1,anio1,dia2,mes2,anio2);
		}
		catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.err.println("generalException:"+e.getMessage());
			e.printStackTrace();
			try {
				throw e;
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.err.println("generalException:"+e.getMessage());
			e.printStackTrace();
			try {
				throw e;
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
		finally {
			try{
				daoVuelos.cerrarVuelos();
				if(this.conn!=null){
					this.conn.close();
				}
			}catch (SQLException e2) {
				System.err.println("generalException:"+e2.getMessage());
				e2.printStackTrace();
				try {
					throw e2;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return consulta;	}

}




