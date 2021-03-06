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
import java.util.Date;
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
		List<Entity> entities =  p.asList(FetchOptions.Builder.withLimit(999999999));
		for (Entity e : entities) {
			todoEntities.add(new TodoEntity(e));
		}

		return todoEntities;
	}

	public List<TodoEntity> getAll(int limit) {
		Query query = new Query("todoEntity");
		query.addSort("date", Query.SortDirection.DESCENDING);
		List<TodoEntity> todoEntities = new ArrayList<TodoEntity>();
		PreparedQuery p = datastore.prepare(query);
		List<Entity> entities =  p.asList(FetchOptions.Builder.withLimit(limit));
		for (Entity e : entities) {
			todoEntities.add(new TodoEntity(e));
		}

		return todoEntities;
	}

	public List<TodoEntity> getRange(Date from, Date to) {
		Query query = new Query("todoEntity");
		query.addFilter("date", Query.FilterOperator.GREATER_THAN_OR_EQUAL, from);
		query.addFilter("date", Query.FilterOperator.LESS_THAN_OR_EQUAL, to);
		query.addSort("date", Query.SortDirection.DESCENDING);
		List<TodoEntity> todoEntities = new ArrayList<TodoEntity>();
		PreparedQuery p = datastore.prepare(query);
		List<Entity> entities =  p.asList(FetchOptions.Builder.withLimit(999999999));
		for (Entity e : entities) {
			todoEntities.add(new TodoEntity(e));
		}

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
