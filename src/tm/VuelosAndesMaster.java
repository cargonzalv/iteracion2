
package tm;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import dao.DAOTablaReservas;
import dao.DAOTablaVuelos;
import vos.Vuelo;
import vos.ListaVuelos;
import vos.ReservaCarga;
import vos.ReservaViaje;


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


	public ListaVuelos darVuelos() throws Exception {
		ArrayList<Vuelo> vuelos;
		DAOTablaVuelos daoVuelos = new DAOTablaVuelos();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoVuelos.setConn(conn);
			vuelos = daoVuelos.darVuelos();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
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
		return new ListaVuelos(vuelos);
	}

	
	
	public void updateVuelo(Vuelo video) throws Exception {
		DAOTablaVuelos daoVuelos = new DAOTablaVuelos();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoVuelos.setConn(conn);
			daoVuelos.updateVuelo(video);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
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
			daoVuelos.setConn(conn);
			daoVuelos.deleteVuelo(vuelo);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
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

	public void asociarVuelo(int idVuelo, String Avion) throws Exception {
		DAOTablaVuelos daoVuelos= new DAOTablaVuelos();
		try{
			this.conn=darConexion();
			daoVuelos.setConn(conn);
			daoVuelos.asociarVuelo(idVuelo,Avion);
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
		
	}

	public Vuelo darVuelo(int idVuelo) throws Exception {
		DAOTablaVuelos daoVuelos= new DAOTablaVuelos();
		Vuelo vuelo= null;
		try{
			this.conn=darConexion();
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

	public void asociarViajero(int idReserva, int idViajero) throws Exception{
		DAOTablaReservas daoReserva= new DAOTablaReservas();
		try{
			this.conn=darConexion();
			daoReserva.setConn(conn);
			daoReserva.asociarViajero(idReserva,idViajero);
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

	public ReservaViaje darReservaViaje(int idReserva) throws Exception {
		DAOTablaReservas daoReservas= new DAOTablaReservas();
		ReservaViaje reserva= null;
		try{
			this.conn=darConexion();
			daoReservas.setConn(conn);
			reserva=daoReservas.getReservaViaje(idReserva);
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


	public void asociarCarga(int idReserva, int idCarga) throws Exception {
		DAOTablaReservas daoReserva= new DAOTablaReservas();
		try{
			this.conn=darConexion();
			daoReserva.setConn(conn);
			daoReserva.asociarCarga(idReserva,idCarga);
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


	public ArrayList<Vuelo> darVueloMasPopulares() throws Exception {
		DAOTablaVuelos daoVuelos= new DAOTablaVuelos();
		ArrayList<Vuelo> vuelos= null;
		try{
			this.conn=darConexion();
			daoVuelos.setConn(conn);
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

	
	
}
