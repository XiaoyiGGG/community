package com.duanblog.service.impl;


import com.duanblog.dto.QuestionDto;
import com.duanblog.exception.CustomizeErrorCode;
import com.duanblog.exception.CustomizeException;
import com.duanblog.mapper.QuestionMapper;
import com.duanblog.mapper.UserMapper;
import com.duanblog.model.Question;
import com.duanblog.model.QuestionExample;
import com.duanblog.model.User;
import com.duanblog.service.QuestionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
    public PageInfo<QuestionDto> findAllQuestion(Integer page, Integer size) {
        /*QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria();*/
        PageHelper.startPage(page,size);
        List<Question> questions = questionMapper.selectAll();
        //System.out.println(questions);
        PageInfo<Question> questionPageInfo = new PageInfo<>(questions);
        List<QuestionDto> questionDtoList = new ArrayList<>();
        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDto questionDTO = new QuestionDto();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDtoList.add(questionDTO);
        }
        PageInfo<QuestionDto> questionDtoPageInfo = new PageInfo<>();
        BeanUtils.copyProperties(questionPageInfo,questionDtoPageInfo);
        questionDtoPageInfo.setList(questionDtoList);
        return questionDtoPageInfo;
    }


    @Override
    public PageInfo<QuestionDto> findAllMyQuestion(Integer userId,Integer page, Integer size) {
        QuestionExample questionExample = new QuestionExample();
        QuestionExample.Criteria criteria = questionExample.createCriteria();
        criteria.andCreatorEqualTo(userId);
        PageHelper.startPage(page,size);
        List<Question> questions = questionMapper.selectByExample(questionExample);
        PageInfo<Question> questionPageInfo = new PageInfo<>(questions);
        //System.out.println(questionPageInfo);
        List<QuestionDto> questionDTOList = new ArrayList<>();
        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDto questionDTO = new QuestionDto();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        PageInfo<QuestionDto> questionDtoPageInfo = new PageInfo<QuestionDto>();
        BeanUtils.copyProperties(questionPageInfo,questionDtoPageInfo);
        questionDtoPageInfo.setList(questionDTOList);
        return questionDtoPageInfo;
    }

    @Override
    public QuestionDto findById(Integer id) {

        Question question = questionMapper.selectByPrimaryKey(id);
        if (question == null) {
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        //System.out.println(question);
        QuestionDto questionDto = new QuestionDto();
        BeanUtils.copyProperties(question,questionDto);
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        questionDto.setUser(user);
        return questionDto;
    }

    @Override
    public void createOrUpdate(Question question) {
        if (question.getId() == null) {
            // 创建
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            question.setViewCount(0);
            question.setLikeCount(0);
            question.setCommentCount(0);
            questionMapper.insert(question);
        } else {
            // 更新
            Question dbQuestion = questionMapper.selectByPrimaryKey(question.getId());

            if (dbQuestion == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }

            if (dbQuestion.getCreator().longValue() != question.getCreator().longValue()) {
                throw new CustomizeException(CustomizeErrorCode.INVALID_OPERATION);
            }

            Question updateQuestion = new Question();
            updateQuestion.setGmtModified(System.currentTimeMillis());
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setDescription(question.getDescription());
            updateQuestion.setTag(question.getTag());
            QuestionExample example = new QuestionExample();
            example.createCriteria()
                    .andIdEqualTo(question.getId());
            int update = questionMapper.updateByExampleSelective(updateQuestion, example);
            if (update!=1){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }

        }
    }

    @Override
    public void incView(Integer id) {
        Question question = new Question();
        question.setId(id);
        question.setViewCount(1);
        questionMapper.incView(question);
    }

}
