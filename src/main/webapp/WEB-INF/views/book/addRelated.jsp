<%@include file="/template.html"%>
<%@page import="com.kailiang.lms.bean.Branch"%>
<%@page import="com.kailiang.lms.bean.Genre"%>
<%@page import="com.kailiang.lms.bean.Author"%>
<%@page import="com.kailiang.lms.bean.Publisher"%>
<%@page import="java.util.List"%>
<div class="container">
	<h2>Add Book Related</h2>
	<hr>
	<div class="row">
		<div class="col-md-4"></div>
		<div class="col-sm-3">
			<form class="form-signin" action="storeBookInDB">
				<h5 class="form-signin-heading">Add book's relations</h5>

				<h5>Publisher</h5>
				<select name="publisherId" class="selectpicker"
					data-live-search="true">
					<%
						for (Publisher pub : (List<Publisher>) request.getAttribute("publishers")) {
					%>
					<option value="<%=pub.getPublisherId()%>"><%=pub.getPublisherName()%></option>
					<%
						}
					%>
				</select> <br>
				<h5>Author</h5>
				<select name="authorId" class="selectpicker" data-live-search="true"
					multiple="multiple">
					<%
						for (Author author : (List<Author>) request.getAttribute("authors")) {
					%>
					<option value="<%=author.getAuthorId()%>"><%=author.getAuthorName()%></option>
					<%
						}
					%>
				</select> <br>
				<h5>Genre</h5>
				<select name="genre_id" class="selectpicker" data-live-search="true"
					multiple="multiple">
					<%
						for (Genre genre : (List<Genre>) request.getAttribute("genres")) {
					%>
					<option value="<%=genre.getGenre_id()%>"><%=genre.getGenre_name()%></option>
					<%
						}
					%>
				</select> <br>
				<h5>Branch</h5>
				<select name="branchId" class="selectpicker" data-live-search="true"
					multiple="multiple">
					<%
						for (Branch branch : (List<Branch>) request.getAttribute("branches")) {
					%>
					<option value="<%=branch.getBranchId()%>"><%=branch.getBranchName()%></option>
					<%
						}
					%>
				</select> <br> <input type="hidden" name="title" value="${title}">
				<br>
				<button class="btn btn-lg btn-primary btn-block" type="submit">Confirm</button>
			</form>
		</div>
		<div class="col-md-5"></div>
	</div>
	<br> <br> <br> <br>
	<h4>
		<a href="bookMenu.jsp">Back to the main menu</a>
	</h4>
</div>
