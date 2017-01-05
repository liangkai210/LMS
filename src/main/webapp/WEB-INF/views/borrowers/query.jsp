<%@include file="/template.html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript">
    function searchBorrower(pageNo) {
        if (pageNo == "undefined") {
            pageNo = 1;
        }
        $.ajax({
            url: "queryBorrower",
            data: {
                title: $('#inputBorrowerName').val() || "",
                pageNo: pageNo,
                pageSize: 10,
            }
        })
            .done(function (data) {
                    if (pageNo == null || pageNo.length == 0) {
                        pageNo = 0;
                    }
                    var content = "";
                    content += "<table class='table'>"
                    content += "<tr><th>#</th><th>Borrower Name</th><th>Address</th><th>Phone</th><th>Loan Status</th><th>Edit Borrower</th><th>Edit Loan Status</th><th>Delete Borrower</th></tr>"
                    console.log(data);
                    for (var i = 0; i < data.borrowers.length; i++) {
                        var borrower = data.borrowers[i];
                        content += "<tr><td>" + (i + 1) + "</td>"
                        content += "<td>" + borrower.name + "</td>"
                        content += "<td>" + borrower.address + "</td>"
                        content += "<td>" + borrower.phone + "</td>"
                        if (borrower.loanStatus == false) {
                            content += "<td>No Loans</td>"
                        } else {
                            content += "<td>Has Loans</td>"
                        }
                        content += "<td><button class = 'btn btn-success' data-toggle = 'modal' data-target = '#editBorrowerModal'";
                        content += "onclick = 'getEditBorrower(" + borrower.cardNo + ")'>Edit</button></td>";

                        content += "<td><button class = 'btn btn-success' data-toggle = 'modal' data-target = '#editLoansModal'";
                        content += "onclick = 'getEditLoans(" + borrower.cardNo + ")'>Edit Loan Status</button></td>";

                        content += "<td><a class = 'btn btn-danger' href ='deleteBorrower?cardNo=" + borrower.cardNo + "'";
                        content += " onclick='return confirm()'>Delete</a></td></tr>";
                    }
                    content += "</table>"
                    content += "<nav aria-label='Page navigation'>"
                    content += "<ul class='pagination'>"
                    content += "<li><a onclick='searchBorrower(" + (data["getPrev"]) + ")' "
                    content += "'aria-label='Previous'><span aira-hidden='true'>&laquo;</span></a></li>"
                    for (var i = 0; i < data["pages"]; i++) {
                        content += "<li><a onclick = 'searchBorrower(" + (i + 1) + ")'>" + (i + 1) + "</a></li>"
                    }
                    content += "<li><a onclick='searchBorrower(" + (data["getNext"]) + ")' "
                    content += "'aria-label='Next'><span aira-hidden='true'>&raquo;</span></a></li>"
                    content += "</ul></nav>"

                    $("#resultTable").html(content);
                }
            );
    }

    function getEditLoans(id) {
        $.ajax({
            url: "showLoans",
            data: {
                cardNo: id,
            }
        }).done(function (response) {
            $(".editLoanModalBody").html(response);
        });
    }
    function getEditBorrower(id) {
        $.ajax({
            url: "redirectBorDetail",
            data: {
                cardNo: id,
            }
        }).done(function (response) {
            $(".editBorModalBody").html(response);
        });
    }

    searchBorrower(1);

</script>
<div class="container">
    <h2>Find Borrower</h2>
    <div class="col-sm-3">
        <section>
            <h5 class="form-signin-heading">Find Borrower By Name</h5>
            <label for="inputBorrowerName" class="sr-only">Borrower Name</label>
            <input onkeyup="searchBorrower()" type="text" id="inputBorrowerName" name="title"
                   class="form-control" placeholder="title" required autofocus>
        </section>
    </div>
    <div id="resultTable">
    </div>
</div>
<div class="modal fade" id="editBorrowerModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">Edit Borrower</h4>
            </div>
            <div class="modal-body editBorModalBody">
                ...
            </div>
        </div>
    </div>
</div>

<%--<div class="modal fade bs-example-modal-lg" id="editBorrowerModal" role="dialog">--%>
<%--<div class="modal-dialog modal-lg" role="document">--%>
<%--<div class="modal-content">--%>
<%--<div class="editBorModalBody">--%>

<%--</div>--%>
<%--</div>--%>
<%--</div>--%>
<%--</div>--%>
<div class="modal fade bs-example-modal-lg" id="editLoansModal" role="dialog">
    <div class="modal-dialog modal-lg" role="document" style="width: 1000px;">
        <div class="modal-content">
            <div class="editLoanModalBody">

            </div>
        </div>
    </div>
</div>