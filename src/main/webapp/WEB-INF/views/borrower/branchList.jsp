<%@include file="/template.html" %>
<%@page import="com.kailiang.lms.bean.Branch"%>
<%@page import="com.kailiang.lms.bean.Book"%>
<%@page import="java.util.List"%>
<%@page import="com.kailiang.lms.dao.BookDao"%>
<%
	Integer cardNo = (Integer) request.getAttribute("cardNo");
	List<Branch> list = (List<Branch>) request.getAttribute("list");
%>


<div class="container">
	<h2>Branches</h2>
	<hr>
	<div class="row">
		<div class="col-md-2"></div>

		<div class="col-md-8">
			<form class="form-signin" action="bookCopyList">
				<h5 class="form-signin-heading"></h5>
				<h5>Branches</h5>
				<table class="table">
					<tr>
						<th>#</th>
						<th>Branch Name</th>
						<th>Branch Address</th>
						<th>Selection</th>
					</tr>
					<%
						for (Branch branch : list) {
					%>
					<tr>
						<td><%=(list.indexOf(branch) + 1)%></td>
						<td><%=branch.getBranchName()%></td>
						<td><%=branch.getBranchAddress()%></td>
						<td><a
							href="bookCopyList?branchId=<%=branch.getBranchId()%>&cardNo=<%=cardNo%>"><button
									type="button" class="btn btn-primary">Confirm</button></a></td>
					</tr>
					<%
						}
					%>
				</table>
			</form>
		</div>
		<div class="col-md-2"></div>
	</div>

</div>
