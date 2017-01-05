<%@include file="/template.html"%>
<%
	String branchName = request.getParameter("branchName");
	
%>
<div class="container">
	<h2>Add Branch</h2>
	<hr>
	<div class="row">
		<div class="col-md-4"></div>
		<div class="col-md-4" >
			<h4>
				ADD BRANCH:
				<%=branchName%>
				Success!
			</h4>
			<a href="redirectBranAdd" class="list-group-item list-group-item-success"> Add another branch</a><br> <a
				href="redirectBranMenu" class="list-group-item list-group-item-danger"> Return to Main Menu</a>
		</div>
		<div class="col-md-4"></div>
	</div>
</div>
