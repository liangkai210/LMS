<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/template.html" %>
<script type="text/javascript">
    function searchGenres(pageNo) {
        if (pageNo == "undefined") {
            pageNo = 1;
        }
        $.ajax({
            url: "queryGenre",
            data: {
                title: $('#inputBook').val() || "",
                pageNo: pageNo,
                pageSize: 10,
                isAjax: true
            }
        }).done(function (data) {
                if (pageNo == null || pageNo.length == 0) {
                    pageNo = 0;
                }
                data = JSON.parse(data);
                var content = "";
                content += "<table class='table'>";
                content += "<tr><th>#</th><th>Genre Name</th><th>Books</th><th>Edit Genre</th><th>Delete Genre</th></tr>";
                for (var i = 0; i < data["genres"].length; i++) {
                    var genre = data["genres"][i];
                    content += "<tr><td>" + (i + 1) + "</td>";
                    content += "<td>" + genre.genre_name + "</td>";
                    content += "<td>";
                    for (var j = 0; j < genre.books.length; j++) {
                        var book = genre.books[j];
                        content += book.title;
                        if (j != genre.books.length - 1) {
                            content += ", ";
                        }
                    }
                    content += "</td>";
                    content += "<td><button class = 'btn btn-success' data-toggle = 'modal' data-target = '#editGenreModal'";
                    content += "onclick = 'getEditGenre(" + genre.genre_id + ")'>Edit</button></td>";

                    content += "<td><a class = 'btn btn-danger' href ='deleteGenre?genreId=" + genre.genre_id + "'";
                    content += " onclick='return confirm()'>Delete</a></td></tr>";
                }
                content += "</table>"
                content += "<nav aria-label='Page navigation'>"
                content += "<ul class='pagination'>"
                content += "<li><a onclick='searchGenres(" + (data["getPrev"]) + ")' "
                content += "'aria-label='Previous'><span aira-hidden='true'>&laquo;</span></a></li>"
                for (var i = 0; i < data["pages"]; i++) {
                    content += "<li><a onclick = 'searchGenres(" + (i + 1) + ")'>" + (i + 1) + "</a></li>"
                }
                content += "<li><a onclick='searchGenres(" + (data["getNext"]) + ")' "
                content += "'aria-label='Next'><span aira-hidden='true'>&raquo;</span></a></li>"
                content += "</ul></nav>"

                $('#resultTable').html(content);

                if (data.alert != null) {
                    $('#alertDiv').html(data.alert);
                    $('#alertDiv').html(data.alert).hide(2000);
                }
            }
        );

    }
    function getEditGenre(id) {
        $.ajax({
            url: "redirectGenDetail",
            data: {
                genreId: id,
            }
        }).done(function (response) {
            $(".editGenreModalBody").html(response);
        });
    }
    searchGenres(1);
</script>
<div class="container">
    <h2>Find Author</h2>
    <hr>
    <div class="row">
        <div class="col-sm-3">
            <br> <br>
            <section>
                <h5 class="form-signin-heading">Find Genre</h5>
                <label for="inputBook" class="sr-only">Genre Name</label> <input
                    onkeydown="searchGenres()" type="text" id="inputBook" name="title"
                    class="form-control" placeholder="genreName" required autofocus>
                <br>
            </section>
        </div>
        <div class="col-sm-8" id="resultTable">
        </div>
    </div>
    <br><h4>
    <a href="redirectGenre">Back to the main menu</a>
</h4>
</div>
<div class="modal fade bs-example-modal-lg" id="editGenreModal" role="dialog">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="editGenreModalBody">

            </div>
        </div>
    </div>
</div>
