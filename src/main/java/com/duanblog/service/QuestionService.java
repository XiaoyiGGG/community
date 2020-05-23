package com.duanblog.service;

import com.duanblog.dto.QuestionDto;
import com.duanblog.model.Question;

import java.util.List;

public interface QuestionService {

    void insertQuestion(Question question);

    List<QuestionDto> findAllQuestion();
}
