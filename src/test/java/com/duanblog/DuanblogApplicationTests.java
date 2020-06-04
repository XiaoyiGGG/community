package com.duanblog;

import com.duanblog.dto.QuestionDto;
import com.duanblog.mapper.QuestionMapper;
import com.duanblog.mapper.UserMapper;
import com.duanblog.model.Question;
import com.duanblog.model.QuestionExample;
import com.duanblog.model.User;
import com.duanblog.model.UserExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
@RunWith(SpringRunner.class)
@SpringBootTest
public class DuanblogApplicationTests {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;


    @Test
    public void contextLoads() {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andTokenEqualTo("d44d2e5e-a99f-4772-8a56-f0d6399c2a07");
        List<User> users = userMapper.selectByExample(userExample);
        System.out.println(users);

    }

    @Test
    public void test1(){

        for (int i = 0; i <145 ; i++) {
            Question question = new Question(null,"title"+i,"description"+i,1590136237018L+60*i,1590136237018L+60*i,8,null,null,null,"tag"+i);
            questionMapper.insert(question);
        }
    }

    @Test
    public void test2(){
        List<Question> list = questionMapper.list(1, 5);
        System.out.println(list);
    }

    @Test
    public void test3(){
        Integer page = 2;
        Integer size = 5;
        PageHelper.startPage(page,size);
        List<Question> questions = questionMapper.selectByExample(new QuestionExample());
        System.out.println(questions);

        List<QuestionDto> questionDTOList = new ArrayList<>();
        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDto questionDTO = new QuestionDto();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        PageInfo<QuestionDto> questionDtoPageInfo = new PageInfo<QuestionDto>(questionDTOList);
        System.out.println(questionDtoPageInfo);
    }
}
