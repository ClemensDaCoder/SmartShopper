package at.jku.smartshopper.backend;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import at.jku.smartshopper.persistence.ArticleEntity;
import at.jku.smartshopper.persistence.BasketEntity;
import at.jku.smartshopper.persistence.BasketToArticleEntity;
import at.jku.smartshopper.persistence.UserEntity;

@ApplicationPath("/")
@Path("/basket")
@Stateless
public class BasketResource {

	@Inject
	private EntityManager entityManager;
	@Inject
	private WebserviceEntityManager webserviceEntityManager;

	@GET
	@Path("/{username}/{timestamp}/")
	@Produces(MediaType.APPLICATION_JSON)
	public Basket getBasket(@PathParam("username") String username, @PathParam("timestamp") long timeStamp) {
		// load user entity from database
		UserEntity userEntity = webserviceEntityManager.getEntity(UserEntity.class, username);
		// load basket entity from database
		BasketEntity basketEntity = webserviceEntityManager.getEntity(BasketEntity.class, new Date(timeStamp));
		
		// if user does not own basket
		if (!basketEntity.getUser().equals(userEntity)) {
			Response response = Response
					.status(Status.FORBIDDEN)
					.entity("User does not own Basket! - Should I be tellin' you this?")
					.build();
			throw new WebApplicationException(response);
		}

		// everything ok - now fill basket object and return it
		Basket basket = new Basket();
		basket.setUserId(userEntity.getUsername());
		// add all items stored in basketEntity to basket
		for (BasketToArticleEntity bta : basketEntity.getBasketToArticle()) {
			BasketRow row = new BasketRow();
			row.setBarcode(bta.getArticle().getBarcode());
			row.setQuantity(BigInteger.valueOf(bta.getAmount()));
			row.setPrice(bta.getPrice());
			basket.getRows().add(row);
		}

		return basket;
	}

	@PUT
	@Path("/{username}/basket/{timestamp}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void putBasket(@PathParam("username") String username, @PathParam("timestamp") long timeStamp, Basket basket) {
		//load user entity from database - throws exception
		UserEntity userEntity = webserviceEntityManager.getEntity(UserEntity.class, username);
		
		if (basket != null) {
//			EntityTransaction transaction = entityManager.getTransaction();
			//test
//			boolean isActive = transaction.isActive();
			
			//begin transaction
//			transaction.begin();
			BasketEntity basketEntity = new BasketEntity();
			//set attributes of basketEntity 
			basketEntity.setUser(userEntity);
			basketEntity.setInsertStamp(new Date(timeStamp));
			//create list of BasketToArticleEntity (content of basket)
			List<BasketToArticleEntity> basketToArticle = new ArrayList<BasketToArticleEntity>();
			for (BasketRow row : basket.getRows()) {
				ArticleEntity articleEntity = webserviceEntityManager.getEntity(ArticleEntity.class, row.getBarcode());
				BasketToArticleEntity b2AE = new BasketToArticleEntity();
				b2AE.setArticle(articleEntity);
				b2AE.setAmount(row.getQuantity().intValue());
				b2AE.setBasket(basketEntity);
				b2AE.setPrice(articleEntity.getPrice());
				basketToArticle.add(b2AE);
				//entityManager.persist(b2AE);
			}
			basketEntity.setBasketToArticle(basketToArticle);
//			entityManager.persist(basketToArticle);
						
			//???
			userEntity.getBaskets().add(basketEntity);
			
			//TODO check if this is enough or every entity has to be persisted
			entityManager.persist(basketEntity);
			//TODO check if complete transaction is commited
//			transaction.commit();
		}
	}

}