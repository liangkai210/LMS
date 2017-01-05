package com.kailiang.lms.servlet;

import com.kailiang.lms.service.BookCopyService;
import com.kailiang.lms.service.BookLoanService;
import com.kailiang.lms.service.BookService;
import com.kailiang.lms.service.BorService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class BorrowerController extends BaseController {

    private static final long serialVersionUID = 1L;
    @Autowired
    BorService borService;
    @Autowired
    BookLoanService bLService;
    @Autowired
    BookService bookService;
    @Autowired
    BookCopyService bCService;

    @RequestMapping("returnBook")
    @ResponseBody
    private String returnFinish(HttpServletRequest request) {
        String bookId = request.getParameter("bookId");
        String branchId = request.getParameter("branchId");
        String cardNo = request.getParameter("cardNo");
        borService.returnFinish(bookId, branchId, cardNo);
        return "<div class='alert alert-success' role='alert'>Return Book Successful</div>";
    }

    @RequestMapping("checkout")
    @ResponseBody
    private String checkout(HttpServletRequest request) {
        String cardNo = request.getParameter("cardNo");
        String branchId = request.getParameter("branchId");
        String bookId = request.getParameter("bookId");
        borService.checkOut(cardNo, branchId, bookId);

        return "<div class='alert alert-success' role='alert'>Borrow Book Successful</div>";
    }

    @RequestMapping("redirectMainMenu")
    private String redirectMainMenu() {
        return "index";
    }

    @RequestMapping("redirectBorMenu")
    private String redirectBorMenu() {
        return "borrowerMenu";
    }

    @RequestMapping(value = "queryBookInBranch", produces = "application/json")
    @ResponseBody
    private String queryBookInBranch(HttpServletRequest request, HttpServletResponse response) {
        int pageNo = 1;
        int pageSize = -1;
        String pno = request.getParameter("pageNo");
        String psize = request.getParameter("pageSize");
        String cardNo = request.getParameter("cardNo");
        String branchId = request.getParameter("branchId");
        String searchString = request.getParameter("title");
        if (pno != null) {
            pageNo = Integer.parseInt(pno);
        }
        if (psize != null) {
            pageSize = Integer.parseInt(psize);
        }
        return borService.bookCopyList(branchId, cardNo, pageNo, pageSize, searchString).toString();
    }

    @RequestMapping(value = "queryLoansInBranch", produces = "application/json")
    @ResponseBody
    private String queryLoansInBranch(HttpServletRequest request, HttpServletResponse response) {
        int pageNo = 1;
        int pageSize = -1;
        String pno = request.getParameter("pageNo");
        String psize = request.getParameter("pageSize");
        String cardNo = request.getParameter("cardNo");
        String branchId = request.getParameter("branchId");
        String searchString = request.getParameter("title");
        if (pno != null) {
            pageNo = Integer.parseInt(pno);
        }
        if (psize != null) {
            pageSize = Integer.parseInt(psize);
        }
        JSONObject res = borService.bookLoansList(branchId, cardNo, pageNo, pageSize, searchString);

        System.out.println(res.toString());

        return res.toString();
    }

}
