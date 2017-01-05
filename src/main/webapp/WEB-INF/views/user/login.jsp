<%-- 
<%@page import="com.kailiang.lms.bean.Borrower"%>
<%@page import="java.util.List"%>
<%@page import="com.kailiang.lms.dao.BorrowerDao"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<%
	String type = request.getParameter("type");
	String username = request.getParameter("username");
	String password = request.getParameter("password");

	if ("admin".equals(type)) {
		if ("1234".equals(password)) {
			response.sendRedirect(basePath + "pages/user/adminMenu.jsp");
		} else {
			response.sendRedirect(basePath + "index.jsp?message=Invalid_Input");
		}
	} else if ("librarian".equals(type)) {
		if ("12345".equals(password)) {
			response.sendRedirect(basePath + "pages/user/librarian.jsp");
		} else {
			response.sendRedirect(basePath + "index.jsp?message=Invalid_Input");
		}
	} /* else {
		BorrowerDao bd = new BorrowerDao();
		List<Borrower> borrowers = bd.get("cardNo", "name", Integer.parseInt(password), username);
		if (borrowers == null || borrowers.size() == 0) {
			response.sendRedirect(basePath + "index.jsp?message=Invalid_Input");
		} else {
			session.setAttribute("user", borrowers.get(0));
			response.sendRedirect(basePath + "pages/borrower/index.jsp");
		} */
	} 
%> --%>