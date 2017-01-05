
<%@include file="/template.html"%>




<div class="container">
	<h2>Add Author</h2>
	<div class="row">
		<div class="col-md-4"></div>
		<div class="col-md-4">
			<form class="form-signin" action="addAuthor">
				<br> <br> <br> <br> <br> <br>
				<h5 class="form-signin-heading">Add Author</h5>
				<label for="inputBook" class="sr-only">Author Name</label> <input
					type="text" id="authorName" name="authorName" class="form-control"
					placeholder="title" required autofocus> <br>
				<button class="btn btn-lg btn-primary btn-block" type="submit">Confirm</button>
			</form>
			<div class="col-md-4"></div>
		</div>
	</div>
	<br> <br> <br> <br>
	<h4>
		<a href="authorMenu.jsp">Back to the main menu</a>
	</h4>
</div>

