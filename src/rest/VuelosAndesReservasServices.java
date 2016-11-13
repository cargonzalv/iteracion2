
package rest;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tm.VuelosAndesMaster;
import vos.ConsultaAvion;
import vos.CreacionReservaGrupalYCarga;
import vos.ProductoReservaGrupalYCarga;
import vos.Reserva;
import vos.ReservaCarga;
import vos.ReservaViajeros;
import vos.ReservaViajerosMultiple;
import vos.ReservaViajeros;

@Path("")
public class VuelosAndesReservasServices {

	@Context
	private ServletContext context;

	private String getPath() {
		return context.getRealPath("WEB-INF/ConnectionData");
	}
	
	
	private String doErrorMessage(Exception e){
		return "{ \"ERROR\": \""+ e.getMessage() + "\"}" ;
	}
	
	/**
	 * RF12
	 * @param reservaViajeros
	 * @return
	 */
	@POST
	@Path("/crearReservaViajero")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addReservaViajero(ReservaViajeros reservaViajeros) {
		VuelosAndesMaster tm = new VuelosAndesMaster(getPath());
		Reserva res;
		try {
			res = tm.createReservaVueloViajero(reservaViajeros);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(res).build();
	}

	
	
	@POST
	@Path("/reservasCarga")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addReservaCarga(ReservaCarga reservaCarga) {
		VuelosAndesMaster tm = new VuelosAndesMaster(getPath());
		try {
			tm.createReservaCarga(reservaCarga);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(reservaCarga).build();
	}
	
	/**
	 * RF16 Registrar reserva para un grupo de viajeros.
	 * @param reserva
	 * @return
	 */
	@POST
	@Path("/reservaGrupalYCarga")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createReservaGrupalYCarga(CreacionReservaGrupalYCarga reserva) {
		VuelosAndesMaster tm = new VuelosAndesMaster(getPath());
		ProductoReservaGrupalYCarga r = null;
		try {
			r = tm.crearReservaGruposYCargas(reserva);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(r).build();
	}
	
	/**
	 * RFC4 Consultar aeronave.
	 * @param idAvion
	 * @return
	 */
	@GET
	@Path("/consultaAeronave/{idAvion}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response consultarAvion(String idAvion)
	{
		VuelosAndesMaster tm = new VuelosAndesMaster(getPath());
		ConsultaAvion consulta = null;
		try {
			consulta = tm.consultarAvion(idAvion);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(consulta).build();
	}

	
	
	/**
	 * RF13
	 * @param idReserva
	 * @return
	 */
	@DELETE
	@Path("/reservaViajero/{idReserva}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteReservaPasajero(@javax.ws.rs.PathParam("idReserva") int idReserva) {
		VuelosAndesMaster tm = new VuelosAndesMaster(getPath());
		Reserva reserva = null;
		try {
			reserva = tm.cancelarReservaVueloViajero(idReserva);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(reserva).build();
	}
	
	

	
	@PUT
	@Path("reservas/reserva/{idReserva}/asosiarCarga/carga/{idCarga}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response asociarCarga(@javax.ws.rs.PathParam("idReserva") int idReserva, @javax.ws.rs.PathParam("idCarga") int idCarga){
		VuelosAndesMaster tm= new VuelosAndesMaster(getPath());
		 ReservaCarga reserva=null;
		try{
			tm.asociarCarga(idReserva,idCarga);
			reserva=tm.darReservaCarga(idReserva);
		}
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(reserva).build();
	}
	
}
