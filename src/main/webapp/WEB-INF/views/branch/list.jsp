<%@include file="/template.html"%>
<%@page import="com.kailiang.lms.bean.Branch"%>
<%@page import="java.util.List"%>
<%
	List<Branch> list = (List<Branch>) request.getAttribute("branches");
	Integer count = (Integer) request.getAttribute("count");
	Integer pageNo = (Integer) request.getAttribute("pageNo");
	Integer pageSize = (Integer) request.getAttribute("pageSize");
	Integer pages = 1;

	if (count % 10 > 0) {
		pages = (count / 10) + 1;
	} else {
		pages = (count / 10);
	}
%>


<div class="container">
	<h2>Branches</h2>
	<hr>
	<div class="row">
		<div class="col-md-2"></div>
		<div class="col-md-8">
			<nav aria-label="Page navigation">
				<ul class="pagination">
					<li><a href="#" aria-label="Previous"> <span
							aria-hidden="true">&laquo;</span>
					</a></li>
					<%
						for (int i = 1; i <= pages; i++) {
					%>
					<li><a href="allBranch?pageNo=<%=i%>"><%=i%></a></li>
					<%
						}
					%>
					<li><a href="#" aria-label="Next"> <span
							aria-hidden="true">&raquo;</span>
					</a></li>
				</ul>
			</nav>
			<table class="table">
				<tr>
					<th>ID</th>
					<th>Branch Name</th>
					<th>Branch Address</th>
					<th>Edit Branch</th>
				</tr>
				<%
					for (Branch branch : list) {
				%>
				<form class="form-signin" action="choice">
					<tr>
						<input type="hidden" name="branchId"
							value="<%=branch.getBranchId()%>">
						<input type="hidden" name="branchName"
							value="<%=branch.getBranchName()%>">
						<input type="hidden" name="branchAddress"
							value="<%=branch.getBranchAddress()%>">
						<td><%=(list.indexOf(branch) + 1) + ((pageNo - 1) * pageSize)%></td>
						<td><%=branch.getBranchName()%></td>
						<td><%=branch.getBranchAddress()%></td>
						<td><button class="btn btn-success" type="submit">Update</button></td>
					</tr>
				</form>
				<%
					}
				%>
				<br>
				<br>
			</table>
		</div>
		<div class="col-md-2"></div>
	</div>
</div>
