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

		return result;
	}
	
	@PUT
	@Path("/{barcode}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void putArticle(@PathParam("barcode") String barcode, ArticleEntity article) {
		if (article != null) {
			article.setBarcode(barcode);
			entityManager.persist(article);
		}
	}

}