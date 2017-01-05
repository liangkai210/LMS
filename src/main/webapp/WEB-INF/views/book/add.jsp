<%@include file="/template.html" %>
<script>

    function addBook() {
        var title = $('#title').val() || "";

        $.ajax({
            url: "validateTitle",
            data: {
                title: title
            }
        }).done(function (data) {
            if (data === "Good") {
                window.location = "/redirectToRelated?title=" + title;
            } else {
                $('#inValidAlert').html(data);
            }
        });
    }

    function redirectToAddRelated(title) {
        console.log("called");

        $.ajax({
            url: "redirectToRelated",
            data: {
                title: title
            }
        }).done(function (data) {
        });

    }

</script>
<div class="container">
    <h2>Add Book</h2>
    <div class="row">
        <div id="inValidAlert"></div>
        <div class="col-md-4"></div>
        <div class="col-md-4">

            <br>
            <h5 class="form-signin-heading">Add book</h5>
            <label class="sr-only">Book title</label> <input
                type="text" id="title" name="title" class="form-control"
                placeholder="title" required autofocus> <br>
            <button onclick="addBook()" class="btn btn-lg btn-primary btn-block" type="submit">Confirm</button>


            <div class="col-md-4"></div>
        </div>
    </div>
</div>
<br>
