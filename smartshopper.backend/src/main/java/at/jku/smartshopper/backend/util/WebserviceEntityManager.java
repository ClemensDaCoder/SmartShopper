package at.jku.smartshopper.backend.util;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import at.jku.smartshopper.persistence.ArticleEntity;
import at.jku.smartshopper.persistence.UserEntity;

public class WebserviceEntityManager {
	
	@Inject
	private static EntityManager entityManager;
	
	public static UserEntity getUserEntity(String username) {
		UserEntity user = new UserEntity();
		user = entityManager.find(UserEntity.class,  username);
		if (user == null) {
			Response response = Response.status(Status.NOT_FOUND)
					.entity("User not found!").build();
			throw new WebApplicationException(response);
		}
		return user;
	}
	
	public static ArticleEntity getArticleEntity(String barcode) {
		ArticleEntity articleEntity = new ArticleEntity();			
	    articleEntity = entityManager.find(ArticleEntity.class, barcode);
		if (articleEntity == null) {
			Response response = Response.status(Status.NOT_FOUND).entity("Article not found!").build();
			throw new WebApplicationException(response);
		}
		return articleEntity;
	}

}
