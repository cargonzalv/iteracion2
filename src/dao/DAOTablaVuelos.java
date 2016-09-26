/**-------------------------------------------------------------------
 * $Id$
 * Universidad de los Andes (Bogotá - Colombia)
 * Departamento de Ingeniería de Sistemas y Computación
 *
 * Materia: Sistemas Transaccionales
 * Ejercicio: VideoAndes
 * Autor: Juan Felipe García - jf.garcia268@uniandes.edu.co
 * -------------------------------------------------------------------
 */
package dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Vuelo;



/**
 * Clase DAO que se conecta la base de datos usando JDBC para resolver los requerimientos de la aplicación
 * @author Juan
 */
public class DAOTablaVuelos {


	/**
	 * Arraylits de recursos que se usan para la ejecución de sentencias SQL
	 */
	private ArrayList<Object> vuelos;

	/**
	 * Atributo que genera la conexión a la base de datos
	 */
	private Connection conn;

	/**
	 * Método constructor que crea DAOVideo
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOTablaVuelos() {
		vuelos = new ArrayList<Object>();
	}

	/**
	 * Método que cierra todos los recursos que estan enel arreglo de recursos
	 * <b>post: </b> Todos los recurso del arreglo de recursos han sido cerrados
	 */
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

	/**
	 * Método que inicializa la connection del DAO a la base de datos con la conexión que entra como parámetro.
	 * @param con  - connection a la base de datos
	 */
	public void setConn(Connection con){
		this.conn = con;
	}


	/**
	 * Método que, usando la conexión a la base de datos, saca todos los videos de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM VIDEOS;
	 * @return Arraylist con los videos de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Vuelo> darVuelos() throws SQLException, Exception {
		ArrayList<Vuelo> vuelos = new ArrayList<Vuelo>();

		String sql = "SELECT * FROM ISIS2304A131620.VUELOS";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String name = rs.getString("NAME");
			int id = Integer.parseInt(rs.getString("ID"));
			int duration = Integer.parseInt(rs.getString("DURATION"));
			vuelos.add(new Vuelo(id, name, duration));
		}
		return vuelos;
	}


	/**
	 * Método que busca el/los videos con el nombre que entra como parámetro.
	 * @param name - Nombre de el/los videos a buscar
	 * @return ArrayList con los videos encontrados
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Vuelo> buscarVuelosPorAerolinea(String name) throws SQLException, Exception {
		ArrayList<Vuelo> vuelos = new ArrayList<Vuelo>();

		String sql = "SELECT * FROM ISIS2304A131620.VUELOS WHERE NAME ='" + name + "'";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String name2 = rs.getString("NAME");
			int id = Integer.parseInt(rs.getString("ID"));
			int duration = Integer.parseInt(rs.getString("DURATION"));
			vuelos.add(new Vuelo(id, name2, duration));
		}

		return vuelos;
	}

	/**
	 * Método que agrega el video que entra como parámetro a la base de datos.
	 * @param video - el video a agregar. video !=  null
	 * <b> post: </b> se ha agregado el video a la base de datos en la transaction actual. pendiente que el video master
	 * haga commit para que el video baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el video a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addVuelo(Vuelo vuelo) throws SQLException, Exception {

		String sql = "INSERT INTO ISIS2304A131620.VUELOS VALUES (";
		sql += vuelo.getId() + ",'";
		sql += vuelo.getName() + "',";
		sql += vuelo.getDuration() + ")";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		
		prepStmt.executeQuery();

	}
	
	/**
	 * Método que actualiza el video que entra como parámetro en la base de datos.
	 * @param video - el video a actualizar. video !=  null
	 * <b> post: </b> se ha actualizado el video en la base de datos en la transaction actual. pendiente que el video master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el video.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void updateVuelo(Vuelo vuelo) throws SQLException, Exception {

		String sql = "UPDATE ISIS2304A131620.VUELOS SET ";
		sql += "name='" + vuelo.getName() + "',";
		sql += "duration=" + vuelo.getDuration();
		sql += " WHERE id = " + vuelo.getId();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		
		prepStmt.executeQuery();
	}

	/**
	 * Método que elimina el video que entra como parámetro en la base de datos.
	 * @param video - el video a borrar. video !=  null
	 * <b> post: </b> se ha borrado el video en la base de datos en la transaction actual. pendiente que el video master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el video.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void deleteVuelo(Vuelo vuelo) throws SQLException, Exception {

		String sql = "DELETE FROM ISIS2304A131620.VUELOS";
		sql += " WHERE id = " + vuelo.getId();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		
		prepStmt.executeQuery();
	}

	
	

}
