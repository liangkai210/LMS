package com.kailiang.lms.servlet;

import javax.servlet.http.HttpServlet;

public abstract class BaseController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected static final String PAGE_NOT_FOUND = "404.html";
	protected static final String INTERNAL_SERVER_ERROR = "500.jsp";

//	protected String getUrl() {
//		return req.getRequestURI().substring(req.getContextPath().length(), req.getRequestURI().length());
//	}
//
//	protected static void dispatch(HttpServletRequest req, HttpServletResponse resp, String target) {
//		try {
//			req.getRequestDispatcher(target).forward(req, resp);
//		} catch (ServletException | IOException e) {
//			e.printStackTrace();
//		}
//	}
//
//	protected HttpServletRequest req;
//	protected HttpServletResponse resp;
//
//	@Override
//	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		this.req = req;
//		this.resp = resp;
//		dispatch(req, resp, execute());
//	}
//
//	@Override
//	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		doGet(req, resp);
//	}
//
//	public abstract String execute();

}
