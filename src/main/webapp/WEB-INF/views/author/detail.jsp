<%@page import="com.kailiang.lms.bean.Book" %>
<%@page import="java.util.List" %>
<%
    String authorId = (String) request.getAttribute("authorId");
    String authorName = (String) request.getAttribute("authorName");
    List<Book> bookList = (List<Book>) request.getAttribute("bookList");
    List<Book> books = (List<Book>) request.getAttribute("books");
%>

<div class="container">
    <h2>Author Details</h2>
    <hr>
    <div class="row">
        <div class="col-md-4"></div>
        <div class="col-md-4">
            <form class="form-signin" action="updateAuthor">
                <input type="hidden" name="authorId" value="<%=authorId%>">
                <h5 class="form-signin-heading">Update</h5>
                <input type="text" id="inputBook" name="authorName"
                       class="form-control" value="<%=authorName%>" required autofocus>
                <br>
                <h5>
                    Books <select name="bookId" class="selectpicker"
                                  data-live-search="true" multiple>
                    <%
                        for (Book book : books) {
                    %>
                    <%
                        if (bookList.contains(book)) {
                    %>
                    <option value="<%=book.getBookId()%>" selected><%=book.getTitle()%>
                    </option>
                    <%
                    } else {
                    %>
                    <option value="<%=book.getBookId()%>"><%=book.getTitle()%>
                    </option>
                    <%
                        }
                    %>
                    <%
                        }
                    %>
                </select><br>
                    <br>
                </h5>
                <button class="btn btn-lg btn-primary btn-block" type="submit">Update</button>
            </form>
        </div>
        <div class="col-md-4"></div>
    </div>
    <br> <br> <br> <br>
    <h4>
    </h4>
</div>
<script>
    $(document).ready(function () {
        $('.selectpicker').selectpicker({

            style: 'btn-info',

            size: 4
        });
    });
</script>
