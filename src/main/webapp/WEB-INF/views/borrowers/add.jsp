
<%@include file="/template.html"%>
<div class="container">
<h2>Add Borrower</h2>
	<div class="row">
		<div class="col-md-4"></div>
		<div class="col-md-4">
			<form class="form-signin" action="addBorrower">
				<br> 
				<h5 class="form-signin-heading">Add borrower</h5>
				<label for="inputBook" class="sr-only">Borrower Name</label> <input
					type="text" id="name" name="name" class="form-control"
					placeholder="title" required autofocus> <br>
				<label for="inputBook" class="sr-only">Borrower Address</label> <input
					type="text" id="address" name="address" class="form-control"
					placeholder="address" required autofocus> <br>
				<label for="inputBook" class="sr-only">Borrower Phone</label> <input
					type="text" id="phone" name="phone" class="form-control"
					placeholder="phone" required autofocus> <br>
				<button class="btn btn-lg btn-primary btn-block" type="submit">Confirm</button>
			</form>
			<div class="col-md-4"></div>
		</div>
	</div>
</div>
<br>
