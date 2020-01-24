package com.epam.mangalib.entity;

import com.epam.mangalib.database.jsql.SQLObject;

import java.util.List;
import java.util.Map;

import static com.epam.mangalib.util.MangaLibDAOConstant.*;

public class Manga extends SQLObject {
    private List<User> userList;
    private List<Genre> genreList;
    private List<Author> authorList;
    private List<Artist> artistList;
    private List<Chapter> chapterList;

    public Manga() {}

    public Manga(Map<String, Object> objectMap) {
        super(objectMap);
    }

    public long getId() {
        return (long) get(MANGA_ID);
    }

    public void setId(long id) {
        set(MANGA_ID, id);
    }

    public long getLanguageId() {
        return (long) get(LANGUAGE_ID);
    }

    public void setLanguageId(long languageId) {
        set(LANGUAGE_ID, languageId);
    }

    public String getName() {
        return (String) get(MANGA_NAME);
    }

    public void setName(String name) {
        set(MANGA_NAME, name);
    }

    public String getImgURL() {
        return (String) get(MANGA_IMG_URL);
    }

    public void setImgURL(String imgURL) {
        set(MANGA_IMG_URL, imgURL);
    }

    public String getDescription() {
        return (String) get(MANGA_DESCRIPTION);
    }

    public void setDescription(String description) {
        set(MANGA_DESCRIPTION, description);
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public List<Genre> getGenreList() {
        return genreList;
    }

    public void setGenreList(List<Genre> genreList) {
        this.genreList = genreList;
    }

    public List<Author> getAuthorList() {
        return authorList;
    }

    public void setAuthorList(List<Author> authorList) {
        this.authorList = authorList;
    }

    public List<Artist> getArtistList() {
        return artistList;
    }

    public void setArtistList(List<Artist> artistList) {
        this.artistList = artistList;
    }

    public List<Chapter> getChapterList() {
        return chapterList;
    }

    public void setChapterList(List<Chapter> chapterList) {
        this.chapterList = chapterList;
    }

}
