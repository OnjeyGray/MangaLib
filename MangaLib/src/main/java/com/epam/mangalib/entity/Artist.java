package com.epam.mangalib.entity;

import com.epam.mangalib.database.jsql.SQLObject;

import java.util.List;
import java.util.Map;

import static com.epam.mangalib.util.MangaLibDAOConstant.*;

public class Artist extends SQLObject {
    private List<Manga> mangaList;

    public Artist() {}

    public Artist(Map<String, Object> objectMap) {
        super(objectMap);
    }

    public long getId() {
        return (long) get(ARTIST_ID);
    }

    public void setId(long id) {
        set(ARTIST_ID, id);
    }

    public long getLanguageId() {
        return (long) get(LANGUAGE_ID);
    }

    public void setLanguageId(long languageId) {
        set(LANGUAGE_ID, languageId);
    }

    public String getName() {
        return (String) get(ARTIST_NAME);
    }

    public void setName(String name) {
        set(ARTIST_NAME, name);
    }

    public String getDescription() {
        return (String) get(ARTIST_DESCRIPTION);
    }

    public void setDescription(String description) {
        set(ARTIST_DESCRIPTION, description);
    }

    public List<Manga> getMangaList() {
        return mangaList;
    }

    public void setMangaList(List<Manga> mangaList) {
        this.mangaList = mangaList;
    }
}
