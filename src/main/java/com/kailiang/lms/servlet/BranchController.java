package com.kailiang.lms.servlet;

import com.google.gson.Gson;
import com.kailiang.lms.bean.Branch;
import com.kailiang.lms.service.BookCopyService;
import com.kailiang.lms.service.BookService;
import com.kailiang.lms.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
public class BranchController extends BaseController {

    @Autowired
    BranchService brService;
    @Autowired
    BookCopyService bCService;
    @Autowired
    BookService bookService;

    private static final long serialVersionUID = 1L;

    @RequestMapping("addBookCopies")
    private void addBookCopies(HttpServletRequest req, HttpServletResponse response) {
        String branchId = req.getParameter("branchId");
        String bookId = req.getParameter("bookId");
        String noOfCopies = req.getParameter("noOfCopies");
        brService.addBookCopies(bookId, branchId, noOfCopies);

        String json = new Gson().toJson("status").toString();
        print(json, response);
        //return new ModelAndView("branch/addCopySuc");
    }

    @RequestMapping("queryBranchBookCopies")
    private void queryBranchBookCopies(HttpServletRequest request, HttpServletResponse response) {
        int pageNo = 1;
        int pageSize = -1;
        String pno = request.getParameter("pageNo");
        String psize = request.getParameter("pageSize");
        String searchString = request.getParameter("title");
        String branchId = request.getParameter("branchId");
        System.out.print(branchId);
        if (pno != null) {
            pageNo = Integer.parseInt(pno);
        }
        if (psize != null) {
            pageSize = Integer.parseInt(psize);
        }
        Map<String, Object> map = brService.getBookCopiesDetails(searchString, pageNo, pageSize, branchId);
        String json = new Gson().toJson(map).toString();
        print(json, response);
    }

    @RequestMapping("copyDetail")
    private ModelAndView copyDetail(HttpServletRequest req) {
        int pageNo = 1;
        int pageSize = -1;
        String pno = req.getParameter("pageNo");
        String psize = req.getParameter("pageSize");
        String searchString = req.getParameter("title");
        String branchId = req.getParameter("branchId");

        if (pno != null) {
            pageNo = Integer.parseInt(pno);
        }
        if (psize != null) {
            pageSize = Integer.parseInt(psize);
        }
        return new ModelAndView("branch/updateCopies", brService.getBookCopiesDetails(searchString, pageNo, pageSize, branchId));
    }

    @RequestMapping("updateBranch")
    private RedirectView updateBranch(HttpServletRequest req) {
        String branchId = req.getParameter("branchId");
        String branchName = req.getParameter("branchName");
        String branchAddress = req.getParameter("branchAddress");
        String selection = req.getParameter("operator");
        brService.updateBranch(selection, branchId, branchName, branchAddress);
        brService.setAlert("success");
        req.setAttribute("selection", selection);
        return new RedirectView("/redirectBranQuery");
    }

    @RequestMapping("allBranch")
    private String allBranch(HttpServletRequest req) {
        try {

            int pageNo = Integer.parseInt(req.getParameter("pageNo"));
            brService.setPageNo(pageNo);

            List<Branch> branches = brService.getAllBranch();
            req.setAttribute("branches", branches);
            req.setAttribute("count", brService.getCount());
            req.setAttribute("pageNo", pageNo);
            req.setAttribute("pageSize", brService.getPageSize());
            return "branch/list";
        } catch (Exception e) {
            e.printStackTrace();
            return INTERNAL_SERVER_ERROR;
        }
    }

    @RequestMapping("addBranch")
    private ModelAndView addBranch(@RequestParam("branchName") String branchName, @RequestParam("branchAddress") String branchAddress) {
        if (branchName == null || branchName.trim().length() == 0) {
            return new ModelAndView("branch/branchMenu", "message", "Branch' name cannot be empty!");
        }
        brService.addBranch(branchName, branchAddress);
        return new ModelAndView("branch/addFinish");
    }

    @RequestMapping("redirectBranAdd")
    private String redirectBranAdd() {
        return "branch/add";
    }

    @RequestMapping("redirectBranQuery")
    private ModelAndView redirectBranQuery(HttpServletRequest req) {
        int pageNo = 1;
        int pageSize = -1;
        String pno = req.getParameter("pageNo");
        String psize = req.getParameter("pageSize");
        String branchName = req.getParameter("title");

        if (pno != null) {
            pageNo = Integer.parseInt(pno);
        }
        if (psize != null) {
            pageSize = Integer.parseInt(psize);
        }
        return new ModelAndView("branch/query", brService.getBranchInfo(branchName, pageNo, pageSize));
    }

    @RequestMapping("queryBranch")
    private void queryBranch(HttpServletRequest request, HttpServletResponse response) {
        int pageNo = 1;
        int pageSize = -1;
        String pno = request.getParameter("pageNo");
        String psize = request.getParameter("pageSize");
        String branchName = request.getParameter("title");
        if (pno != null) {
            pageNo = Integer.parseInt(pno);
        }
        if (psize != null) {
            pageSize = Integer.parseInt(psize);
        }
        Map<String, Object> map = brService.getBranchInfo(branchName, pageNo, pageSize);
        String json = new Gson().toJson(map).toString();
        print(json, response);
    }

    private void print(String str, HttpServletResponse resp) {
        try {
            resp.getWriter().append(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("redirectBranMenu")
    private String redirectBranMenu() {
        return "branch/branchMenu";
    }

    @RequestMapping("redirectBranDetail")
    private ModelAndView redirectBranDetail(HttpServletRequest request) {
        return new ModelAndView("branch/branchDetail", brService.getBranchDetails(request.getParameter("branchId")));
    }

}
