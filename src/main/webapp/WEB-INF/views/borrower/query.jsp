<%@include file="/../template.html"%>
<%@page import="com.kailiang.lms.bean.Book"%>
<%@page import="com.kailiang.lms.bean.BookCopies"%>
<%@page import="java.util.List"%>
<%
	String cardNo = (String) request.getAttribute("cardNo");
	List<Book> list = (List<Book>) request.getAttribute("list");
%>
<script type="text/javascript">
	function searchBookCopies() {

		$.ajax({
			url : "/lms/views/borrower/queryBookCopies",
			data : {
				title : $('#inputBook').val(),
				isAjax : true
			}
		}).done(function(data) {
			console.log(data);
			$("#resultTable").html(data);
		});
	}
</script>
<div class="container">
	<h2>Find Book Copy</h2>
	<hr>
	<div class="row">
		<div class="col-sm-4">
			<br> <br>
			<section class="col-sm-8">
				<h5 class="form-signin-heading">Find Book By Title</h5>
				<label for="inputBook" class="sr-only">Book title</label> <input
					onkeyup="searchBookCopies()" type="text" id="inputBook"
					name="title" class="form-control" placeholder="title" required
					autofocus> <br>
			</section>
			<input type="hidden" name="list" value="list"> <input
				type="hidden" name="cardNo" value="cardNo">
		</div>
		<div class="col-sm-8" id="resultTable"></div>
	</div>

</div>
