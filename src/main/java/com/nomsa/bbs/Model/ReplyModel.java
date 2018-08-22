package com.nomsa.bbs.Model;

public class ReplyModel {

    private int index;
    private String writer;
    private String created;
    private int post;
    private String content;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created.substring(0, 10);
    }

    public int getPost() {
        return post;
    }

    public void setPost(int post) {
        this.post = post;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
