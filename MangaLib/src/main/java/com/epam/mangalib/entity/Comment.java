package com.epam.mangalib.entity;

import com.epam.mangalib.database.jsql.SQLObject;

import java.util.Map;

import static com.epam.mangalib.util.MangaLibDAOConstant.*;

public class Comment extends SQLObject {
    private User user;

    public Comment() {}

    public Comment(Map<String, Object> objectMap) {
        super(objectMap);
    }

    public long getId() {
        return (long) get(COMMENT_ID);
    }

    public void setId(long id) {
        set(COMMENT_ID, id);
    }

    public long getUserId() {
        return (long) get(USER_ID);
    }

    public void setUserId(long mangaId) {
        set(USER_ID, mangaId);
    }

    public long getChapterId() {
        return (long) get(CHAPTER_ID);
    }

    public void setChapterId(long languageId) {
        set(CHAPTER_ID, languageId);
    }

    public String getContent() {
        return (String) get(COMMENT_CONTENT);
    }

    public void setContent(String name) {
        set(COMMENT_CONTENT, name);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
