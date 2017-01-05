<%@include file="/template.html"%>
<%@page import="java.util.Map"%>
<%@page import="com.kailiang.lms.bean.BookLoans"%>
<%@page import="com.kailiang.lms.bean.Book"%>
<%@page import="java.util.List"%>
<%@page import="com.kailiang.lms.dao.BookDao"%>
<%
	String cardNo = (String) request.getAttribute("cardNo");
	List<BookLoans> list = (List<BookLoans>) request.getAttribute("list");
	Map<Integer, Book> map = (Map<Integer, Book>) request.getAttribute("map");
%>


<h2>Returning Books</h2>
<div class="container">
	<hr>
	<div class="row">
		<div class="col-md-4"></div>

		<div class="col-md-4">
			<form class="form-signin" action="returnFinish">
				<h5 class="form-signin-heading"></h5>
				<h5>Books</h5>
				<%
					for (BookLoans bookLoans : list) {
				%>
				<br> <input type="checkbox" name="bookId"
					value="<%=bookLoans.getBookId()%>"> <input type="hidden"
					name="branchId" value="<%=bookLoans.getBranchId()%>"> <br>
				<input type="hidden" name="bookName"
					value="<%=map.get(bookLoans.getBookId()).getTitle()%>">
				<%=map.get(bookLoans.getBookId()).getTitle()%>
				<br>
				<%
					}
				%>
				<br> <br> <input type="hidden" name="cardNo"
					value="<%=cardNo%>">
				<button class="btn btn-lg btn-primary btn-block" type="submit">Confirm</button>
			</form>
		</div>
		<div class="col-md-4"></div>
	</div>

</div>
