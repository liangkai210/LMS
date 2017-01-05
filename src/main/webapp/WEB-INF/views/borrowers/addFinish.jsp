<%@include file="/template.html"%>
<%
	String name = request.getParameter("name");
%>

<div class="container">
	<h2>Add Borrower</h2>
	<hr>
	<div class="row">
		<div class="col-md-4"></div>
		<div class="col-md-4">
			<h4>
				ADD BORROWER:
				<%=name%>
				Success!
			</h4>
			<a href="add.jsp" class="list-group-item list-group-item-success">
				Add another Borrower</a><br> <a href="redirectBorMainMenu"
				class="list-group-item list-group-item-danger"> Return to Main
				Menu</a>
		</div>
		<div class="col-md-4"></div>
	</div>
</div>
