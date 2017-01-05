<%@include file="/template.html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<style>
    @media (min-width: 1500px)
        .modal-lg {
            width: 1800px;
            height: 1200px; /* control height here */
        }

        #addCopyModal {
            width: 1500px;
            margin-top: 10px !important;
            margin-left: 10px !important;
        }

        #addCopyModal .addBookCopiesModalBody-body {
            max-height: 900px;
        }
</style>
<script type="text/javascript">
    function searchBranch(pageNo) {
        if (pageNo == "undefined") {
            pageNo = 1;
        }
        $.ajax({
            url: "queryBranch",
            data: {
                title: $('#inputBranchName').val(),
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
                    content += "<table class='table'>";
                    content += "<tr><th>#</th><th>Branch Name</th><th>Branch Address</th><th>Edit Branch</th><th>Edit Book Copy</th></tr>";
                    for (var i = 0; i < data["branches"].length; i++) {
                        console.log(data);
                        var branches = data["branches"][i];
                        console.log(branches.branchName);
                        content += "<tr><td>" + (i + 1) + "</td>";
                        content += "<td>" + branches.branchName + "</td>";
                        content += "<td>" + branches.branchAddress + "</td>";


                        content += "<td><button class = 'btn btn-success' data-toggle = 'modal' data-target = '#editBranchModal'";
                        content += "onclick = 'getEditBranch(" + branches.branchId + ")'>Edit</button></td>";

                        content += "<td><a class = 'btn btn-success' data-toggle = 'modal' data-target = '#addCopyModal'";
                        content += "onclick = 'getBookCopyDetail(" + branches.branchId + ")'>Edit Book Copy</button></td>";
                    }
                    content += "</table>";
                    content += "<nav aria-label='Page navigation'>";
                    content += "<ul class='pagination'>";
                    content += "<li><a onclick='searchBranch(" + (data["getPrev"]) + ")' ";
                    content += "'aria-label='Previous'><span aira-hidden='true'>&laquo;</span></a></li>";
                    for (var i = 0; i < data["pages"]; i++) {
                        content += "<li><a onclick = 'searchBranch(" + (i + 1) + ")'>" + (i + 1) + "</a></li>"
                    }
                    content += "<li><a onclick='searchBranch(" + (data["getNext"]) + ")' ";
                    content += "'aria-label='Next'><span aira-hidden='true'>&raquo;</span></a></li>";
                    content += "</ul></nav>"

                    console.log(data);

                    $("#resultTable").html(content);
                }
            );
    }

    function getEditBranch(id) {
        $.ajax({
            url: "redirectBranDetail",
            data: {
                branchId: id,
            }
        }).done(function (response) {
            $(".editBranchModalBody").html(response);
        });
    }
    function getBookCopyDetail(id) {
        $.ajax({
            url: "copyDetail",
            data: {
                branchId: id,
            }
        }).done(function (response) {
            $(".addBookCopiesModalBody").html(response);
        });
    }

</script>
<div class="container">
    <h2>Find Book</h2>
    <div class="col-sm-3">
        <div id="alertContainer"></div>
        <section>
            <h5 class="form-signin-heading">Find Branch By Name</h5>
            <label class="sr-only">Branch Name</label>
            <input onkeyup="searchBranch()" type="text" id="inputBranchName" name="title"
                   class="form-control" placeholder="title" required autofocus>
        </section>
    </div>
    <div id="resultTable">
        <table class="table">
            <tr>
                <th>#</th>
                <th>Branch Name</th>
                <th>Branch Address</th>
                <th>Edit</th>
                <th>Edit Book Copy</th>
            </tr>

            <c:forEach var="item" items="${branches}" varStatus="status">
                <tr>
                    <td>${status.index + 1}</td>
                    <td>${item.branchName}</td>
                    <td>${item.branchAddress}</td>
                    <td>
                        <button class="btn btn-success" data-toggle="modal" data-target="#editBranchModal"
                                onclick="getEditBranch(${item.branchId})">Edit
                        </button>
                    </td>
                    <td>
                        <button class="btn btn-success" data-toggle="modal" data-target="#addCopyModal"
                                onclick="getBookCopyDetail(${item.branchId})">Edit Book Copy
                        </button>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <nav aria-label="Page navigation">
            <ul class="pagination">
                <li><a href="redirectBranQuery?pageNo=${getPrev}&pageSize=10"
                       aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
                </a></li>
                <c:forEach begin="1" end="${pages}" var="i">
                    <li><a href="redirectBranQuery?pageNo=${i}&pageSize=10">${i}</a></li>
                </c:forEach>
                <li><a href="redirectBranQuery?pageNo=${getNext}&pageSize=10"
                       aria-label="Next"> <span aria-hidden="true">&raquo;</span>
                </a></li>
            </ul>
        </nav>
    </div>
    <br> <br> <br> <br>
    <h4>
    </h4>
</div>

<div class="modal fade bs-example-modal-lg" id="editBranchModal" role="dialog">
    <div class="modal-dialog modal-lg" role="document" style="width: 600px; height: 420px;">
        <div class="modal-content">
            <div class="editBranchModalBody">

            </div>
        </div>
    </div>
</div>
<div class="modal fade bs-example-modal-lg" id="addCopyModal" role="dialog">
    <div class="modal-dialog modal-lg" role="document" style="width: 1200px;">
        <div class="modal-content">
            <div class="addBookCopiesModalBody">

            </div>
        </div>
    </div>
</div>