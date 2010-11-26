<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="/WEB-INF/tlds/jstlFunctions" prefix="f" %>
<%@include file="header.jsp" %>

<form method="post" action="?action=new">
	New todo
	<input type="text" name="text" id="text" />
	<input name="submit" id="submit" value="Add" type="submit" />
</form>
<hr />

<c:if test="${empty todos}">
	<p>No todos</p>
</c:if>
<c:if test="${not empty todos}">
	<%--${todos} ${requestScope['todos']} ${requestScope.todos} --%>
	<c:forEach var="todo" items="${todos}">
		<form action="?action=edit" method="post">
			[${f:formatDate(todo.date, "d.M.yyyy HH:mm:ss")}]:
			<input type="text" name="text" value="<c:out value="${todo.text}" />" />
			<input type="hidden" name="id" value="${todo.keyAsString}" />
			<input type="submit" name="submit" value="Edit" />
			<a href="?action=delete&id=${todo.keyAsString}">X</a>
		</form>
	</c:forEach>
</c:if>

<%@include file="footer.jsp" %>