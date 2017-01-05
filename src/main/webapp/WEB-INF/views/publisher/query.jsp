<%@include file="/template.html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript">
    function searchPublisher(pageNo) {
        if (pageNo == "undefined") {
            pageNo = 1;
        }
        $.ajax({
            url: "queryPublisher",
            data: {
                title: $('#inputPubName').val() || "",
                pageNo: pageNo,
                pageSize: 10,
            }
        })
            .done(function (data) {
                    if (pageNo == null || pageNo.length == 0) {
                        pageNo = 0;
                    }
                    console.log(data);
                    var content = "";

                    for (var i = 0; i < data.publishers.length; i++) {
                        content += "<div>";
                        var publisher = data["publishers"][i];
                        // content += "<tr><td>" + (i + 1) + "</td>"
                        content += "<h4>" + publisher.publisherName + "</h4>";

                        content += "<button class = 'btn btn-success' data-toggle = 'modal' data-target = '#editPubModal'";
                        content += "onclick = 'getEditPub(" + publisher.publisherId + ")'>Edit</button>";
                        console.log(publisher.publisherId);
                        content += "<a class = 'btn btn-danger' href ='deletePublisher?publisherId=" + publisher.publisherId + "'";
                        content += " onclick='return confirm()'>Delete</a>";

                        content += "<h5>Books</h5>";
                        content += "<table class='table'>";
                        content += "<tr><th>#</th><th>Title</th><th>Authors</th><th>Genres</th></tr>";

                        for (var j = 0; j < publisher.books.length; j++) {
                            var book = publisher.books[j];

                            content += "<tr><td>" + (j + 1) + "</td>";
                            content += "<td>" + book.title + "</td>";
                            content += "<td>";
                            for (var k = 0; k < book.authors.length; k++) {
                                content += book.authors[k][1];
                                if (k != book.authors.length - 1) {
                                    content += ", ";
                                }
                            }
                            content += "</td>";
                            content += "<td>";
                            for (var k = 0; k < book.genres.length; k++) {
                                content += book.genres[k][1];
                                if (k != book.genres.length - 1) {
                                    content += ", ";
                                }
                            }
                            content += "</td>";
                            content += "</tr>";
                        }
                        content += "</table>";
                        content += "</div>";
                    }

                    content += "<nav aria-label='Page navigation'>"
                    content += "<ul class='pagination'>"
                    content += "<li><a onclick='searchPublisher(" + (data["getPrev"]) + ")' "
                    content += "'aria-label='Previous'><span aira-hidden='true'>&laquo;</span></a></li>"
                    for (var i = 0; i < data["pages"]; i++) {
                        content += "<li><a onclick = 'searchPublisher(" + (i + 1) + ")'>" + (i + 1) + "</a></li>"
                    }
                    content += "<li><a onclick='searchPublisher(" + (data["getNext"]) + ")' "
                    content += "'aria-label='Next'><span aira-hidden='true'>&raquo;</span></a></li>"
                    content += "</ul></nav>";

                    $('#resultTable').html(content);

                    if (data.alert != null) {
                        $('#alertDiv').html(data.alert);
                        $('#alertDiv').html(data.alert).hide(2000);
                    }

                }
            );
    }

    function getEditPub(id) {
        $.ajax({
            url: "pubDetail",
            data: {
                publisherId: id,
            }
        }).done(function (response) {
            $(".editPubModalBody").html(response);
        });
    }

    searchPublisher(1);

</script>
<div class="container">
    <h2>Find Book</h2>
    <div class="col-sm-3">
        <div id="alertDiv"></div>
        <section>
            <h5 class="form-signin-heading">Find Publisher By Name</h5>
            <label class="sr-only">Publisher Name</label>
            <input onkeyup="searchPublisher()" type="text" id="inputPubName" name="title"
                   class="form-control" placeholder="title" required autofocus>
        </section>
    </div>
</div>
<div id="resultTable">
</div>
<div class="modal fade bs-example-modal-lg" id="editPubModal" role="dialog">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="editPubModalBody">

            </div>
        </div>
    </div>
</div>