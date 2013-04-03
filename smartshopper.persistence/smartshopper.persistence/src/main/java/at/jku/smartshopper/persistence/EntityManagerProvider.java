package at.jku.smartshopper.persistence;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class EntityManagerProvider {
	
	@Produces
	@PersistenceContext(unitName = "SmartShopperPersistence")
	private EntityManager entityManager;

}
