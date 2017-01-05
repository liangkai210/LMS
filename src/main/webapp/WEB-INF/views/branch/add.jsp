
<%@include file="/template.html"%>
<div class="container">
	<h2>Add Branch</h2>
	<hr>
	<div class="row">
		<div class="col-md-4"></div>
		<div class="col-md-4">
			<form class="form-signin" action="addBranch">
				<h5 class="form-signin-heading">Add Branch</h5>
				<label class="sr-only">Branch Name</label> <input
					type="text" id="inputBrName" name="branchName" class="form-control"
					placeholder="branchName" required autofocus> <br> <label
					class="sr-only">Branch Address</label> <input
					type="text" id="inputBrAddress" name="branchAddress"
					class="form-control" placeholder="branchAddress" required autofocus>
				<br>
				<button class="btn btn-lg btn-primary btn-block" type="submit">Confirm</button>
			</form>
		</div>
		<div class="col-md-4"></div>
	</div>
</div>
