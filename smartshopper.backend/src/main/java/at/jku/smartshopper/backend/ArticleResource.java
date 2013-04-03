package at.jku.smartshopper.backend;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import at.jku.smartshopper.persistence.ArticleEntity;


@ApplicationPath("/")
@Path("/article")
@Stateless
public class ArticleResource {

//	@PersistenceUnit(unitName = "SmartShopperPersistence")
	@Inject
	private EntityManager entityManager;

	@GET
	@Path("/{barcode}")
	@Produces(MediaType.APPLICATION_JSON)
	public Article getArticle(@PathParam("barcode") String barcode) {
		ArticleEntity article = new ArticleEntity();
//		try {
			
	    article = entityManager.find(ArticleEntity.class, barcode);
		if (article == null) {
			Response response = Response.status(Status.NOT_FOUND)
					.entity("Article not found!").build();
			throw new WebApplicationException(response);
		}

		Article result = new Article();
		result.setBarcode(barcode);
		result.setName(article.getName());
		result.setPrice(article.getPrice());
//		} catch (Exception e) {
//			Response response = Response.status(Status.NOT_FOUND).entity(e).build();
//			throw new WebApplicationException(response);
//		}

		return result;
//		return null;
	}

}