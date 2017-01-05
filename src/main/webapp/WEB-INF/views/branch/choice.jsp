<%@include file="/template.html"%>
<%@page import="com.kailiang.lms.bean.Branch"%>
<%@page import="com.kailiang.lms.dao.BranchDao"%>
<%
	String branchId = (String) request.getAttribute("branchId");
	String branchName = (String) request.getAttribute("branchName");
	String branchAddress = (String) request.getAttribute("branchAddress");
%>
<div class="container">
	<h2>Operations</h2>
	<hr>
	<div>
		<div class="row">
			<div class="col-xs-4 col-xs-offset-4">
				<form action="copyDetail">
					<h3>Add copies</h3>
					<input type="hidden" name="branchId" value="<%=branchId%>">
					<button class="btn btn-lg btn-primary btn-block" type="submit">Confirm</button>
				</form>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-4 col-xs-offset-4">
				<form action="redirectBranDetail">
					<h3>Update Branch</h3>
					<input type="hidden" name="branchId" value="<%=branchId%>">
					<input type="hidden" name="branchName" value="<%=branchName%>">
					<input type="hidden" name="branchAddress"
						value="<%=branchAddress%>">
					<button class="btn btn-lg btn-primary btn-block" type="submit">Confirm</button>
				</form>
			</div>
		</div>

		<br> <a href="branchMenu.jsp"> Return to Main Menu</a>
	</div>
</div>
