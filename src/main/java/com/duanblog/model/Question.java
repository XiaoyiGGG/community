package com.duanblog.model;

import lombok.Data;

@Data
public class Question {
    private Integer id;

    private String title;

    private String description;

    private Long gmtCreate;

    private Long gmtModified;

    private Integer creator;

    private Integer commentCount;

    private Integer viewCount;

    private Integer likeCount;

    private String tag;

    public Question() {
    }

    public Question(Integer id, String title, String description, Long gmtCreate, Long gmtModified, Integer creator, Integer commentCount, Integer viewCount, Integer likeCount, String tag) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.gmtCreate = gmtCreate;
        this.gmtModified = gmtModified;
        this.creator = creator;
        this.commentCount = commentCount;
        this.viewCount = viewCount;
        this.likeCount = likeCount;
        this.tag = tag;
    }
}