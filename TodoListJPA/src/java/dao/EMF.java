package dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public final class EMF {
	private static final EntityManagerFactory emfInstance =
		Persistence.createEntityManagerFactory("transactions-optional");

	private EMF() {}

	public static EntityManager get() {
		return emfInstance.createEntityManager();
	}
}
