package com.epam.mangalib.entity;

import com.epam.mangalib.database.jsql.SQLObject;

import java.util.Map;

import static com.epam.mangalib.util.MangaLibDAOConstant.*;

public class Language extends SQLObject {
    public Language() {}

    public Language(Map<String, Object> objectMap) {
        super(objectMap);
    }

    public long getId() {
        return (long) get(LANGUAGE_ID);
    }

    public void setId(long id) {
        set(LANGUAGE_ID, id);
    }

    public String getName() {
        return (String) get(LANGUAGE_NAME);
    }

    public void setName(String name) {
        set(LANGUAGE_NAME, name);
    }

    public String getLocale() {
        return (String) get(LANGUAGE_LOCALE);
    }

    public void setLocale(String locale) {
        set(LANGUAGE_LOCALE, locale);
    }

}
