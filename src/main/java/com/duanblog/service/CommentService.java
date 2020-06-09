package com.duanblog.service;

import com.duanblog.dto.CommentDto;
import com.duanblog.model.Comment;

import java.util.List;

public interface CommentService {
    void insertComment(Comment comment);

    List<CommentDto> findAllComment(Integer id);
}
