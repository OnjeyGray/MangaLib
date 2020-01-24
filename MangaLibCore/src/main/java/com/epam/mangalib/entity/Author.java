package com.epam.mangalib.entity;

import com.epam.mangalib.database.jsql.SQLObject;

import java.util.List;
import java.util.Map;

import static com.epam.mangalib.util.MangaLibDAOConstant.*;

public class Author extends SQLObject {
    private List<Manga> mangaList;

    public Author() {}

    public Author(Map<String, Object> objectMap) {
        super(objectMap);
    }

    public long getId() {
        return (long) get(AUTHOR_ID);
    }

    public void setId(long id) {
        set(AUTHOR_ID, id);
    }

    public long getLanguageId() {
        return (long) get(LANGUAGE_ID);
    }

    public void setLanguageId(long languageId) {
        set(LANGUAGE_ID, languageId);
    }

    public String getName() {
        return (String) get(AUTHOR_NAME);
    }

    public void setName(String name) {
        set(AUTHOR_NAME, name);
    }

    public String getDescription() {
        return (String) get(AUTHOR_DESCRIPTION);
    }

    public void setDescription(String description) {
        set(AUTHOR_DESCRIPTION, description);
    }

    public List<Manga> getMangaList() {
        return mangaList;
    }

    public void setMangaList(List<Manga> mangaList) {
        this.mangaList = mangaList;
    }

}
