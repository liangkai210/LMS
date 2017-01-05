package com.kailiang.lms.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kailiang.lms.service.AuthorService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AuthorController extends BaseController {

    private static final long serialVersionUID = 1L;
    @Autowired
    AuthorService authorService;

    @RequestMapping("queryAuthor")
    private void queryAuthor(HttpServletRequest req, HttpServletResponse resp) {
        int pageNo = 1;
        int pageSize = -1;
        String pno = req.getParameter("pageNo");
        String psize = req.getParameter("pageSize");
        String authorName = req.getParameter("title");
        if (pno != null) {
            pageNo = Integer.parseInt(pno);
        }
        if (psize != null) {
            pageSize = Integer.parseInt(psize);
        }
        Map<String, Object> map = authorService.getAuthorInfo(authorName, pageNo, pageSize);
        String json = new Gson().toJson(map).toString();
        print(json, resp);
    }

    private void print(String str, HttpServletResponse resp) {
        try {
            resp.getWriter().append(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("updateAuthor")
    private ModelAndView updateAuthor(@RequestParam("authorId") String authorId, @RequestParam("authorName") String authorName, @RequestParam("bookId") String[] bookId) {
        authorService.updateAuthor(authorId, authorName, bookId);
        return new ModelAndView("author/updateFinish");

    }

    @RequestMapping("deleteAuthor")
    private ModelAndView deleteAuthor(@RequestParam("authorId") String authorId) {
        authorService.deleteAuthor(authorId);
        return new ModelAndView("author/deleteFinish");
    }

    @RequestMapping("addAuthor")
    private ModelAndView addAuthor(@RequestParam("authorName") String authorName) {
        if (authorName == null || authorName.trim().length() == 0) {
            return new ModelAndView("author/authorMenu", "message", "Author' name cannot be empty!");
        }
        authorService.addAuthor(authorName);
        return new ModelAndView("author/addFinish");
    }

    @RequestMapping("redirectAddAut")
    private String redirectAdd() {
        return "author/add";
    }

    @RequestMapping("redirectAuthor")
    private String redirectAuthor() {
        return "author/authorMenu";
    }

    @RequestMapping("redirectQueryAut")
    private ModelAndView redirectQuery(HttpServletRequest req) {
        int pageNo = 1;
        int pageSize = -1;
        String pno = req.getParameter("pageNo");
        String psize = req.getParameter("pageSize");
        String authorName = req.getParameter("authorName");
        if (pno != null) {
            pageNo = Integer.parseInt(pno);
        }
        if (psize != null) {
            pageSize = Integer.parseInt(psize);
        }

        return new ModelAndView("author/query", authorService.getAuthorInfo(authorName, pageNo, pageSize));
    }

    @RequestMapping("redirectAutDetail")
    private ModelAndView redirectDetail(@RequestParam("authorId") String authorId) {

        return new ModelAndView("author/detail", authorService.getAuthorBooks(authorId));
    }

}
