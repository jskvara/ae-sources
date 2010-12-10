package dao;

import entity.TodoEntity;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

public class TodoDAOImpl implements TodoDAO {
	private EntityManager em = EMF.get();
	private EntityTransaction tx = em.getTransaction();

	public TodoDAOImpl () {
	}

	public List<TodoEntity> getAll() {
		Query query = em.createQuery("SELECT t FROM TodoEntity t ORDER BY t.created DESC");
		
		return query.getResultList();
	}

	public List<TodoEntity> getAll(int limit) {
		Query query = em.createQuery("SELECT t FROM TodoEntity t ORDER BY t.created DESC");
		query.setMaxResults(limit);
		
		return query.getResultList();
	}

	public Collection<TodoEntity> getRange(Date from, Date to) {
		Query query = em.createQuery("SELECT t FROM TodoEntity t "
			+ "WHERE created <= :dateFrom AND created >= :dateTo "
			+ "ORDER BY t.created DESC");
		query.setParameter("dateFrom", from);
		query.setParameter("dateTo", to);

		return query.getResultList();
	}

	public TodoEntity get(Long id) {
		return em.find(TodoEntity.class, id);
	}

	public void create(TodoEntity todo) {
		tx.begin();
		em.persist(todo);
		tx.commit();
	}

	public void update(TodoEntity todo) {
		tx.begin();
		em.merge(todo);
		tx.commit();
	}

	public void delete(Long id) {
		tx.begin();
		TodoEntity todo = em.find(TodoEntity.class, id);
		em.remove(todo);
		tx.commit();
	}
}
