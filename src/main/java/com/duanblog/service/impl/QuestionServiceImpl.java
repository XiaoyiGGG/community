package com.duanblog.service.impl;

import com.duanblog.dto.QuestionDto;
import com.duanblog.mapper.QuestionMapper;
import com.duanblog.mapper.UserMapper;
import com.duanblog.model.Question;
import com.duanblog.model.QuestionExample;
import com.duanblog.model.User;
import com.duanblog.service.QuestionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public void insertQuestion(Question question) {
        questionMapper.insert(question);
    }

    @Override
    public List<QuestionDto> findAllQuestion() {
        List<Question> questions = questionMapper.selectByExample(new QuestionExample());
        List<QuestionDto> questionDtos = new ArrayList<>();
        for (Question question : questions) {
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(question,questionDto);
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            questionDto.setUser(user);
            questionDtos.add(questionDto);
        }
        return questionDtos;
    }
}
