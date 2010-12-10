package dao;

import entity.TodoEntity;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

public class TodoDAOImpl implements TodoDAO {
	private PersistenceManager pm = PMF.get();

	public TodoDAOImpl () {
	}

	public List<TodoEntity> getAll() {
		Query query = pm.newQuery(TodoEntity.class);
		query.setOrdering("date DESC");

		List<TodoEntity> todos = new ArrayList<TodoEntity>();
		try {
			todos = (List<TodoEntity>) query.execute();
		} finally {
			query.closeAll();
		}

		return todos;
	}
	public List<TodoEntity> getAll(int limit) {
		Query query = pm.newQuery(TodoEntity.class);
		// query.setFilter("done == doneParam");
		// query.declareParameters("String doneParam");
		query.setOrdering("date DESC");
		query.setRange(0, limit);

		List<TodoEntity> todos = new ArrayList<TodoEntity>();
		try {
			todos = (List<TodoEntity>) query.execute();
		} finally {
			query.closeAll();
		}

		return todos;
	}

	public List<TodoEntity> getRange(Date from, Date to) {
		Query query = pm.newQuery(TodoEntity.class);
		query.setFilter("date >= dateFromParam");
		query.setFilter("date <= dateToParam");
		query.declareImports("import java.util.Date;");
		query.declareParameters("Date dateFromParam, Date dateToParam");
		query.setOrdering("date DESC");

		List<TodoEntity> todos = new ArrayList<TodoEntity>();
		try {
			todos = (List<TodoEntity>) query.execute(from, to);
		} finally {
			query.closeAll();
		}

		return todos;
	}

	/*
	public List<TodoEntity> getAll() {
		Entity entity = new Entity("kind");
		Email myMail = new Mail("test@example.com");
		entity.setProperty("emailProperty", myMail);
		Key key = datastore.put(entity);
		Entity storedEntity = datastore.get(key);
		Email storedEmail = storedEntity.getProperty("emailProperty");

		DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();

		Query q = new Query(TodoEntity.class.getName());
		PreparedQuery pq = datastoreService.prepare(q);
		int limit = pq.countEntities(null) > 0 ? pq.countEntities(null) : 1;
		List<Entity> todos = pq.asList(withLimit(limit));
		for (Entity todo : todos) {
			// pretypovat
		}

		return todos;
	}*/

	public TodoEntity get(Long id) {
		TodoEntity detached = null;
		try {
			TodoEntity todo = pm.getObjectById(TodoEntity.class, id);
			detached = pm.detachCopy(todo);
		} catch (JDOObjectNotFoundException e) {
			return null;
		} finally {
			// pm.close();
		}
		
		return detached;
	}

	public void create(TodoEntity todo) {
		try {
			pm.makePersistent(todo);
		} finally {
			// pm.close();
		}
	}

	public void update(TodoEntity todo) {
		try {
			// TodoEntity todoPersistent = pm.getObjectById(TodoEntity.class, todo.getId());
			// todoPersistent.setText(todo.getText());
			// todoPersistent.setDate(todo.getDate());
			pm.makePersistent(todo);
		} finally {
			// pm.close();
		}
	}

	/*public void delete(TodoEntity todo) {
		pm.deletePersistent(todo);
	}*/
	public void delete(Long id) {
		TodoEntity todo = pm.getObjectById(TodoEntity.class, id);
		pm.deletePersistent(todo);
	}
}
