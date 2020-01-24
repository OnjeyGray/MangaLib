package com.epam.mangalib.database;

import com.epam.mangalib.database.jsql.*;
import com.epam.mangalib.entity.Language;
import com.epam.mangalib.entity.Manga;

import java.sql.SQLException;
import java.util.List;

import static com.epam.mangalib.util.MangaLibConstant.DEFAULT_MANGA_IMG;
import static com.epam.mangalib.util.MangaLibDAOConstant.*;

public class MangaDAO extends SQLDAO<Manga> {

    public List<Manga> getMangaListByLanguage(long languageId) throws SQLException {
        return mapListToObjectList(new SQLSelect().
                select(ALL).from(MANGA_TABLE).innerJoin(MANGA_TRANSLATE_TABLE).using(MANGA_ID).
                where(LANGUAGE_ID).eq(languageId).orderBy(MANGA_NAME).executeSelect());
    }

    public Manga getMangaByIdAndLanguage(long id, long languageId) throws SQLException {
        return mapListToObject(new SQLSelect().
                select(ALL).from(MANGA_TABLE).innerJoin(MANGA_TRANSLATE_TABLE).using(MANGA_ID).
                where(MANGA_ID).eq(id).and(LANGUAGE_ID).eq(languageId).executeSelect());
    }

    public List<Manga> getMangaListByUser(long userId, long languageId) throws SQLException {
        return mapListToObjectList(new SQLSelect().
                select(ALL).from(MANGA_TABLE).innerJoin(MANGA_TRANSLATE_TABLE).using(MANGA_ID).
                where(MANGA_ID).in(new SQLSelect().
                select(MANGA_ID).from(MANGA_USER_TABLE).where(USER_ID).eq(userId).executeInner()).
                and(LANGUAGE_ID).eq(languageId).orderBy(MANGA_NAME).executeSelect());
    }

    public List<Manga> getMangaListByGenre(long genreId, long languageId) throws SQLException {
        return mapListToObjectList(new SQLSelect().
                select(ALL).from(MANGA_TABLE).innerJoin(MANGA_TRANSLATE_TABLE).using(MANGA_ID).
                where(MANGA_ID).in(new SQLSelect().
                select(MANGA_ID).from(MANGA_GENRE_TABLE).where(GENRE_ID).eq(genreId).executeInner()).
                and(LANGUAGE_ID).eq(languageId).orderBy(MANGA_NAME).executeSelect());
    }

    public List<Manga> getMangaListByAuthor(long authorId, long languageId) throws SQLException {
        return mapListToObjectList(new SQLSelect().
                select(ALL).from(MANGA_TABLE).innerJoin(MANGA_TRANSLATE_TABLE).using(MANGA_ID).
                where(MANGA_ID).in(new SQLSelect().
                select(MANGA_ID).from(MANGA_AUTHOR_TABLE).where(AUTHOR_ID).eq(authorId).executeInner()).
                and(LANGUAGE_ID).eq(languageId).orderBy(MANGA_NAME).executeSelect());
    }

    public List<Manga> getMangaListByArtist(long artistId, long languageId) throws SQLException {
        return mapListToObjectList(new SQLSelect().
                select(ALL).from(MANGA_TABLE).innerJoin(MANGA_TRANSLATE_TABLE).using(MANGA_ID).
                where(MANGA_ID).in(new SQLSelect().
                select(MANGA_ID).from(MANGA_ARTIST_TABLE).where(ARTIST_ID).eq(artistId).executeInner()).
                and(LANGUAGE_ID).eq(languageId).orderBy(MANGA_NAME).executeSelect());
    }

    public void updateTranslate(Manga manga) throws SQLException {
        new SQLUpdate().update(MANGA_TRANSLATE_TABLE).set(MANGA_NAME).eq(manga.getName()).
                comma(MANGA_DESCRIPTION).eq(manga.getDescription()).
                where(MANGA_ID).eq(manga.getId()).and(LANGUAGE_ID).eq(manga.getLanguageId()).executeUpdate();
    }

    public void updateImg(Manga manga) throws SQLException {
        new SQLUpdate().update(MANGA_TABLE).set(MANGA_IMG_URL).eq(manga.getImgURL()).
                where(MANGA_ID).eq(manga.getId()).executeUpdate();
    }

