package dao;

import com.google.appengine.api.datastore.Key;
import entity.TodoEntity;
import java.util.Collection;

public interface TodoDAO {
	public Collection<TodoEntity> getAll();
	public Collection<TodoEntity> getAll(int limit);
	public TodoEntity get(Key id);
	public void create(TodoEntity todo);
	public void update(TodoEntity todo);
	public void delete(Key id);
}
