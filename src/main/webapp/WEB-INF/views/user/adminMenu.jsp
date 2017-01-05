<%@include file="/template.html" %>
<%-- <%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%> --%>

<div class="container">
    <h2>Administrator Menu</h2>
    <hr>
    <div class="row">
        <div class="col-md-4"></div>
        <div class="col-md-4">

            <div class="list-group-item list-group-item-success">
                <a href="redirectBook" class="list-group-item"> Operations on
                    Books</a> <br> <a href="redirectPubMainMenu"
                                      class="list-group-item"> Operations on Publishers</a> <br> <a
                    href="redirectBorMainMenu" class="list-group-item"> Operations
                on Borrowers</a> <br> <a href="redirectAuthor"
                                         class="list-group-item"> Operations on Authors</a> <br> <a
                    href="redirectBranMenu" class="list-group-item"> Operations on
                Branches</a> <br> <a href="redirectGenre"
                                     class="list-group-item"> Operations on Genres</a>
            </div>

        </div>
        <div class="col-md-4"></div>
    </div>
</div>
