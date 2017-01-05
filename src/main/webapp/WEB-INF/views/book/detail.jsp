<%@page import="com.kailiang.lms.bean.Author" %>
<%@page import="com.kailiang.lms.bean.Genre" %>
<%@page import="com.kailiang.lms.bean.Publisher" %>
<%@page import="java.util.List" %>
<%
    String id = request.getParameter("bookId");
    Publisher publisher = (Publisher) request.getAttribute("publisher");
    String bookTitle = (String) request.getAttribute("bookTitle");
    Integer publisherId = (Integer) request.getAttribute("publisherId");
    String publisherName = (String) request.getAttribute("publisherName");
    List<Author> authorList = (List<Author>) request.getAttribute("authorList");
    List<Genre> genreList = (List<Genre>) request.getAttribute("genreList");
    List<Author> authors = (List<Author>) request.getAttribute("authors");
    List<Genre> genres = (List<Genre>) request.getAttribute("genres");
    List<Publisher> publishers = (List<Publisher>) request.getAttribute("publishers");
%>

<div>
    <h2>Book Details</h2>
    <hr>
    <div class="row">
        <div class="col-md-4"></div>
        <div class="col-md-4">
            <form class="form-signin" action="saveBook">
                <input type="hidden" name="bookId" value="<%=id%>">
                <h5 class="form-signin-heading">Update</h5>
                <input type="text" id="inputBook" name="bookTitle"
                       class="form-control" value="<%=bookTitle%>" required autofocus>
                <br>
                <h5>
                    Publisher <select name="publisherId" class="selectpicker"
                                      data-live-search="true">
                    <%
                        for (Publisher pub : publishers) {
                    %>
                    <%
                        if (pub.getPublisherId() == publisher.getPublisherId()) {
                    %>
                    <option value="<%=publisherId%>" selected><%=publisherName%>
                    </option>
                    <%
                    } else {
                    %>
                    <option value="<%=pub.getPublisherId()%>"><%=pub.getPublisherName()%>
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
                <h5>
                    Authors <select name="authorId" class="selectpicker"
                                    data-live-search="true" multiple>
                    <%
                        for (Author author : authors) {
                    %>
                    <%
                        if (authorList.contains(author)) {
                    %>
                    <option value="<%=author.getAuthorId()%>" selected><%=author.getAuthorName()%>
                    </option>
                    <%
                    } else {
                    %>
                    <option value="<%=author.getAuthorId()%>"><%=author.getAuthorName()%>
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
                <h5>
                    Genres <select name="genre_id" class="selectpicker"
                                   data-live-search="true" multiple>
                    <%
                        for (Genre genre : genres) {
                    %>
                    <%
                        if (genreList.contains(genre)) {
                    %>
                    <option value="<%=genre.getGenre_id()%>" selected><%=genre.getGenre_name()%>
                    </option>
                    <%
                    } else {
                    %>
                    <option value="<%=genre.getGenre_id()%>"><%=genre.getGenre_name()%>
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
