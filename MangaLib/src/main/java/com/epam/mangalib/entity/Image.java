package com.epam.mangalib.entity;

import com.epam.mangalib.database.jsql.SQLObject;

import java.util.Map;

import static com.epam.mangalib.util.MangaLibDAOConstant.*;

public class Image extends SQLObject {
    public Image() {}

    public Image(Map<String, Object> objectMap) {
        super(objectMap);
    }

    public long getId() {
        return (long) get(IMAGE_ID);
    }

    public void setId(long id) {
        set(IMAGE_ID, id);
    }

    public String getURL() {
        return (String) get(IMAGE_URL);
    }

    public void setURL(String URL) {
        set(IMAGE_URL, URL);
    }

    public long getChapterId() {
        return (long) get(CHAPTER_ID);
    }

    public void setChapterId(long chapterId) {
        set(CHAPTER_ID, chapterId);
    }

    public long getLanguageId() {
        return (long) get(LANGUAGE_ID);
    }

    public void setLanguageId(long languageId) {
        set(LANGUAGE_ID, languageId);
    }
}
