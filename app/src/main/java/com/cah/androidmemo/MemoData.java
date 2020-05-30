package com.cah.androidmemo;

public class MemoData {
    private String id, title, content;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public MemoData(String id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }
}
