<%@ page import="com.kailiang.lms.bean.Branch" %>
<%@include file="/template-forModal.html" %>
<%
    Branch branch = (Branch) request.getAttribute("branch");
%>

<div class="container">
    <hr>
    <div class="row">
        <div class="col-md-6">
            <h3>Branch Details</h3>

            <form class="form-signin" action="updateBranch">
                <input type="hidden" name="operator" value="update"> <input
                    type="hidden" name="branchId" value="<%=branch.getBranchId()%>">
                <h5 class="form-signin-heading">Update</h5>
                <input type="text" name="branchName" class="form-control"
                       value="<%=branch.getBranchName()%>" required autofocus> <br> <input
                    type="text" name="branchAddress" class="form-control"
                    value="<%=branch.getBranchAddress()%>" required autofocus> <br>
                <button class="btn btn-lg btn-primary btn-block" type="submit">Update</button>
            </form>
            <form class="form-signin" action="deleteBranch">
                <input type="hidden" name="operator" value="delete"> <input
                    type="hidden" name="branchId" value="<%=branch.getBranchId()%>">
                <h5 class="form-signin-heading">Delete</h5>
                <input type="text" id="branchId" name="branchName"
                       class="form-control" value="<%=branch.getBranchName()%>" required autofocus
                       readonly="readonly"> <br>
                <button class="btn btn-lg btn-primary btn-block" type="submit">Delete</button>
            </form>
        </div>
    </div>
</div>

