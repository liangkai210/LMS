<%@include file="/template-forModal.html" %>

<%
    String publisherId = (String) request.getAttribute("publisherId");
    String publisherName = (String) request.getAttribute("publisherName");
    String publisherAddress = (String) request.getAttribute("publisherAddress");
    String publisherPhone = (String) request.getAttribute("publisherPhone");
%>
<div class="container">
    <h2>Publisher Details</h2>
    <hr>
    <div class="row">
        <div class="col-md-4"></div>
        <div class="col-md-4">
            <form class="form-signin" action="updatePublisher">
                <input type="hidden" name="operator" value="update"> <input
                    type="hidden" name="publisherId" value="<%=publisherId%>">
                <h5 class="form-signin-heading">Update</h5>
                <input type="text" name="publisherName"
                       class="form-control" value="<%=publisherName%>" required autofocus>
                <br> <input type="text" name="publisherAddress"
                            class="form-control" value="<%=publisherAddress%>" required
                            autofocus> <br> <input type="text"
                                                   name="publisherPhone" class="form-control"
                                                   value="<%=publisherPhone%>" required autofocus>
                <br>
                <button class="btn btn-lg btn-primary btn-block" type="submit">Update</button>
            </form>
        </div>
        <div class="col-md-4"></div>
    </div>
</div>
<script>

    function updateCopies(bookId, branchId) {
        var noOfCopies = $('#bookCopyInput' + bookId).val();
        console.log('called');
        $.ajax({
            url: "addBookCopies",
            data: {
                bookId: bookId,
                branchId: branchId,
                noOfCopies: noOfCopies
            }
        }).done(function (data) {
            $('#alertContainer').show();
            $('#bookCopyInput' + bookId).val(noOfCopies);
            $('#alertContainer').html("<div class='alert alert-success' role='alert'>Update Copies Successful</div>").hide(3000);
        })
    }
</script>
