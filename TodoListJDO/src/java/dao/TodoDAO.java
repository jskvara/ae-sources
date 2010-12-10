package dao;

import entity.TodoEntity;
import java.util.Collection;
import java.util.Date;

public interface TodoDAO {
	public Collection<TodoEntity> getAll();
	public Collection<TodoEntity> getAll(int limit);
	public TodoEntity get(Long id);
	public void create(TodoEntity todo);
	public void update(TodoEntity todo);
	public void delete(Long id);

	public Collection<TodoEntity> getRange(Date from, Date to);
}
