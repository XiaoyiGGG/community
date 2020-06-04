package com.duanblog.service;


import com.duanblog.dto.QuestionDto;
import com.duanblog.model.Question;

import com.github.pagehelper.PageInfo;



public interface QuestionService {

    void insertQuestion(Question question);

    PageInfo<QuestionDto> findAllQuestion(Integer page, Integer size);

    PageInfo<QuestionDto> findAllMyQuestion(Integer userId,Integer page, Integer size);

    QuestionDto findById(Integer id);

    void createOrUpdate(Question question);

    void incView(Integer id);
}
