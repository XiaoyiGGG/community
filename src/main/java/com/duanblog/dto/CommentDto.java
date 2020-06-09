package com.duanblog.dto;

import com.duanblog.model.User;
import lombok.Data;

@Data
public class CommentDto {
    private Long id;
    private Integer parentId;
    private Integer type;
    private Long commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private Long likeCount;
    private Integer commentCount;
    private String content;
    private User user;
}
