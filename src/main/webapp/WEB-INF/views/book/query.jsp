<%@include file="/template.html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript">
    function searchBooks(pageNo) {
        if (pageNo == "undefined") {
            pageNo = 1;
        }
        $.ajax({
            url: "queryBook",
            data: {
                title: $('#inputBook').val() || "",
                pageNo: pageNo,
                pageSize: 10,
            }
        })
            .done(function (data) {
                    if (pageNo == null || pageNo.length == 0) {
                        pageNo = 0;
                    }
                    data = JSON.parse(data);
                    var content = "";
                    content += "<table class='table'>"
                    content += "<tr><th>#</th><th>Book Title</th><th>Authors</th><th>Genres</th><th>Publisher</th><th>Edit Book</th><th>Delete Book</th></tr>"
                    for (var i = 0; i < data["books"].length; i++) {
                        var books = data["books"][i];
                        content += "<tr><td>" + (i + 1) + "</td>"
                        content += "<td>" + books.book.title + "</td>"
                        content += "<td>"
                        for (var j in books.authors) {
                            if (j != books.authors.length - 1) {
                                content += books.authors[j].authorName
                                    + ", "
                            } else {
                                content += books.authors[j].authorName
                            }
                        }
                        content += "</td>"
                        content += "<td>"
                        for (var k in books.genres) {
                            if (k != books.genres.length - 1) {
                                content += books.genres[k].genre_name
                                    + ", "
                            } else {
                                content += books.genres[k].genre_name
                            }
                        }
                        content += "</td><td>"
                            + books.publisher.publisherName
                            + "</td>"


                        content += "<td><button class = 'btn btn-success' data-toggle = 'modal' data-target = '#editBookModal'";
                        content += "onclick = 'getEditBook(" + books.book.bookId + ")'>Edit</button></td>";

                        content += "<td><a class = 'btn btn-danger' href ='deleteBook?bookId=" + books.book.bookId + "'";
                        content += " onclick='return confirm()'>Delete</a></td></tr>";
                    }
                    content += "</table>";
                    content += "<nav aria-label='Page navigation'>";
                    content += "<ul class='pagination'>";
                    content += "<li><a onclick='searchBooks(" + (data["getPrev"]) + ")' ";
                    content += "'aria-label='Previous'><span aira-hidden='true'>&laquo;</span></a></li>";
                    for (var i = 0; i < data["pages"]; i++) {
                        content += "<li><a onclick = 'searchBooks(" + (i + 1) + ")'>" + (i + 1) + "</a></li>";
                    }
                    content += "<li><a onclick='searchBooks(" + (data["getNext"]) + ")' ";
                    content += "'aria-label='Next'><span aira-hidden='true'>&raquo;</span></a></li>";
                    content += "</ul></nav>";

                    $("#resultTable").html(content);

                    console.log(data);

                    if (data.alert != null) {
                        $('#alertDiv').html(data.alert);
                        $('#alertDiv').html(data.alert).hide(2000);
                    }
                }
            );
    }

    function getEditBook(id) {
        $.ajax({
            url: "updateBook",
            data: {
                bookId: id,
            }
        }).done(function (response) {
            $(".editBookModalBody").html(response);
        });
    }

    searchBooks(1);

</script>
<div class="container">
    <h2>Find Book</h2>
    <div class="col-sm-3">
        <div id="alertDiv"></div>
        <section>
            <h5 class="form-signin-heading">Find Book By Title</h5>
            <label for="inputBook" class="sr-only">Book title</label>
            <input onkeyup="searchBooks()" type="text" id="inputBook" name="title"
                   class="form-control" placeholder="title" required autofocus>
        </section>
    </div>
    <div id="resultTable">
    </div>
    <br> <br> <br> <br>
    <h4>
        <a href="redirectBook">Back to the main menu</a>
    </h4>
</div>

<div class="modal fade bs-example-modal-lg" id="editBookModal" role="dialog">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content" style="width: 600px;">
            <div class="editBookModalBody">

            </div>
        </div>
    </div>
</div>