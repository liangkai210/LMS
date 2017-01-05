<%@include file="/../template.html"%>
<%@page import="com.kailiang.lms.bean.Book"%>
<%@page import="java.util.List"%>
<%@page import="com.kailiang.lms.dao.BookDao"%>
<%
	String title = request.getParameter("title");
%>
<%=title%>


<div class="container">
	<h2>Books</h2>
	<hr>
	<div class="row">
		<div class="col-md-4"></div>

		<div class="col-md-4">
			<form class="form-signin" action="detail.jsp">
				<h5 class="form-signin-heading"></h5>
				<h5>Books</h5>
				<%
					for (Book book : (List<Book>) request.getAttribute("books")) {
				%>
				<br> <input type="radio" name="bookId"
					value="<%=book.getBookId()%>"> <br> <input
					type="hidden" name="bookTitle" value="<%=book.getTitle()%>">
				<%=book.getTitle()%>
				<%
					}
				%>
				<br> <br>
				<button class="btn btn-lg btn-primary btn-block" type="submit">Confirm</button>
			</form>
		</div>
		<div class="col-md-4"></div>
	</div>
	<br> <br> <br> <br>
	<h4>
		<a href="bookMenu.jsp">Back to the main menu</a>
	</h4>
</div>
