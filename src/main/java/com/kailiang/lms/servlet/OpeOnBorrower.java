package com.kailiang.lms.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kailiang.lms.bean.Borrower;
import com.kailiang.lms.service.BookLoanService;
import com.kailiang.lms.service.BorService;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class OpeOnBorrower extends BaseController {

    private static final long serialVersionUID = 1L;

    @Autowired
    BorService borSerivce;
    @Autowired
    BookLoanService bLService;

    @RequestMapping("deleteBorrower")
    private ModelAndView deleteBorrower(HttpServletRequest req) {
        String cardNo = req.getParameter("cardNo");
        borSerivce.deleteBorrower(cardNo);
        return new ModelAndView("borrowers/deleteFinish");
    }

    @RequestMapping(value = "queryBorrower", produces = "application/json")
    @ResponseBody
    private String queryBorrower(HttpServletRequest req, HttpServletResponse resp) {
        int pageNo = 1;
        int pageSize = -1;
        String pno = req.getParameter("pageNo");
        String psize = req.getParameter("pageSize");
        String borName = req.getParameter("title");
        if (pno != null) {
            pageNo = Integer.parseInt(pno);
        }
        if (psize != null) {
            pageSize = Integer.parseInt(psize);
        }
        JSONObject response = borSerivce.getBorInfo(borName, pageNo, pageSize);
        return response.toString();
    }

    @RequestMapping("queryBranchBookLoans")
    private void queryBranchBookLoans(HttpServletRequest request, HttpServletResponse response) {
        int pageNo = 1;
        int pageSize = -1;
        String pno = request.getParameter("pageNo");
        String psize = request.getParameter("pageSize");
        String searchString = request.getParameter("title");
        String cardNo = request.getParameter("cardNo");
        System.out.print(cardNo);
        if (pno != null) {
            pageNo = Integer.parseInt(pno);
        }
        if (psize != null) {
            pageSize = Integer.parseInt(psize);
        }
        Map<String, Object> map = borSerivce.showBookLoans(searchString, pageNo, pageSize, cardNo);
        String json = new Gson().toJson(map).toString();
        print(json, response);
    }


    @RequestMapping("showLoans")
    private ModelAndView loanDetail(HttpServletRequest req, HttpServletResponse response) {
        int pageNo = 1;
        int pageSize = -1;
        String pno = req.getParameter("pageNo");
        String psize = req.getParameter("pageSize");
        String searchString = req.getParameter("title");
        String cardNo = req.getParameter("cardNo");

        if (pno != null) {
            pageNo = Integer.parseInt(pno);
        }
        if (psize != null) {
            pageSize = Integer.parseInt(psize);
        }
        return new ModelAndView("borrowers/record", borSerivce.showBookLoans(searchString, pageNo, pageSize, cardNo));
    }

    private void print(String str, HttpServletResponse resp) {
        try {
            resp.getWriter().append(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("updateDate")
    @ResponseBody
    private String updateDate(HttpServletRequest req) {
        String cardNo = req.getParameter("cardNo");
        String bookId = req.getParameter("bookId");
        String branchId = req.getParameter("branchId");
        String newDueDate = req.getParameter("dueDate");
        borSerivce.updateDate(cardNo, bookId, branchId, newDueDate);
        return "<div class='alert alert-success' role='alert'>Update Date Successful</div>";
    }

    // private String detail(HttpServletRequest req) {
    // String cardNo = req.getParameter("cardNo");
    // try {
    // Borrower borrower = adminService.getSingleBor("cardNo", cardNo);
    // String name = borrower.getName();
    // String address = borrower.getAddress();
    // String phone = borrower.getPhone();
    // req.setAttribute("name", name);
    // req.setAttribute("address", address);
    // req.setAttribute("phone", phone);
    // req.setAttribute("cardNo", cardNo);
    // return "borrowers/detail";
    // } catch (Exception e) {
    // e.printStackTrace();
    // return INTERNAL_SERVER_ERROR;
    // }
    // }

    @RequestMapping("updateBorrower")
    private String updateBorrower(HttpServletRequest req) {
        String cardNo = req.getParameter("cardNo");
        String name = req.getParameter("name");
        String address = req.getParameter("address");
        String phone = req.getParameter("phone");
        borSerivce.updateBorrower(cardNo, name, address, phone);
        //TODO set alert
        return "borrowers/updateFinish";
    }

    @RequestMapping("addBorrower")
    private ModelAndView addBorrower(HttpServletRequest req) {
        String name = req.getParameter("name");
        String address = req.getParameter("address");
        String phone = req.getParameter("phone");
        Borrower borrower = null;
        if (name == null || name.trim().length() == 0) {
            req.setAttribute("message", "Borrower' name cannot be empty!");
            return new ModelAndView("borrowers/borMenu");
        }
        if (address == null || address.trim().length() == 0) {
            req.setAttribute("message", "Borrower' address cannot be empty!");
            return new ModelAndView("borrowers/borMenu");
        }
        if (phone == null || phone.trim().length() == 0) {
            req.setAttribute("message", "Borrower's phone cannot be empty!");
            return new ModelAndView("borrowers/borMenu");
        }
        borSerivce.addBorrower(name, address, phone, null, null);
        return new ModelAndView("borrowers/addFinish");
    }

    @RequestMapping("redirectAddBor")
    private String redirectAddBor() {
        return "borrowers/add";
    }

    @RequestMapping("queryBor")
    private String redirectQuery(HttpServletRequest req, HttpServletResponse resp) {
        return "borrowers/query";
    }

    @RequestMapping("redirectBorMainMenu")
    private String redirectMainMenu() {
        return "borrowers/borMenu";
    }

    @RequestMapping("redirectBorDetail")
    private ModelAndView redirectDetail(HttpServletRequest req) {
        String cardNo = req.getParameter("cardNo");
        return new ModelAndView("borrowers/detail", borSerivce.getBorrowerDetails(cardNo));
    }

    @RequestMapping("redToOpeDate")
    private ModelAndView redToOpeDate(HttpServletRequest req) {
        String cardNo = req.getParameter("cardNo");
        String dateOut = req.getParameter("dateOut");
        String dueDate = req.getParameter("dueDate");
        String bookId = req.getParameter("bookId");
        String branchId = req.getParameter("branchId");
        Map<String, Object> map = new HashMap<>();
        map.put("cardNo", cardNo);
        map.put("dateOut", dateOut);
        map.put("dueDate", dueDate);
        map.put("bookId", bookId);
        map.put("branchId", branchId);
        return new ModelAndView("borrowers/operateDate", map);
    }

}
