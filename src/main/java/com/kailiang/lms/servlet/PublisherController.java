package com.kailiang.lms.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kailiang.lms.service.PublisherService;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class PublisherController extends BaseController {

    private static final long serialVersionUID = 1L;

    @Autowired
    PublisherService pubService;

    @RequestMapping("deletePublisher")
    private RedirectView deletePublisher(HttpServletRequest req) {
        String publisherId = req.getParameter("publisherId");
        pubService.deletePub(Integer.parseInt(publisherId));
        pubService.setAlert("success");
        return new RedirectView("/redirectQueryPub");
    }

    @RequestMapping(value = "queryPublisher", produces = "application/json")
    @ResponseBody
    private String queryPublisher(HttpServletRequest req, HttpServletResponse resp) {
        int pageNo = 1;
        int pageSize = -1;
        String pno = req.getParameter("pageNo");
        String psize = req.getParameter("pageSize");
        String pubName = req.getParameter("title");
        if (pno != null) {
            pageNo = Integer.parseInt(pno);
        }
        if (psize != null) {
            pageSize = Integer.parseInt(psize);
        }
        JSONObject response = pubService.getPublisherInfo(pubName, pageNo, pageSize);
        return response.toString();
    }

    @RequestMapping("pubDetail")
    private ModelAndView detail(HttpServletRequest req) {
        String publisherId = req.getParameter("publisherId");
        return new ModelAndView("publisher/detail", pubService.pubDetail(publisherId));
    }

    @RequestMapping("updatePublisher")
    private RedirectView updatePublisher(HttpServletRequest req) {

        String publisherId = req.getParameter("publisherId");
        String publisherName = req.getParameter("publisherName");
        String publisherAddress = req.getParameter("publisherAddress");
        String publisherPhone = req.getParameter("publisherPhone");

        pubService.updatePublisher(publisherId, publisherName, publisherAddress, publisherPhone);
        pubService.setAlert("success");
        return new RedirectView("/redirectQueryPub");

    }


    @RequestMapping("addPublisher")
    private ModelAndView addPublisher(HttpServletRequest req) {
        String publisherName = req.getParameter("publisherName");
        String publisherAddress = req.getParameter("publisherAddress");
        String publisherPhone = req.getParameter("publisherPhone");
        if (publisherName == null || publisherName.trim().length() == 0) {
            return new ModelAndView("publisher/publisherMenu", "message", "Publihser' name cannot be empty!");
        }
        if (publisherAddress == null || publisherAddress.trim().length() == 0) {
            return new ModelAndView("publisher/publisherMenu", "message", "Publihser' address cannot be empty!");
        }
        if (publisherPhone == null || publisherPhone.trim().length() == 0) {
            return new ModelAndView("publisher/publisherMenu", "message", "Publihser' phone cannot be empty!");
        }
        pubService.addPublisher(publisherName, publisherAddress, publisherPhone);
        return new ModelAndView("publisher/addFinish");
    }

    @RequestMapping("redirectAddPub")
    private String redirectAddPub() {
        return "publisher/add";
    }

    @RequestMapping("redirectQueryPub")
    private String redirectQueryPub() {
        return "publisher/query";
    }

    @RequestMapping("redirectPubMainMenu")
    private String redirectPubMainMenu() {
        return "publisher/publisherMenu";
    }

    @RequestMapping("redirectDetail")
    private String redirectDetail(HttpServletRequest req, HttpServletResponse resp) {
        String publisherId = req.getParameter("publisherId");
        String publisherName = req.getParameter("publisherName");
        String publisherAddress = req.getParameter("publisherAddress");
        String publisherPhone = req.getParameter("publisherPhone");
        req.setAttribute("publisherId", publisherId);
        req.setAttribute("publisherName", publisherName);
        req.setAttribute("publisherAddress", publisherAddress);
        req.setAttribute("publisherPhone", publisherPhone);
        return "publisher/detail";

    }
}
