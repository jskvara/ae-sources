package service;

import com.google.inject.Inject;
import dao.TodoDAO;
import entity.TodoEntity;
import java.util.Collection;
import java.util.Date;

public class TodoService {

	@Inject
	TodoDAO todoDAO;

	public Collection<TodoEntity> getAllTodos() {
		return todoDAO.getAll();
	}

	public void create(String text) {
		TodoEntity todo = new TodoEntity(null, text, new Date());
		todoDAO.create(todo);
	}

	public void edit(Long id, String text) {
		TodoEntity todo = new TodoEntity(id, text, new Date());
		todoDAO.update(todo);
	}

	public void delete(Long id) {
		todoDAO.delete(id);
	}
}
