<%@include file="/template.html" %>
<%@page import="java.util.Date"%>
<%
	String date = (String)request.getAttribute("dateOut");
	String dueDate = (String) request.getAttribute("dueDate");
%>

	<div class="container">
	<h2>Check Out</h2>
		<hr>
		<div class="row">
			<div class="col-md-4"></div>
			<div class="col-md-4">
				<h3>
				The date you checked out:
				<%=new Date(Long.parseLong(date)) %>
				<br>
				The due date :
				<%=new Date(Long.parseLong(dueDate)) %>
				</h3>
				<br> <a href="redirectMainMenu"> Main Menu </a>
			</div>
			<div class="col-md-4"></div>
		</div>
	</div>
