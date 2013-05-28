package at.jku.smartshopper.backend;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;

import at.jku.smartshopper.backend.util.WebserviceEntityManager;
import at.jku.smartshopper.persistence.ArticleEntity;
import at.jku.smartshopper.persistence.BasketEntity;
import at.jku.smartshopper.persistence.BasketToArticleEntity;
import at.jku.smartshopper.persistence.ShopEntity;
import at.jku.smartshopper.persistence.UserEntity;

@ApplicationPath("/")
@Path("/basket")
@Stateless
public class BasketResource {

	@Inject
	private EntityManager entityManager;
	@Inject
	private WebserviceEntityManager webserviceEntityManager;


	@RolesAllowed("user")
	@GET
	@Path("/{username}/basket/{timestamp}/")
	@Produces(MediaType.APPLICATION_JSON)
	public Basket getBasket(@PathParam("username") String username, @PathParam("timestamp") long timeStamp, @Context SecurityContext securityContext) {
		String securedUser = securityContext.getUserPrincipal().getName();
		// load user entity from database
		UserEntity userEntity = webserviceEntityManager.getEntity(UserEntity.class, username);
		// load basket entity from database
		BasketEntity basketEntity = webserviceEntityManager.getEntity(BasketEntity.class, new Date(timeStamp));
		
		// if user does not own basket
		if (!basketEntity.getUser().equals(userEntity)) {
			Response response = Response.status(Status.FORBIDDEN)
					.entity("User does not own Basket! - Should I be tellin' you this?").build();
			throw new WebApplicationException(response);
		}

		// everything ok - now fill basket object and return it
		return mapBasket(basketEntity);
	}
	
	@PermitAll
	@GET
	@Path("/{username}/basket/latest")
	@Produces(MediaType.APPLICATION_JSON)
	public Basket getLatestBasket(@PathParam("username") String username) {
		// load user entity from database
		UserEntity userEntity = webserviceEntityManager.getEntity(UserEntity.class, username);
		
		//determine latest basket
		BasketEntity latestBasketEntity = null;
		for (BasketEntity basketEntity : userEntity.getBaskets()) {
			if (latestBasketEntity == null) {
				latestBasketEntity = basketEntity;
			}
			if (basketEntity.getInsertStamp().after(latestBasketEntity.getInsertStamp())) {
				latestBasketEntity = basketEntity;
			}
		}
		
		//if user has no baskets
		if (latestBasketEntity == null) {
			Response response = Response.status(Status.NOT_FOUND).entity("No Basket found!").build();
			throw new WebApplicationException(response);
		}
		
		return mapBasket(latestBasketEntity);
	}

	@PermitAll
	@GET
	@Path("/{username}/basket/all")
	@Produces(MediaType.APPLICATION_JSON)
	public BasketList getAllBaskets(@PathParam("username") String username){
		// load user entity from database
		UserEntity userEntity = webserviceEntityManager.getEntity(UserEntity.class, username);
		
		BasketList basketList = new BasketList();
		
		for (BasketEntity basketEntity : userEntity.getBaskets()) {
			Basket basket = mapBasket(basketEntity);
			basketList.getBaskets().add(basket);
		}
		
		return basketList;
	}


	@RolesAllowed("user")
	@PUT
	@Path("/{username}/basket/{timestamp}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void putBasket(@PathParam("username") String username, @PathParam("timestamp") long timeStamp, Basket basket) {
		//load user entity from database - throws exception
		UserEntity userEntity = webserviceEntityManager.getEntity(UserEntity.class, username);
		
		
		
		if (basket != null) {
			//get shop entity from db
			ShopEntity shopEntity = webserviceEntityManager.getEntity(ShopEntity.class, basket.getShopId());
			
			//begin transaction
			BasketEntity basketEntity = new BasketEntity();
			//set attributes of basketEntity 
			basketEntity.setUser(userEntity);
			basketEntity.setShop(shopEntity);
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
			}
			basketEntity.setBasketToArticle(basketToArticle);
						
			userEntity.getBaskets().add(basketEntity);
			
			entityManager.persist(basketEntity);
		}
	}

	/** Creates Basket object from given BasketEntity object.
	 * 
	 * @param basketEntity
	 * @return
	 */
	private Basket mapBasket(BasketEntity basketEntity) {
		Basket basket = new Basket();
		basket.setUserId(basketEntity.getUser().getUsername());
		basket.setShopId(basketEntity.getShop().getShopId());
		// add all items stored in basketEntity to basket
		for (BasketToArticleEntity bta : basketEntity.getBasketToArticle()) {
			BasketRow row = new BasketRow();
			row.setBarcode(bta.getArticle().getBarcode());
			row.setQuantity(BigInteger.valueOf(bta.getAmount()));
			row.setPrice(bta.getPrice());
			row.setName(bta.getArticle().getName());
			basket.getRows().add(row);
		}
		
		return basket;
	}
}