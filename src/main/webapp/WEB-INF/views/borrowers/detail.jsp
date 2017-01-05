<%@include file="/template-forModal.html" %>
<%
    String cardNo = (String) request.getAttribute("cardNo");
    String name = (String) request.getAttribute("name");
    String address = (String) request.getAttribute("address");
    String phone = (String) request.getAttribute("phone");
%>
<%--<div class="container">--%>
    <%--<h2>Borrower Details</h2>--%>
    <%--<div class="row">--%>
        <%--<div class="col-md-4"></div>--%>
        <%--<div class="col-md-4" >--%>
            <form class="form-signin" action="updateBorrower">
                <input type="hidden" name="operator" value="update"> <input
                    type="hidden" name="cardNo" value="<%=cardNo%>">
                <h5 class="form-signin-heading">Update</h5>
                <input type="text" id="inputName" name="name"
                       class="form-control" value="<%=name%>" required autofocus>
                <br> <input type="text" id="inputAddress" name="address"
                            class="form-control" value="<%=address%>" required
                            autofocus> <br> <input type="text" id="inputPhone"
                                                   name="phone" class="form-control"
                                                   value="<%=phone%>" required autofocus>
                <button class="btn btn-lg btn-primary btn-block" type="submit">Update</button>
            </form>
        <%--</div>--%>
        <%--<div class="col-md-4"></div>--%>
    <%--</div>--%>
<%--</div>--%>
