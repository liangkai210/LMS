package com.kailiang.lms.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.kailiang.lms.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class GenreController extends BaseController {

    private static final long serialVersionUID = 1L;
    @Autowired
    GenreService genreService;

    @RequestMapping("queryGenre")
    private void queryGenre(HttpServletRequest req, HttpServletResponse resp) {
        int pageNo = 1;
        int pageSize = -1;
        String pno = req.getParameter("pageNo");
        String psize = req.getParameter("pageSize");
        String genreName = req.getParameter("title");
        if (pno != null) {
            pageNo = Integer.parseInt(pno);
        }
        if (psize != null) {
            pageSize = Integer.parseInt(psize);
        }
        Map<String, Object> map = genreService.getGenreInfo(genreName, pageNo, pageSize);


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

    @RequestMapping("updateGenre")
    private ModelAndView updateGenre(@RequestParam("genreId") String genreId, @RequestParam("genreName") String genreName, @RequestParam("bookId") String[] bookId) {
        genreService.updateGenre(genreId, genreName, bookId);
        return new ModelAndView("genre/updateFinish");

    }

    @RequestMapping("deleteGenre")
    private ModelAndView deleteGenre(@RequestParam("genreId") String genreId) {
        genreService.deleteGenre(genreId);
        return new ModelAndView("genre/deleteFinish");
    }

    @RequestMapping("addGenre")
    private ModelAndView addGenre(@RequestParam("genreName") String genreName) {
        if (genreName == null || genreName.trim().length() == 0) {
            return new ModelAndView("genre/genreMenu", "message", "Genre' name cannot be empty!");
        }
        genreService.addGenre(genreName);
        return new ModelAndView("genre/addFinish");
    }

    @RequestMapping("redirectGenDetail")
    private ModelAndView redirectDetail(@RequestParam("genreId") String genreId) {
        return new ModelAndView("genre/detail", genreService.getGenreBooks(genreId));
    }

    @RequestMapping("redirectAddGen")
    private String redirectAdd() {
        return "genre/add";
    }


    @RequestMapping("redirectGenre")
    private String redirectGenre() {
        return "genre/genreMenu";
    }

    @RequestMapping("redirectQueryGen")
    private ModelAndView redirectQuery(HttpServletRequest req) {
        int pageNo = 1;
        int pageSize = -1;
        String pno = req.getParameter("pageNo");
        String psize = req.getParameter("pageSize");
        String genreName = req.getParameter("genreName");
        if (pno != null) {
            pageNo = Integer.parseInt(pno);
        }
        if (psize != null) {
            pageSize = Integer.parseInt(psize);
        }

        return new ModelAndView("genre/query", genreService.getGenreInfo(genreName, pageNo, pageSize));
    }

}
