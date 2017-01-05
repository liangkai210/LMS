<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="/template.html" %>
<script>

    function fillTables() {
        queryBookInBranch(1);
        queryLoansInBranch(1);
    }

    function queryBookInBranch(pageNo) {
        if (pageNo == "undefined") {
            pageNo = 1;
        }
        var cardNo = $('#cardNo').val();
        var branchId = $('option:selected').val();
        $.ajax({
            url: "queryBookInBranch",
            data: {
                cardNo: cardNo,
                branchId: branchId,
                pageNo: pageNo,
                pageSize: 10
            }
        }).done(function (data) {
            if (pageNo == null || pageNo.length == 0) {
                pageNo = 0;
            }
            var content = "";
            content += "<table class='table'>"
            content += "<tr><th>#</th><th>Book Title</th><th>Authors</th><th>Genres</th><th>Publisher</th><th>Borrow Book</th></tr>"
            for (var i = 0; i < data.books.length; i++) {
                var book = data.books[i];
                content += "<tr><td>" + (i + 1) + "</td>"
                content += "<td>" + book.title + "</td>"
                content += "<td>"
                for (var j = 0; j < book.authors.length; j++) {
                    console.log(book.authors[j]);
                    content += book.authors[j].authorName;
                    if (j != book.genres.length - 1) {
                        content += ", ";
                    }
                }
                content += "</td>"
                content += "<td>"
                for (var k = 0; k < book.genres.length; k++) {
                    content += book.genres[k].genreName;
                    if (k != book.genres.length - 1) {
                        content += ", ";
                    }
                }
                content += "</td><td>"
                    + book.publisher.publisherName
                    + "</td>"


                content += "<td><button class = 'btn btn-success'";
                content += "onclick = 'borrowBook(" + book.bookId + ", " + branchId + ", " + cardNo + ")'>Borrow</button></td>";

            }
            content += "</table>"
            content += "<nav aria-label='Page navigation'>"
            content += "<ul class='pagination'>"
            content += "<li><a onclick='queryBookInBranch(" + (data["getPrev"]) + ")' "
            content += "'aria-label='Previous'><span aira-hidden='true'>&laquo;</span></a></li>"
            for (var i = 0; i < data["pages"]; i++) {
                content += "<li><a onclick = 'queryBookInBranch(" + (i + 1) + ")'>" + (i + 1) + "</a></li>"
            }
            content += "<li><a onclick='queryBookInBranch(" + (data["getNext"]) + ")' "
            content += "'aria-label='Next'><span aira-hidden='true'>&raquo;</span></a></li>"
            content += "</ul></nav>"
            $("#bookTable").html(content);
        })
    }

    function borrowBook(bookId, branchId, cardNo) {
        $.ajax({
            url: "checkout",
            data: {
                bookId: bookId,
                branchId: branchId,
                cardNo: cardNo
            }
        }).done(function (response) {
            $("#alertSection").html(response).show();
            $("#alertSection").html(response).hide(2000);
            fillTables();
        });
    }


    function queryLoansInBranch(pageNo) {
        var cardNo = $('#cardNo').val();
        var branchId = $('option:selected').val();

        if (pageNo == "undefined") {
            pageNo = 1;
        }
        $.ajax({
            url: "queryLoansInBranch",
            data: {
                pageNo: pageNo,
                branchId: branchId,
                cardNo: cardNo,
                pageSize: 10
            }
        }).done(function (data) {
            if (pageNo == null || pageNo.length == 0) {
                pageNo = 0;
            }

            var content = "";
            content += "<table class='table'>"
            content += "<tr><th>#</th><th>Book Title</th><th>DateOut</th><th>DueDate</th><th>Return Book</th></tr>";
            console.log(data);
            for (var i = 0; i < data.bookLoans.length; i++) {
                var book = data.bookLoans[i];
                content += "<tr><td>" + (i + 1) + "</td>";
                content += "<td>" + book.title + "</td>";
                content += "<td>" + book.dateOut + "</td>";
                content += "<td>" + book.dueDate + "</td>";

                content += "<td><button class = 'btn btn-success'";
                content += "onclick = 'returnBook(" + book.bookId + ", " + branchId + ", " + cardNo + ")'>Return</button></td>";

            }
            content += "</table>"
            content += "<nav aria-label='Page navigation'>"
            content += "<ul class='pagination'>"
            content += "<li><a onclick='queryLoansInBranch(" + (data["getPrev"]) + ")' "
            content += "'aria-label='Previous'><span aira-hidden='true'>&laquo;</span></a></li>"
            for (var i = 0; i < data["pages"]; i++) {
                content += "<li><a onclick = 'queryLoansInBranch(" + (i + 1) + ")'>" + (i + 1) + "</a></li>"
            }
            content += "<li><a onclick='queryLoansInBranch(" + (data["getNext"]) + ")' "
            content += "'aria-label='Next'><span aira-hidden='true'>&raquo;</span></a></li>"
            content += "</ul></nav>"
            $("#loanTable").html(content);
        })
    }

    function returnBook(bookId, branchId, cardNo) {
        $.ajax({
            url: "returnBook",
            data: {
                bookId: bookId,
                branchId: branchId,
                cardNo: cardNo
            }
        }).done(function (response) {
            $("#alertSection").html(response).show();
            $("#alertSection").html(response).hide(2000);

            fillTables();
        });
    }

    $(document).ready(function () {
        fillTables();
    });


</script>
<h2>Borrower Menu</h2>
<hr>
<div class="panel panel-default">
    <div class="panel-heading">
        <h3 class="panel-title">Branches</h3>
    </div>
    <div class="panel-body">
        <input type="hidden" value="${cardNo}" id="cardNo"/>
        <select onchange="fillTables()" name="branchId" class="selectpicker" data-live-search="true">
            <c:forEach var="item" items="${branchList}" varStatus="status">
                <option value="${item.getBranchId()}">${item.getBranchName()}</option>
            </c:forEach>
        </select>
    </div>
</div>

<div class="panel panel-default">
    <div class="panel-heading">
    </div>
    <div class="panel-body">
        <div class="row">
            <div id="alertSection"></div>
            <h2 class="panel-title">Available Books</h2>
            <div id="bookTable" class="col-md-6"></div>
            <h2 class="panel-title">Loans</h2>
            <div id="loanTable" class="col-md-6"></div>
        </div>
    </div>
</div>
