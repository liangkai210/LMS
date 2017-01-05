<%@include file="/template.html"%>
<%@page import="java.util.Date"%>
<%
	String cardNo = (String) request.getAttribute("cardNo");
	String dateOut = (String) request.getAttribute("dateOut");
	String dueDate = (String) request.getAttribute("dueDate");
	String bookId = (String) request.getAttribute("bookId");
	String branchId = (String) request.getAttribute("branchId");
%>
<div class="container">
	<h2>Borrower Details</h2>
	<hr>
	<div class="row">
		<div class="col-md-4"></div>
		<div class="col-md-4">
			<form class="form-signin" action="updateDate">
				<input type="hidden" name="operator" value="update"> <input
					type="hidden" name="cardNo" value="<%=cardNo%>">
				<h5 class="form-signin-heading">Update</h5>
				<input type="date" id="inputBook" name="dueDate"
					class="form-control" value="<%=dueDate%>" required autofocus>
				<br> <input type="hidden" name="bookId" value="<%=bookId%>">
				<input type="hidden" name="branchId" value="<%=branchId%>">
				<input type="hidden" name="dateOut" value="<%=dateOut%>">
				<button class="btn btn-lg btn-primary btn-block" type="submit">Update</button>
			</form>
		</div>
		<div class="col-md-4"></div>
	</div>
</div>
