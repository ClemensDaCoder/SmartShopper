package at.jku.smartshopper.backend;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import at.jku.smartshopper.backend.util.WebserviceEntityManager;
import at.jku.smartshopper.persistence.BasketEntity;
import at.jku.smartshopper.persistence.BasketToArticleEntity;
import at.jku.smartshopper.persistence.UserEntity;

@ApplicationPath("/")
@Path("/basket")
@Stateless
public class BasketResource {

	@Inject
	private EntityManager entityManager;

	@GET
	@Path("/{username}/{basket}/")
	@Produces(MediaType.APPLICATION_JSON)
	public Basket getBasket(@PathParam("username") String username,	@PathParam("basket") long timeStamp) {
		

		UserEntity user = WebserviceEntityManager.getUserEntity(username);
		
		BasketEntity basket = new BasketEntity();
		basket = entityManager.find(BasketEntity.class, timeStamp);
		if (basket == null) {
			Response response = Response.status(Status.NOT_FOUND)
					.entity("Basket not found!").build();
			throw new WebApplicationException(response);
		}
		
		if (!basket.getUser().equals(user)) {
			Response response = Response.status(Status.FORBIDDEN)
					.entity("User does not own Basket! - Should I be tellin' you this?").build();
			throw new WebApplicationException(response);
		}
		
		Basket result = new Basket();
		result.setUserId(user.getName());
		for (BasketToArticleEntity bta : basket.getBasketToArticle()) {
			result.getBarcodes().add(bta.getArticle().getBarcode());
		}
		//TODO: how does amount of article(s) in basket does not get lost?

		
		return result;
	}

	@PUT
	@Path("/{username}/{basket}/")
	@Consumes(MediaType.APPLICATION_JSON)
	public void putBasket(@PathParam("username") String username, @PathParam("basket") long timeStamp, BasketEntity basket) {
		UserEntity user = WebserviceEntityManager.getUserEntity(username);
		
		if (basket != null) {
			entityManager.persist(basket);
		}
	}
	
	

}