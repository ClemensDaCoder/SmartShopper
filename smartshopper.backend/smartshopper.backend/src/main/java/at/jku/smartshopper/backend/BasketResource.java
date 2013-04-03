package at.jku.smartshopper.backend;

import javax.ejb.Stateless;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@ApplicationPath("/")
@Path("/basket")
@Stateless
public class BasketResource {
	
	@GET
	@Path("/{username}/{basket}/")
	@Produces(MediaType.APPLICATION_JSON)
	public Basket getBasket(@PathParam("username") String username, @PathParam("basket") long timeStamp) {
		//TODO
		return null;
	}
	
	@PUT
	@Path("/{username}/{basket}/")
	@Consumes(MediaType.APPLICATION_JSON)
	public void putBasket(@PathParam("username") String username, @PathParam("basket") long timeStamp, Basket basket) {
		//TODO
	}
	
}