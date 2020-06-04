package com.duanblog.controller;

import com.duanblog.dto.QuestionDto;
import com.duanblog.service.QuestionService;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


import javax.servlet.http.HttpServletRequest;


@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index( @RequestParam(value = "page",defaultValue = "1") Integer page,
                         @RequestParam(value = "size",defaultValue = "5") Integer size,
                         HttpServletRequest request,
                         Model model) {

        PageInfo<QuestionDto> pagination = questionService.findAllQuestion(page, size);
        model.addAttribute("pagination", pagination);
        //System.out.println(pagination.getList());
        return "index";
    }
}
