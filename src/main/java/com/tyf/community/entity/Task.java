package com.tyf.community.entity;

import java.util.Date;

public class Task {

    private int id;

    private String userPost;

    private String title;

    private String content;

    private String userGet;

    private int status;

    private int circleId;

    private Date createTime;

    private Date updateTime;

    public Task() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getUserGet() {
        return userGet;
    }

    public void setUserGet(String userGet) {
        this.userGet = userGet;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public int getCircleId() {
        return circleId;
    }

    public void setCircleId(int circleId) {
        this.circleId = circleId;
    }

    @Override
    public String toString() {
        return "Task{" +
                "userPost='" + userPost + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", userGet='" + userGet + '\'' +
                ", status=" + status +
                '}';
    }
}
