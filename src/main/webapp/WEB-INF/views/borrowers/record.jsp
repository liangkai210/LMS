<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<script type="text/javascript">
    function searchLoans(pageNo, cardNo) {

        $.ajax({
            url: "queryBranchBookLoans",
            data: {
                title: $('#inputBook').val(),
                pageNo: pageNo || 1,
                pageSize: 10,
                cardNo: cardNo
            }
        })
            .done(function (data) {
                    if (pageNo == null || pageNo.length == 0) {
                        pageNo = 0;
                    }
                    data = JSON.parse(data);
                    var content = "";
                    content += "<div id='alertContainer'>No alert</div>"
                    content += "<table class='table'>"
                    content += "<tr><th>#</th><th>Book Title</th><th>Branch</th><th>DateOut</th><th>DueDate</th><th>Operation</th></tr>"
                    console.log(data.loans);
                    for (var i = 0; i < data["loans"].length; i++) {
                        var loan = data["loans"][i];
                        content += "<tr><td>" + (i + 1) + "</td>"
                        content += "<td>" + loan.book.title + "</td>"
                        content += "<td>" + loan.branch.branchName + "</td>"
                        content += "<td>" + loan.dateOut + "</td>"
                        content += "<td><input type='date' id='bookLoanInput" + loan.book.bookId + "' name='dueDateOfLoan' value='" + loan.dueDate + "'></td>";

                        content += "<td><a href='javascript:updateDueDate(" + loan.book.bookId + ", " + loan.branch.branchId + ", " + data["cardNo"] + ")'>update</a></td></tr>";
                    }
                    content += "</table>"
                    content += "<nav aria-label='Page navigation'>"
                    content += "<ul class='pagination'>"
                    content += "<li><a onclick='searchLoans(" + (data["getPrev"]) + ")' "
                    content += "'aria-label='Previous'><span aira-hidden='true'>&laquo;</span></a></li>"
                    for (var i = 0; i < data["pages"]; i++) {
                        content += "<li><a onclick = 'searchLoans(" + (i + 1) + ")'>" + (i + 1) + "</a></li>"
                    }
                    content += "<li><a onclick='searchLoans(" + (data["getNext"]) + ")' "
                    content += "'aria-label='Next'><span aira-hidden='true'>&raquo;</span></a></li>"
                    content += "</ul></nav>"

                    $("#bookLoansTable").html(content);
                }
            );
    }

    function getEditBook(id) {
        $.ajax({
            url: "updateBook",
            data: {
                cardNo: id,
            }
        }).done(function (response) {
            $(".editBookModalBody").html(response);
        });
    }
</script>
<div class="container" style="width: 700px;">
    <h2>Find Book Loan</h2>
    <div class="col-sm-3">
        <section>
            <h5 class="form-signin-heading">Find Loan By Title</h5>
            <label for="inputBook" class="sr-only">Loan title</label>
            <input onkeydown="searchLoans(1, ${cardNo})" type="text" id="inputBook" name="title"
                   class="form-control" placeholder="title" required autofocus>
        </section>
    </div>
    <div id="bookLoansTable">
        <div id='alertContainer'>No alert</div>
        <table class="table">
            <tr>
                <th>#</th>
                <th>Book Title</th>
                <th>Branch</th>
                <th>DateOut</th>
                <th>DueDate</th>
                <th>Operation</th>
            </tr>
            <c:forEach var="item" items="${loans}" varStatus="status">
                <tr>
                    <td>${status.index + 1}</td>
                    <td>${item.book.title}</td>
                    <td>${item.branch.branchName}</td>
                    <td>${item.dateOut}</td>
                    <td>
                        <input type="date" id="bookLoanInput${item.book.bookId}" name="dueDate"
                               value="${item.dueDate}">
                    </td>
                    <td>
                        <a href="javascript:updateDueDate(${item.book.bookId},${item.branch.branchId}, ${cardNo})">update</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <nav aria-label="Page navigation">
            <ul class="pagination">
                <li><a href="javascript:searchLoans(${getPrev})"
                       aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
                </a></li>
                <c:forEach begin="1" end="${pages}" var="i">
                    <li><a href="javascript:searchLoans(${i},${cardNo})">${i}</a></li>
                </c:forEach>
                <li><a href="javascript:searchLoans(${getNext})"
                       aria-label="Next"> <span aria-hidden="true">&raquo;</span>
                </a></li>
            </ul>
        </nav>
    </div>
    <br> <br> <br> <br>
    <h4>
    </h4>
</div>

<div class="modal fade bs-example-modal-lg" id="editBookModal" role="dialog">
    <div class="modal-dialog modal-lg" role="document" style="width: 1000px;">
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
    function updateDueDate(bookId, branchId, cardNo) {
        var dueDate = $('#bookLoanInput' + bookId).val();
        console.log('called');
        $.ajax({
            url: "updateDate",
            data: {
                bookId: bookId,
                branchId: branchId,
                cardNo: cardNo,
                dueDate: dueDate
            }
        }).done(function (data) {
            $('#bookLoanInput' + bookId).val(dueDate);
            $('#alertContainer').html(data).hide(3000);
        })
    }
</script>
