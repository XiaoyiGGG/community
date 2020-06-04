package com.duanblog.controller;

import com.duanblog.dto.QuestionDto;
import com.duanblog.model.Question;
import com.duanblog.model.User;
import com.duanblog.service.QuestionService;
import com.duanblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/publish")
    public String publish(){

        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam(value = "title",required = false) String title,
            @RequestParam(value = "description",required = false) String description,
            @RequestParam(value = "tag",required = false) String tag,
            @RequestParam(value = "id", required = false) Integer id,
            HttpServletRequest request,
            Model model){
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);
        if (title == null || title == "") {
            model.addAttribute("error", "标题不能为空");
            return "publish";
        }
        if (description == null || description == "") {
            model.addAttribute("error", "问题补充不能为空");
            return "publish";
        }
        if (tag == null || tag == "") {
            model.addAttribute("error", "标签不能为空");
            return "publish";
        }

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            model.addAttribute("error", "用户未登录");
            return "publish";
        }

        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setId(id);
        questionService.createOrUpdate(question);
        return "redirect:/";
    }

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Integer id,
                           Model model) {
        QuestionDto questionDTO = questionService.findById(id);
        questionService.incView(id);//增加阅读数
        model.addAttribute("question", questionDTO);
        return "question";
    }

    @GetMapping("/publish/{id}")
    public String editQuestion(@PathVariable(name = "id") Integer id,
                               Model model){
        QuestionDto questionDto = questionService.findById(id);
        model.addAttribute("title",questionDto.getTitle());
        model.addAttribute("description",questionDto.getDescription());
        model.addAttribute("tag", questionDto.getTag());
        model.addAttribute("id", questionDto.getId());
        return "publish";
    }

}
