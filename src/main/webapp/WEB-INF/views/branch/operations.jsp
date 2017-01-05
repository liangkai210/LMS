<%@include file="/template.html"%>
<%
	String operator = (String) request.getAttribute("selection");
%>
<div class="container">
<h2>Operations</h2>
	<hr>
	<div class="row">
		<div class="col-md-4"></div>
		<div class="col-md-4">
			<h4>
				<%
					if (operator.equals("addCopies")) {
				%>
				Copy Add Success!

				<%
					}
					if (operator.equals("update")) {
				%>
				Update Success!

				<%
					}
					if (operator.equals("delete")) {
				%>
				Delete Success!
				<%
					}
				%>
			</h4>
			<br> <a href="redirectBranMenu"> Return to Main Menu</a>
		</div>
		<div class="col-md-4"></div>
	</div>
</div>
