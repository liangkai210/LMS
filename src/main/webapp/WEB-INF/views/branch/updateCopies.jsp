<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<script type="text/javascript">
    function searchBooks(pageNo, branchId) {

        $.ajax({
            url: "queryBranchBookCopies",
            data: {
                title: $('#inputBook').val(),
                pageNo: pageNo || 1,
                pageSize: 10,
                branchId: branchId
            }
        })
            .done(function (data) {
                    if (pageNo == null || pageNo.length == 0) {
                        pageNo = 0;
                    }
                    data = JSON.parse(data);
                    var content = "";
                    content += "<div id='alertModalContainer'>No alert</div>"
                    content += "<table class='table'>"
                    content += "<tr><th>#</th><th>Book Title</th><th>Authors</th><th>Genres</th><th>Publisher</th><th>Book Copies</th><th>Edit Copies</th></tr>"
                    for (var i = 0; i < data["bookInfoList"].length; i++) {
                        var books = data["bookInfoList"][i];
                        console.log(data["bookInfoList"]);
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

                        content += "<td><input type='text' id='bookCopyInput" + books.book.bookId + "' name='noOfCopies' value='" + books.noOfCopies + "'></td>";

                        content += "<td><a href='javascript:updateCopies(" + books.book.bookId + ", " + data["branchId"] + ")'>update</a></td></tr>";
                    }
                    content += "</table>"
                    content += "<nav aria-label='Page navigation'>"
                    content += "<ul class='pagination'>"
                    content += "<li><a onclick='searchBooks(" + (data["getPrev"]) + ")' "
                    content += "'aria-label='Previous'><span aira-hidden='true'>&laquo;</span></a></li>"
                    for (var i = 0; i < data["pages"]; i++) {
                        content += "<li><a onclick = 'searchBooks(" + (i + 1) + ")'>" + (i + 1) + "</a></li>"
                    }
                    content += "<li><a onclick='searchBooks(" + (data["getNext"]) + ")' "
                    content += "'aria-label='Next'><span aira-hidden='true'>&raquo;</span></a></li>"
                    content += "</ul></nav>"

                    $("#bookCopyTable").html(content);
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


</script>
<div class="container">
    <h2>Find Book</h2>
    <div class="col-sm-3">
        <section>
            <h5 class="form-signin-heading">Find Book By Title</h5>
            <label for="inputBook" class="sr-only">Book title</label>
            <input onkeydown="searchBooks(1, ${branchId})" type="text" id="inputBook" name="title"
                   class="form-control" placeholder="title" required autofocus>
        </section>
    </div>
    <div id="bookCopyTable">
        <div id='alertModalContainer'>No alert</div>
        <table class="table">
            <tr>
                <th>#</th>
                <th>Book Title</th>
                <th>Authors</th>
                <th>Genres</th>
                <th>Publisher</th>
                <th>Book Copies</th>
                <th>Edit Book Copy</th>
            </tr>
            <c:forEach var="item" items="${bookInfoList}" varStatus="status">
                <tr>
                    <td>${status.index + 1}</td>
                    <td>${item.book.title}</td>
                    <td><c:forEach var="author" items="${item.authors}"
                                   varStatus="status">
                        ${author.authorName}
                        <c:if test="${not status.last}">,</c:if>
                    </c:forEach></td>
                    <td><c:forEach var="genre" items="${item.genres}"
                                   varStatus="status">
                        ${genre.genre_name}
                        <c:if test="${not status.last}">,</c:if>
                    </c:forEach></td>
                    <td>${item.publisher.publisherName }</td>
                    <td>
                        <input type="text" id="bookCopyInput${item.book.bookId}" name="noOfCopies"
                               value="${item.noOfCopies}">
                    </td>
                    <td><a href="javascript:updateCopies(${item.book.bookId}, ${branchId})">update</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <nav aria-label="Page navigation">
            <ul class="pagination">
                <li><a href="javascript:searchBooks(${getPrev})"
                       aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
                </a></li>
                <c:forEach begin="1" end="${pages}" var="i">
                    <li><a href="javascript:searchBooks(${i})">${i}</a></li>
                </c:forEach>
                <li><a href="javascript:searchBooks(${getNext})"
                       aria-label="Next"> <span aria-hidden="true">&raquo;</span>
                </a></li>
            </ul>
        </nav>
    </div>
    <br> <br> <br> <br>
    <h4>
    </h4>
</div>

<div class="modal fade bs-example-modal-md" id="editBookModal" role="dialog">
    <div class="modal-dialog modal-md" role="document">
        <div class="modal-content">
            <div class="editBookModalBody">

            </div>
        </div>
    </div>
</div>
<script>
    function findBookCopy(bookId) {
        return document.getElementById().value;
    }
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
            $('#alertModalContainer').show();
            $('#bookCopyInput' + bookId).val(noOfCopies);
            $('#alertModalContainer').html("<div class='alert alert-success' role='alert'>Update Copies Successful</div>").hide(3000);
        })
    }
</script>
