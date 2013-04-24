package at.jku.smartshopper.backend;

import java.math.BigInteger;

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
import javax.ws.rs.core.Response.Status;

import at.jku.smartshopper.backend.util.WebserviceEntityManager;
import at.jku.smartshopper.persistence.ShopEntity;

@ApplicationPath("/")
@Path("/shop")
@Stateless
public class ShopResource {
	
	@Inject
	private EntityManager entityManager;
	@Inject
	private WebserviceEntityManager webserviceEntityManager;

	
	@PUT
	@Path("/{shopId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void putShop(@PathParam("shopId") Long shopId, Shop shop) {
		if (shop == null || shop.getName() == null) {
			throw new WebApplicationException(Status.BAD_REQUEST);
		}
		
//		ShopEntity exisitingEntity = entityManager.find(ShopEntity.class, shop.getShopId());
//		if (exisitingEntity != null) {
//			Response response = Response.status(Status.CONFLICT).entity("Shop with given shopId already exists!").build();
//			throw new WebApplicationException(response);
//		}
		
		ShopEntity shopEntity = new ShopEntity();
		shopEntity.setShopId(shop.getShopId());
		shopEntity.setName(shop.getName());
		shopEntity.setStreet(shop.getStreet());
		shopEntity.setZip(shop.getZip().intValue());
		shopEntity.setCity(shop.getCity());
		
		entityManager.persist(shopEntity);
//		entityManager.merge(shopEntity);
	}
	
	
	@GET
	@Path("/{shopId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Shop getShop(@PathParam("shopId") Long shopId) {
		ShopEntity shopEntity = webserviceEntityManager.getEntity(ShopEntity.class, shopId);

		Shop shop = new Shop();
		
		shop.setShopId(shopEntity.getShopId());
		shop.setName(shopEntity.getName());
		shop.setStreet(shopEntity.getStreet());
		shop.setZip(BigInteger.valueOf(shopEntity.getZip()));
		shop.setCity(shopEntity.getCity());
		
		return shop;
	}


}
