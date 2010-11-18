package servlet;

import com.google.inject.Guice;
import com.google.inject.Injector;
import entity.TodoEntity;
import guice.GuiceModule;
import java.io.IOException;
import java.util.Collection;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import service.TodoService;

public class TodoServlet extends HttpServlet {
   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

		Injector injector = Guice.createInjector(new GuiceModule());
		TodoService todoService = injector.getInstance(TodoService.class);

		String action = request.getParameter("action");
		if ("new".equalsIgnoreCase(action)) {
			String text = request.getParameter("text");
			if (text != null && !text.equals("")) {
				todoService.create(text);
			}
		} else if ("edit".equalsIgnoreCase(action)) {
			Long id = Long.valueOf(request.getParameter("id"));
			if (id != null) {
				String text = request.getParameter("text");
				if (text != null && !text.equals("")) {
					todoService.edit(id, text);
				}
			}
		} else if ("delete".equalsIgnoreCase(action)) {
			Long id = Long.valueOf(request.getParameter("id"));
			if (id != null) {
				todoService.delete(id);
			}
		}

		Collection<TodoEntity> todos = todoService.getAllTodos();
		request.setAttribute("todos", todos);

		ServletContext context = getServletContext();
		RequestDispatcher rd = context.getRequestDispatcher("/WEB-INF/viewTodos.jsp");
		rd.include(request, response);
    } 

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		processRequest(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		processRequest(request, response);
	}
}
