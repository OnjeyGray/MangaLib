package com.epam.mangalib.entity;

import com.epam.mangalib.database.jsql.SQLObject;

import java.util.List;
import java.util.Map;

import static com.epam.mangalib.util.MangaLibDAOConstant.*;

public class Chapter extends SQLObject {
    private List<Image> imageList;
    private List<Comment> commentList;

    public Chapter() {}

    public Chapter(Map<String, Object> objectMap) {
        super(objectMap);
    }

    public long getId() {
        return (long) get(CHAPTER_ID);
    }

    public void setId(long id) {
        set(CHAPTER_ID, id);
    }

    public String getName() {
        return (String) get(CHAPTER_NAME);
    }

    public void setName(String name) {
        set(CHAPTER_NAME, name);
    }

    public long getMangaId() {
        return (long) get(MANGA_ID);
    }

    public void setMangaId(long mangaId) {
        set(MANGA_ID, mangaId);
    }

    public long getLanguageId() {
        return (long) get(LANGUAGE_ID);
    }

    public void setLanguageId(long languageId) {
        set(LANGUAGE_ID, languageId);
    }

    public List<Image> getImageList() {
        return imageList;
    }

    public void setImageList(List<Image> imageList) {
        this.imageList = imageList;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }
}
