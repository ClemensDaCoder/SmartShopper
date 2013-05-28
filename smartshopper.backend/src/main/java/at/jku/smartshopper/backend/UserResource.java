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
import javax.ws.rs.core.Response.Status;

import org.apache.commons.codec.digest.DigestUtils;

import at.jku.smartshopper.backend.util.WebserviceEntityManager;
import at.jku.smartshopper.persistence.UserEntity;

@ApplicationPath("/")
@Path("/user")
@Stateless
public class UserResource {

	@Inject
	private EntityManager entityManager;
	@Inject
	private WebserviceEntityManager webserviceEntityManager;

	@PUT
	@Path("/{username}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void putUser(@PathParam("username") String username, User user) {
		if (user == null || user.getUsername() == null
				|| !user.getUsername().equals(username)
				|| user.getPassword() == null) {
			throw new WebApplicationException(Status.FORBIDDEN);
		}
		String hashedPassword = DigestUtils.sha1Hex(user.getPassword());

		UserEntity userEntity = new UserEntity();
		userEntity.setName(user.getName());
		userEntity.setAccountNumber(user.getAccountNumber());
		userEntity.setPasswordHash(hashedPassword);
		userEntity.setSortCode(user.getSortCode());
		userEntity.setSurname(user.getSurname());
		userEntity.setUsername(user.getUsername());
		userEntity.setRole("user");
		entityManager.persist(userEntity);
	}

	@GET
	@Path("/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public User getUser(@PathParam("username") String username) {
		UserEntity userEntity = webserviceEntityManager.getEntity(UserEntity.class, username);

		User user = new User();
		user.setAccountNumber(userEntity.getAccountNumber());
		user.setName(userEntity.getName());
		user.setSurname(userEntity.getSurname());
		user.setSortCode(userEntity.getSortCode());
		user.setUsername(userEntity.getUsername());

		return user;
	}
}