    public void addGenre(long mangaId, long genreId) throws SQLException {
        new SQLInsert().insertInto(MANGA_GENRE_TABLE).variables(MANGA_ID, GENRE_ID).
                values(mangaId, genreId).executeInsert();
    }

    public void deleteGenre(long mangaId, long genreId) throws SQLException {
        new SQLDelete().deleteFrom(MANGA_GENRE_TABLE).where(MANGA_ID).eq(mangaId).
                and(GENRE_ID).eq(genreId).executeDelete();
    }

    public void addArtist(long mangaId, long artistId) throws SQLException {
        new SQLInsert().insertInto(MANGA_ARTIST_TABLE).variables(MANGA_ID, ARTIST_ID).
                values(mangaId, artistId).executeInsert();
    }

    public void deleteArtist(long mangaId, long artistId) throws SQLException {
        new SQLDelete().deleteFrom(MANGA_ARTIST_TABLE).where(MANGA_ID).eq(mangaId).
                and(ARTIST_ID).eq(artistId).executeDelete();
    }

    public void addAuthor(long mangaId, long authorId) throws SQLException {
        new SQLInsert().insertInto(MANGA_AUTHOR_TABLE).variables(MANGA_ID, AUTHOR_ID).
                values(mangaId, authorId).executeInsert();
    }

    public void deleteAuthor(long mangaId, long authorId) throws SQLException {
        new SQLDelete().deleteFrom(MANGA_AUTHOR_TABLE).where(MANGA_ID).eq(mangaId).
                and(AUTHOR_ID).eq(authorId).executeDelete();
    }

    public void addManga() throws SQLException {
        long next = (long) mapListToObject(new SQLCall().select("@next := max(MANGA_ID) + 1").as(NEXT).from(MANGA_TABLE).
                executeCall()).get(NEXT);
        long mangaId = (next == 0 ? 1 : next);
        new SQLAlter().alterTable(MANGA_TABLE).param(AUTO_INCREMENT).eq(mangaId).executeAlter();
        new SQLInsert().insertInto(MANGA_TABLE).variables(MANGA_ID, MANGA_IMG_URL).values(mangaId, DEFAULT_MANGA_IMG).executeInsert();
        for(Language language : new LanguageDAO().getLanguageList()) {
            new SQLInsert().insertInto(MANGA_TRANSLATE_TABLE).variables(MANGA_ID, LANGUAGE_ID, MANGA_NAME,
                    MANGA_DESCRIPTION).values(mangaId, language.getId(), language.getName(), language.getName()).executeInsert();
        }
    }

    public void deleteManga(long mangaId) throws SQLException {
        new SQLDelete().deleteFrom(MANGA_TABLE).where(MANGA_ID).eq(mangaId).executeDelete();
    }

    public void favorite(long mangaId, long userId) throws SQLException {
        new SQLInsert().insertInto(MANGA_USER_TABLE).variables(MANGA_ID, USER_ID).values(mangaId, userId).executeInsert();
    }

    public void unfavorite(long mangaId, long userId) throws SQLException {
        new SQLDelete().deleteFrom(MANGA_USER_TABLE).where(MANGA_ID).eq(mangaId).
                and(USER_ID).eq(userId).executeDelete();
    }

    public List<Manga> getMostPopular(long languageId) throws SQLException {
        return mapListToObjectList(new SQLSelect().select(ALL, "count(MANGA_ID) as 'popularity'").
        from(MANGA_TABLE).innerJoin(MANGA_USER_TABLE).using(MANGA_ID).
                innerJoin(MANGA_TRANSLATE_TABLE).using(MANGA_ID).where(LANGUAGE_ID).
                eq(languageId).groupBy(MANGA_ID).orderBy("popularity").desc().executeSelect());
    }

    public List<Manga> search(String search) throws SQLException {
        return mapListToObjectList(new SQLSelect().select(ALL).from(MANGA_TABLE).innerJoin(MANGA_TRANSLATE_TABLE).using(MANGA_ID).
                where("MANGA_NAME collate UTF8_GENERAL_CI").like("%" + search + "%").
        or(MANGA_DESCRIPTION).like("%" + search + "%").executeSelect());
    }
}

