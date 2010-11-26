package dao;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.FetchOptions.Builder.*;
import com.google.appengine.api.datastore.PreparedQuery;
import entity.TodoEntity;
import java.util.ArrayList;
import java.util.List;

public class TodoDAOImpl implements TodoDAO {
	private DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	
	public TodoDAOImpl () {
	}

	public List<TodoEntity> getAll() {
		Query query = new Query("todoEntity");
		query.addSort("date", Query.SortDirection.DESCENDING);
		List<TodoEntity> todoEntities = new ArrayList<TodoEntity>();
		PreparedQuery p = datastore.prepare(query);
//		int limit = p.countEntities(FetchOptions.Builder.withLimit(100)) > 0 ?
//			p.countEntities(FetchOptions.Builder.withLimit(100)) : 1;
		List<Entity> entities =  p.asList(FetchOptions.Builder.withLimit(100));
		for (Entity e : entities) {
			todoEntities.add(new TodoEntity(e));
		}
		System.out.println(todoEntities);

		return todoEntities;
	}

	public TodoEntity get(Key key) {
		TodoEntity todoEntity = null;
		try {
			Entity entity = datastore.get(key);
			todoEntity = new TodoEntity(entity);
		} catch (EntityNotFoundException e) {
		}

		return todoEntity;
	}

	public void create(TodoEntity todo) {
		Entity todoEntity = todo.getEntity();
		datastore.put(todoEntity);
	}

	public void update(TodoEntity todo) {
		try {
			Entity entity = datastore.get(todo.getKey());
			entity = todo.mergeWithoutKey(entity);
			datastore.put(entity);
		} catch (EntityNotFoundException e) {
		}
	}

	public void delete(Key key) {
		datastore.delete(key);
	}
}
