package dao;

import com.google.appengine.api.datastore.Key;
import entity.TodoEntity;
import java.util.Collection;
import java.util.Date;

public interface TodoDAO {
	public Collection<TodoEntity> getAll();
	public Collection<TodoEntity> getAll(int limit);
	public Collection<TodoEntity> getRange(Date from, Date to);
	public TodoEntity get(Key id);
	public void create(TodoEntity todo);
	public void update(TodoEntity todo);
	public void delete(Key id);
}
