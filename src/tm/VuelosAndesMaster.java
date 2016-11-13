
package tm;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import dao.DAOTablaAviones;
import dao.DAOTablaReservas;
import dao.DAOTablaVuelos;
import vos.Vuelo;
import vos.VueloViajeros;
import vos.Avion;
import vos.AvionCarga;
import vos.AvionViajeros;
import vos.ConsultaAvion;
import vos.ConsultaViajes;
import vos.CreacionReservaGrupalYCarga;
import vos.ListaVuelos;
import vos.ProductoReservaGrupalYCarga;
import vos.Reserva;
import vos.ReservaCarga;
import vos.ReservaViajeros;
import vos.ReservaViajerosMultiple;
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
			//////Transacci贸n
			this.conn = darConexion();
			conn.setAutoCommit(false);
			conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			daoVuelos.setConn(conn);
			daoVuelos.updateVuelo(video);
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
	 * M茅todo que modela la transacci贸n que elimina el video que entra como par谩metro a la base de datos.
	 * <b> post: </b> se ha eliminado el video que entra como par谩metro
	 * @param video - Video a eliminar. video != null
	 * @throws Exception - cualquier error que se genera actualizando los videos
	 */
	public void deleteVuelo(Vuelo vuelo) throws Exception {
		DAOTablaVuelos daoVuelos = new DAOTablaVuelos();
		try 
		{
			//////Transacci贸n
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

	
	public VueloViajeros deleteViaje(int idVuelo) throws Exception {
		DAOTablaVuelos daoVuelos = new DAOTablaVuelos();
		VueloViajeros viaje = null;
		try 
		{
			//////Transacci贸n
			this.conn = darConexion();
			conn.setAutoCommit(false);
			conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			daoVuelos.setConn(conn);
			viaje = daoVuelos.deleteVueloViajeros(idVuelo);
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

	public void asociarVuelo(int idVuelo, String Avion) throws Exception {
		DAOTablaVuelos daoVuelos= new DAOTablaVuelos();
		try{
			this.conn=darConexion();
			conn.setAutoCommit(false);
			conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			daoVuelos.setConn(conn);
			daoVuelos.asociarVuelo(idVuelo,Avion);
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
			consultas = daoVuelos.consultarViajesViajero(idViajero, tipoIdViajero);
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
				consulta = daoAviones.consultarAvionCarga(avionc);
			}
			else if(avion.getTipo().equals(Avion.VIAJEROS))
			{
				AvionViajeros avionv = daoAviones.darAvionViajeros(numSerie);
				consulta = daoAviones.consultarAvionViajeros(avionv);
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

	public Vuelo darVuelo(int idVuelo) throws Exception {
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
			daoVuelos.asociarVuelo(idVuelo,idAvion);
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



	public double generarReporteVuelo(int idVuelo) throws Exception {
		DAOTablaVuelos daoVuelos= new DAOTablaVuelos();
		double costo = 0;
		try{
			this.conn=darConexion();
			conn.setAutoCommit(false);
			conn.setReadOnly(true);
			daoVuelos.setConn(conn);
			DAOTablaReservas daoReservas = new DAOTablaReservas();
			daoReservas.setConn(conn);
			costo=daoVuelos.generarReporte(idVuelo, daoReservas);
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

	public void registrarVuelo(int idVuelo) throws Exception{
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
			reservaGrupal = daoReservas.crearReservaGruposYCargas(reserva);
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
		Reserva res;
		DAOTablaReservas daoReservas = new DAOTablaReservas();
		try 
		{
			//////Transacci髇
			this.conn = darConexion();
			daoReservas.setConn(conn);
			conn.setAutoCommit(false);
			conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			res = daoReservas.createReservaViajero(reserva);
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
			//////Transacci髇
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
	 * M閠odo que modela la transacci髇 que agrega unA solo reserva a la base de datos.
	 * <b> post: </b> se ha agregado el reserva que entra como par醡etro
	 * @param reserva - el reserva a agregar. reserva != null
	 * @throws Exception - cualquier error que se genera agregando el reserva
	 */
	public void createReservaCarga(ReservaCarga reservaCarga) throws Exception {
		DAOTablaReservas daoReservasCarga = new DAOTablaReservas();
		try 
		{
			//////Transacci髇
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
			vuelos=daoVuelos.getVuelosAeropuerto(idAeropuerto);
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



}
