package com.kailiang.lms.servlet;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.kailiang.lms.service.BookService;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class BookController extends BaseController {

    private static final long serialVersionUID = 1L;

    @Autowired
    BookService bookService;

    @RequestMapping(value = "storeBookInDB")
    private RedirectView storeBookInDB(@RequestParam("title") String title,
                                       @RequestParam("publisherId") String publisherId, @RequestParam("authorId") String[] authorIds,
                                       @RequestParam("genre_id") String[] genreIds, @RequestParam("branchId") String[] branchIds) {
        bookService.saveBookInDB(title, publisherId, authorIds, genreIds, branchIds);
        return new RedirectView("/redirectBook");
    }

    @RequestMapping("redirectQuery")
    private ModelAndView redirectQuery(HttpServletRequest req) {
        return new ModelAndView("book/query");
    }

    @RequestMapping(value = "redirectBook", method = RequestMethod.GET)
    private ModelAndView redirectBook() {
        return new ModelAndView("book/bookMenu");
    }

    @RequestMapping(value = "redirectAdd", method = RequestMethod.GET)
    private ModelAndView redirectAdd() {
        return new ModelAndView("book/add");
    }

    @RequestMapping("queryBook")
    private void queryBook(HttpServletRequest req, HttpServletResponse resp) {
        int pageNo = 1;
        int pageSize = -1;
        String pno = req.getParameter("pageNo");
        String psize = req.getParameter("pageSize");
        String title = req.getParameter("title");
        if (pno != null) {
            pageNo = Integer.parseInt(pno);
        }
        if (psize != null) {
            pageSize = Integer.parseInt(psize);
        }
        Map<String, Object> map = bookService.getBookInfo(title, pageNo, pageSize);
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

    @RequestMapping(value = "addBook", method = RequestMethod.GET)
    private ModelAndView showAdd() {
        return new ModelAndView("book/add");
    }

    @RequestMapping(value = "validateTitle", method = RequestMethod.GET)
    @ResponseBody
    private String addBook(@RequestParam("title") String title) {
        if (title == null || title.trim().length() == 0 || !title.matches("[A-Za-z0-9_\\s]+")) {
            return "<div class='alert alert-danger' role='alert'>Title Input Invalid</div>";
        }

        return "Good";
    }

    @RequestMapping(value = "redirectToRelated", method = RequestMethod.GET)
    private ModelAndView redirectToRelated(@RequestParam("title") String title) {
        System.out.print("called");
        return new ModelAndView("book/addRelated", bookService.addBook(title));
    }

    @RequestMapping("saveBook")
    private RedirectView saveBook(@RequestParam("bookTitle") String title, @RequestParam("bookId") String bookId,
                                  @RequestParam("publisherId") String publisherId, @RequestParam("authorId") String[] authorIds,
                                  @RequestParam("genre_id") String[] genreIds) {
        bookService.saveBook(title, bookId, publisherId, authorIds, genreIds);
        bookService.setAlert("success");
        return new RedirectView("/redirectQuery");
    }

    @RequestMapping("updateBook")
    private ModelAndView updateBook(HttpServletRequest request) {
        String bookId = request.getParameter("bookId");
        bookService.setHasOperated(true);
        return new ModelAndView("book/detail", bookService.updateBook(bookId));
    }

    @RequestMapping("deleteBook")
    private RedirectView deleteBook(HttpServletRequest request) {
        String bookId = request.getParameter("bookId");
        bookService.deleteBook(bookId);
        bookService.setHasOperated(true);
        return new RedirectView("/redirectQuery");
    }
}
