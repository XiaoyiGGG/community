package com.duanblog.controller;

import com.duanblog.dto.QuestionDto;
import com.duanblog.model.User;
import com.duanblog.service.QuestionService;
import com.duanblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private UserService userService;
    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")){
                    String token = cookie.getValue();
                    List<User> users = userService.findUserByToken(token);
                    if (users!=null&&users.size()>0){
                        User user = users.get(0);
                        request.getSession().setAttribute("user",user);
                    }
                }
            }
        }
        List<QuestionDto> questionDtoList = questionService.findAllQuestion();
        model.addAttribute("questions",questionDtoList);
        return "index";
    }
}
