package rest;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tm.VuelosAndesMaster;
import vos.ReservaCarga;
import vos.ReservaViaje;

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
	
	@PUT
	@Path("reservas/reserva/{idReserva}/asosiarUsuario/usuario/{idUsuario}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	private Response asociarViajero(@javax.ws.rs.PathParam("idReserva") int idReserva, @javax.ws.rs.PathParam("idViajero") int idViajero) {
		VuelosAndesMaster tm= new VuelosAndesMaster(getPath());
		ReservaViaje reserva=null;
		try{
			tm.asociarViajero(idReserva,idViajero);
			reserva=tm.darReservaViaje(idReserva);
		}
		catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(reserva).build();
	}
	@PUT
	@Path("reservas/reserva/{idReserva}/asosiarCarga/carga/{idCarga}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	private Response asociarCarga(@javax.ws.rs.PathParam("idReserva") int idReserva, @javax.ws.rs.PathParam("idCarga") int idCarga){
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
