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
import at.jku.smartshopper.persistence.ArticleEntity;


@ApplicationPath("/")
@Path("/article")
@Stateless
public class ArticleResource {

//	@PersistenceUnit(unitName = "SmartShopperPersistence")
	@Inject
	private EntityManager entityManager;
	@Inject
	private WebserviceEntityManager webserviceEntityManager;

	@GET
	@Path("/{barcode}")
	@Produces(MediaType.APPLICATION_JSON)
	public Article getArticle(@PathParam("barcode") String barcode) {
		ArticleEntity articleEntity = webserviceEntityManager.getEntity(ArticleEntity.class, barcode);
		Article result = new Article();
		result.setBarcode(barcode);
		result.setName(articleEntity.getName());
		result.setPrice(articleEntity.getPrice());

		return result;
	}
	
	@PUT
	@Path("/{barcode}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void putArticle(@PathParam("barcode") String barcode, Article article) {
		if (article != null) {
			ArticleEntity exisitingEntity = entityManager.find(ArticleEntity.class, barcode);
			if (exisitingEntity != null) {
				Response response = Response.status(Status.CONFLICT).entity("Article with given barcode already exists!").build();
				throw new WebApplicationException(response);
			}
			ArticleEntity articleEntity = new ArticleEntity();
			articleEntity.setBarcode(barcode);
			articleEntity.setName(article.getName());
			articleEntity.setPrice(article.getPrice());			
			entityManager.persist(articleEntity);
		}
	}

}