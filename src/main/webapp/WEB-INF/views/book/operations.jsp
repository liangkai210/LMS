
<%
	String operator = (String)request.getAttribute("selection");
%>
<!DOCTYPE html>
<html lang="en">
<head>
<jsp:include page="../common/header.jsp" />
<title>add book</title>
<style type="text/css">
h2 {
	text-align: center;
	margin: 0;
	margin-top: 10px;
}

footer {
	margin-top: 50px;
}
</style>
</head>
<body>
	<h2>Operations</h2>
	<div class="container">
		<hr>
		<div class="row">
			<div class="col-md-4"></div>
			<div class="col-md-4">
				<h4>
					<%
						if (operator.equals("update")) {
					%>
					Update Successed!

					<%
						}
						if (operator.equals("delete")) {
					%>
					Delete Successed!
					<%
						}
					%>
				</h4>
				<br> <a href="bookMenu.jsp"> Return to Main Menu</a>
			</div>
			<div class="col-md-4"></div>
		</div>
	</div>
	<!-- /container -->
	<jsp:include page="../common/footer.jsp" />
</body>
</html>
