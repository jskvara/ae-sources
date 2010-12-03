package dao;

import entity.TodoEntity;
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
		return getAll(100);
	}

	public List<TodoEntity> getAll(int limit) {
		Query query = em.createQuery("SELECT t FROM TodoEntity t ORDER BY t.created DESC");
		query.setMaxResults(limit);
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
