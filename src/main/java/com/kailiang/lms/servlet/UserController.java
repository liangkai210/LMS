package com.kailiang.lms.servlet;

import com.google.gson.Gson;
import com.kailiang.lms.bean.Borrower;
import com.kailiang.lms.bean.Branch;
import com.kailiang.lms.dao.BorrowerDao;
import com.kailiang.lms.service.BorService;
import com.kailiang.lms.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserController extends HttpServlet {

    @Autowired
    BorService borService;
    @Autowired
    BorrowerDao borDao;
    @Autowired
    BranchService branchService;

    // @Override
    // public String execute() {
    // switch (getUrl()) {
    // case "/verification":
    // return verification();
    //
    // default:
    // break;
    // }
    // return null;
    // }
    @RequestMapping("validateUserName")
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("title");
        String result = null;
        boolean flag = false;
        if (borDao.getBorrowerByName(username) != null || username.trim().length() == 0 || username == null || !username.matches("[A-Za-z0-9_]+")) {
            result = "<p>This username is unavailable!</p>";
        } else {
            flag = true;
            result = "<p>This username is available</p>";
        }
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("flag", flag);
            map.put("result", result);
            String json = new Gson().toJson(map).toString();
            response.getWriter().append(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("registerSuccess")
    private String registerBor(HttpServletRequest req) {
        String name = req.getParameter("name");
        String address = req.getParameter("address");
        String phone = req.getParameter("phone");
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if (name == null || name.trim().length() == 0) {
            req.setAttribute("message", "Borrower' name cannot be empty!");
            return "borrowers/borMenu";
        }
        if (address == null || address.trim().length() == 0) {
            req.setAttribute("message", "Borrower' address cannot be empty!");
            return "borrowers/borMenu";
        }
        if (phone == null || phone.trim().length() == 0) {
            req.setAttribute("message", "Borrower's phone cannot be empty!");
            return "borrowers/borMenu";
        }
        if (username == null || username.trim().length() == 0) {
            req.setAttribute("message", "Borrower's username cannot be empty!");
            return "borrowers/borMenu";
        }
        if (password == null || password.trim().length() == 0) {
            req.setAttribute("message", "Borrower's password cannot be empty!");
            return "borrowers/borMenu";
        }
        List<Borrower> allBors = borDao.getAll();
        for (Borrower bor : allBors) {
            if (username.equals(bor.getUsername())) {
                req.setAttribute("message", "Username is already in use");
                return "user/register";
            }
        }
        borService.addBorrower(name, address, phone, username, password);
        return "index";
    }


    @RequestMapping("register")
    private ModelAndView redirectRegister(HttpServletResponse response) {
        List<Borrower> list = borDao.getAll();
        String json = new Gson().toJson(list).toString();
        return new ModelAndView("user/register", "list", json);
    }

    @RequestMapping(value = "verification", method = RequestMethod.POST)
    private ModelAndView verification(HttpServletRequest map) {
        String type = map.getParameter("type");
        String username = map.getParameter("username");
        String password = map.getParameter("password");

        if ("admin".equals(type)) {
            if ("1234".equals(password)) {
                return new ModelAndView("user/adminMenu");
            } else {
                return new ModelAndView("wrong");
            }
        } else if ("librarian".equals(type)) {
            if ("12345".equals(password)) {
                return new ModelAndView("user/librarianMenu");
            } else {
                return new ModelAndView("wrong");
            }
        } else {
            List<Borrower> borrowers;
            try {
                borrowers = borService.getSpec("username", "password", username, password);
                if (borrowers == null || borrowers.size() == 0) {

                } else {
                    List<Branch> branches = branchService.getAllBranch();
                    Map<String, Object> borMap = new HashMap<>();
                    borMap.put("branchList", branches);
                    borMap.put("cardNo", borrowers.get(0).getCardNo());
                    return new ModelAndView("borrower/borrowerMenu", borMap);
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return new ModelAndView("wrong");
    }
}