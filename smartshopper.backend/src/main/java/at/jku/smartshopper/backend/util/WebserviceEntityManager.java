package at.jku.smartshopper.backend.util;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Stateless
public class WebserviceEntityManager {
	
	@Inject
	private EntityManager entityManager;
	
	public <T> T getEntity(Class<T> entityClass, Object primaryKey) {
		T entity = entityManager.find(entityClass,  primaryKey);
		if (entity == null) {
			Response response = Response.status(Status.NOT_FOUND)
					.entity("Entity not found!").build();
			throw new WebApplicationException(response);
		}
		return entity;
	}

}
