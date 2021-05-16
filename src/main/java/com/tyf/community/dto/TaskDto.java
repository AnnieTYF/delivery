package com.tyf.community.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class TaskDto {

    @ApiModelProperty(value = "任务ID")
    private Integer id;

    @ApiModelProperty(value = "发布者学号")
    private String userPost;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "内容")
    private String content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserPost() {
        return userPost;
    }

    public void setUserPost(String userPost) {
        this.userPost = userPost;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
