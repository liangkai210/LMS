<%@include file="/template.html" %>
<div class="container">
    <h2>Library Management System</h2>
    <hr>
    <div class="row">
        <div class="col-md-4">
            <h5>
                <%
                    String mes = request.getParameter("message");
                    if (mes != null && mes.length() > 0) {
                %>
                <%=mes%>
                <%
                    }
                %>
            </h5>
        </div>

        <div class="col-md-4">
            <form class="form-signin" action="verification" method="post">
                <h5 class="form-signin-heading">Please sign in</h5>
                <select name="type">
                    <option value="admin">Administrator</option>
                    <option value="librarian">Librarian</option>
                    <option value="borrower">Borrower</option>
                </select> <br> <label for="inputEmail" class="sr-only">user name</label>
                <br> <input name="username" type="text" id="inputEmail"
                            class="form-control" placeholder="User Name" required autofocus>
                <br> <label for="inputPassword" class="sr-only">Password</label>
                <input name="password" type="password" id="inputPassword"
                       class="form-control" placeholder="Password" required>
                <div class="checkbox">
                    <label> <input type="checkbox" value="remember-me">
                        Remember me
                    </label>
                </div>
                <button class="btn btn-lg btn-primary btn-block" type="submit">Sign
                    in
                </button>
                <a href="register" class="btn btn-lg btn-primary btn-block">Register</a>
            </form>
        </div>
        <div class="col-md-4"></div>
    </div>
</div>
