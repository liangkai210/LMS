
<%@include file="/../template.html"%>
<div class="container">
<h2>Add Publisher</h2>
	<div class="row">
		<div class="col-md-4"></div>
		<div class="col-md-4">
			<form class="form-signin" action="addPublisher">
				<br> <br> <br> <br> <br> <br>
				<h5 class="form-signin-heading">Add Publisher</h5>
				<label for="inputBook" class="sr-only">Publisher Name</label> <input
					type="text" id="publisherName" name="publisherName" class="form-control"
					placeholder="publisherName" required autofocus> <br>
				<label for="inputBook" class="sr-only">Publisher Address</label> <input
					type="text" id="pulbisherAddress" name="publisherAddress" class="form-control"
					placeholder="publisherAddress" required autofocus> <br>
				<label for="inputBook" class="sr-only">Publisher Phone</label> <input
					type="text" id="publisherPhone" name="publisherPhone" class="form-control"
					placeholder="publisherphone" required autofocus> <br>
				<button class="btn btn-lg btn-primary btn-block" type="submit">Confirm</button>
			</form>
			<div class="col-md-4"></div>
		</div>
	</div>
</div>
<br>