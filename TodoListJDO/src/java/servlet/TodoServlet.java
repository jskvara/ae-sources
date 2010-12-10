package servlet;

import com.google.inject.Guice;
import com.google.inject.Injector;
import dao.PMF;
import entity.TodoEntity;
import guice.GuiceModule;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Date;
import java.util.Random;
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
		} else if ("benchmark-create".equalsIgnoreCase(action)) {

			long loops = 100;
			long startTime = System.currentTimeMillis();
			for (int i = 0; i < loops; i++) {
				todoService.create("Text "+ i);
			}
			long endTime = System.currentTimeMillis();
			PrintWriter out = response.getWriter();
			out.println(loops +"x insert takes "+ (endTime-startTime) +" millis");
			return;
		} else if ("benchmark-edit".equalsIgnoreCase(action)) {
			long loops = 100;
			long startTime = System.currentTimeMillis();
			for (int i = 0; i < loops; i++) {
				todoService.edit((long)(i+100) , "Text "+ i);
			}
			long endTime = System.currentTimeMillis();
			PrintWriter out = response.getWriter();
			out.println(loops +"x edit takes "+ (endTime-startTime) +" millis");
			return;
		} else if ("benchmark-delete".equalsIgnoreCase(action)) {
			Collection<TodoEntity> todos = todoService.getAllTodos(100);
			long loops = 100;
			long startTime = System.currentTimeMillis();
			for (TodoEntity t : todos) {
				todoService.delete(t.getId());
			}
			long endTime = System.currentTimeMillis();
			PrintWriter out = response.getWriter();
			out.println(loops +"x delete takes "+ (endTime-startTime) +" millis");
			return;
		} else if ("benchmark-select".equalsIgnoreCase(action)) {
			int entities = 1000;
			long startTime = System.currentTimeMillis();
			todoService.getAllTodos(entities);
			long endTime = System.currentTimeMillis();
			PrintWriter out = response.getWriter();
			out.println("select "+ entities +" entities takes "+ (endTime-startTime) +" millis");
			return;
		} else if ("delete-all".equalsIgnoreCase(action)) {
			Collection<TodoEntity> todos = todoService.getAllTodos();
			for (TodoEntity e : todos) {
				todoService.delete(e.getId());
			}
			PrintWriter out = response.getWriter();
			out.println("All entities were deleted");
			return;
		} else if ("insert-random".equalsIgnoreCase(action)) {
			long loops = 1000;
			long range = 2000000000L;
			Random r = new Random();
			PrintWriter out = response.getWriter();
			for (int i = 0; i < loops; i++) {
				long d = (long)(r.nextFloat() * 10000 * range);
				todoService.create("Text "+ i, new Date(d));
			}
			out.println("inserted "+ loops +" random entities");
			return;
		} else if ("benchmark-range".equalsIgnoreCase(action)) {
			long loops = 100;
			PrintWriter out = response.getWriter();
			long startTime = System.currentTimeMillis();
			Date from = new Date(2000, 0, 1);
			Date to = new Date(2010, 11, 31);
			for (int i = 0; i < loops; i++) {
				todoService.getRange(from, to);
			}
			long endTime = System.currentTimeMillis();
			out.println("select from range "+ from +", "+ to +" takes "+ (endTime-startTime) +" millis");
			return;
		}

		Collection<TodoEntity> todos = todoService.getAllTodos();
		request.setAttribute("todos", todos);
		PMF.get().close();

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
