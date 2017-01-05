<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/template.html" %>
<script type="text/javascript">
    function searchAuthors(pageNo) {
        if (pageNo == "undefined") {
            pageNo = 1;
        }
        $.ajax({
            url: "queryAuthor",
            data: {
                title: $('#inputBook').val(),
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
            content += "<table class='table'>"
            content += "<tr><th>#</th><th>Author Name</th><th>Books</th><th>Edit Author</th><th>Delete Author</th></tr>"
            for (var i = 0; i < data["authorInfo"].length; i++) {
                var authors = data["authorInfo"][i];
                console.log(authors.author.authorName);
                content += "<tr><td>" + (i + 1) + "</td>"
                content += "<td>" + authors.author.authorName + "</td>"
                content += "<td>"
                for (var j in authors.books) {
                    if (j != authors.books.length - 1) {
                        content += authors.books[j].title
                            + ", "
                    } else {
                        content += authors.books[j].title
                    }
                }
                content += "</td>"
                content += "<td>"

                content += "<td><button class = 'btn btn-success' data-toggle = 'modal' data-target = '#editAuthorModal'";
                content += "onclick = 'getEditAuthor(" + authors.author.authorId + ")'>Edit</button></td>";

                content += "<td><a class = 'btn btn-danger' href ='deleteAuthor?authorId=" +authors.author.authorId +"'";
                content += " onclick='return confirm()'>Delete</a></td></tr>";
            }
            content += "</table>"
            content += "<nav aria-label='Page navigation'>"
            content += "<ul class='pagination'>"
            content += "<li><a onclick='searchAuthors(" + (data["getPrev"]) + ")' "
            content += "'aria-label='Previous'><span aira-hidden='true'>&laquo;</span></a></li>"
            for (var i = 0; i < data["pages"]; i++) {
                content += "<li><a onclick = 'searchAuthors(" + (i + 1) + ")'>" + (i + 1) + "</a></li>"
            }
            content += "<li><a onclick='searchAuthors(" + (data["getNext"]) + ")' "
            content += "'aria-label='Next'><span aira-hidden='true'>&raquo;</span></a></li>"
            content += "</ul></nav>"

            $("#resultTable").html(content);
        });
    }
    function getEditAuthor(id) {
        $.ajax({
            url: "redirectAutDetail",
            data: {
                authorId: id,
            }
        }).done(function (response) {
            $(".editAuthorModalBody").html(response);
        });
    }

</script>
<div class="container">
    <h2>Find Author</h2>
    <hr>
    <div class="row">
        <div class="col-sm-3">
            <br> <br>
            <section>
                <h5 class="form-signin-heading">Find Author</h5>
                <label for="inputBook" class="sr-only">Author Name</label> <input
                    onkeyup="searchAuthors()" type="text" id="inputBook" name="title"
                    class="form-control" placeholder="authorName" required autofocus>
                <br>
            </section>
        </div>
        <div class="col-sm-8" id="resultTable">
            <table class="table">
                <tr>
                    <th>#</th>
                    <th>Author Name</th>
                    <th>Books</th>
                    <th>Edit Author</th>
                    <th>Delete Author</th>
                </tr>
                <c:forEach var="item" items="${authorInfo}" varStatus="status">
                    <tr>
                        <td>${status.index + 1}</td>
                        <td>${item.author.authorName}</td>
                        <td><c:forEach var="book" items="${item.books}"
                                       varStatus="status">
                            ${book.title}
                            <c:if test="${not status.last}">,</c:if>
                        </c:forEach></td>
                        <td>
                            <button class="btn btn-success" data-toggle="modal" data-target="#editAuthorModal"
                                    onclick="getEditAuthor(${item.author.authorId})">Edit
                            </button>
                        </td>
                        <td><a class="btn btn-danger" href="deleteAuthor?authorId=${item.author.authorId}"
                               onclick="return confirm('Are you sure to delete this author?')">Delete</a></td>
                    </tr>
                </c:forEach>
            </table>
            <nav aria-label="Page navigation">
                <ul class="pagination">
                    <li><a href="redirectQueryAut?pageNo=${getPrev}&pageSize=10"
                           aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
                    </a></li>
                    <c:forEach begin="1" end="${pages}" var="i">
                        <li><a href="redirectQueryAut?pageNo=${i}&pageSize=10">${i}</a></li>
                    </c:forEach>
                    <li><a href="redirectQueryAut?pageNo=${getNext}&pageSize=10"
                           aria-label="Next"> <span aria-hidden="true">&raquo;</span>
                    </a></li>
                </ul>
            </nav>
        </div>
    </div>
    <br>
    <br>
    <br>
    <br><h4>
    <a href="redirectAuthor">Back to the main menu</a>
</h4>
</div>
<div class="modal fade bs-example-modal-lg" id="editAuthorModal" role="dialog">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="editAuthorModalBody">

            </div>
        </div>
    </div>
</div>
