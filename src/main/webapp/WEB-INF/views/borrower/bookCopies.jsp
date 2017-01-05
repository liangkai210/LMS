<%@include file="/template.html"%>
<%@page import="com.kailiang.lms.bean.Book"%>
<%@page import="com.kailiang.lms.bean.BookCopies"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%
	String cardNo = (String) request.getAttribute("cardNo");
	String branchId = (String) request.getAttribute("branchId");
	List<BookCopies> list = (List<BookCopies>) request.getAttribute("list");
	Map<Integer, Book> map = (Map<Integer, Book>) request.getAttribute("map");
%>
<%=cardNo%>
<%=list.size()%>
<%=map.size()%>
<div class="container">
	<h2>Branch Details</h2>
	<hr>
	<div class="row">
		<div class="col-md-4"></div>
		<div class="col-md-4">
			<form action="checkout">
				<%
					for (BookCopies bookCopy : list) {
						if (bookCopy.getNoOfCopies() > 0) {
				%>
				<input type="hidden" name="branchId" class="form-control"
					value="<%=bookCopy.getBranchId()%>">
				<input type="checkbox" name="bookId" class="form-control"
					value="<%=bookCopy.getBookId()%>">
				<input type="hidden" name="bookTitle"
					value="<%=map.get(bookCopy.getBookId()).getTitle()%>"
					readonly="readonly">
				<%=map.get(bookCopy.getBookId()).getTitle()%>
				<input type="hidden" name="noOfCopies" class="form-control"
					value="<%=bookCopy.getNoOfCopies()%>"> <input type="hidden"
					name="operator" class="form-control" value="updateCopies">
				<br> <input type="hidden" name="cardNo" value="<%=cardNo%>">
				<%
					}
					}
				%>
				<button class="btn btn-lg btn-primary btn-block" type="submit">Confirm</button>
			</form>
		</div>
		<div class="col-md-4"></div>
	</div>

</div>
