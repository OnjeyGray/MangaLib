package com.epam.mangalib.database;

import com.epam.mangalib.database.jsql.*;
import com.epam.mangalib.entity.Genre;
import com.epam.mangalib.entity.Language;

import java.sql.SQLException;
import java.util.List;

import static com.epam.mangalib.util.MangaLibDAOConstant.*;

public class GenreDAO extends SQLDAO<Genre> {

    public List<Genre> getGenreListByLanguage(long languageId) throws SQLException {
        return mapListToObjectList(new SQLSelect().
                select(ALL).from(GENRE_TABLE).innerJoin(GENRE_TRANSLATE_TABLE).using(GENRE_ID).
                where(LANGUAGE_ID).eq(languageId).orderBy(GENRE_NAME).executeSelect());
    }

    public Genre getGenreByIdAndLanguage(long id, long languageId) throws SQLException {
        return mapListToObject(new SQLSelect().
                select(ALL).from(GENRE_TABLE).innerJoin(GENRE_TRANSLATE_TABLE).using(GENRE_ID).
                where(GENRE_ID).eq(id).and(LANGUAGE_ID).eq(languageId).executeSelect());
    }

    public List<Genre> getGenreListByManga(long mangaId, long languageId) throws SQLException {
        return mapListToObjectList(new SQLSelect().
                select(ALL).from(GENRE_TABLE).innerJoin(GENRE_TRANSLATE_TABLE).using(GENRE_ID).
                where(GENRE_ID).in(new SQLSelect().
                select(GENRE_ID).from(MANGA_GENRE_TABLE).where(MANGA_ID).eq(mangaId).executeInner()).
                and(LANGUAGE_ID).eq(languageId).orderBy(GENRE_NAME).executeSelect());
    }

    public void updateTranslate(Genre genre) throws SQLException {
        new SQLUpdate().update(GENRE_TRANSLATE_TABLE).set(GENRE_NAME).eq(genre.getName()).
                comma(GENRE_DESCRIPTION).eq(genre.getDescription()).
                where(GENRE_ID).eq(genre.getId()).and(LANGUAGE_ID).eq(genre.getLanguageId()).executeUpdate();

    }

    public void addGenre() throws SQLException {
        long next = (long) mapListToObject(new SQLCall().select("@next := max(GENRE_ID) + 1").as(NEXT).from(GENRE_TABLE).
                executeCall()).get(NEXT);
        long genreId = (next == 0 ? 1 : next);
        new SQLAlter().alterTable(GENRE_TABLE).param(AUTO_INCREMENT).eq(genreId).executeAlter();
        new SQLInsert().insertInto(GENRE_TABLE).variables(GENRE_ID).values(genreId).executeInsert();
        for(Language language : new LanguageDAO().getLanguageList()) {
            new SQLInsert().insertInto(GENRE_TRANSLATE_TABLE).variables(GENRE_ID, LANGUAGE_ID, GENRE_NAME,
                    GENRE_DESCRIPTION).values(genreId, language.getId(), language.getName(), language.getName()).executeInsert();
        }
    }

    public void deleteGenre(long genreId) {
        new SQLDelete().deleteFrom(GENRE_TABLE).where(GENRE_ID).eq(genreId);
    }

    public List<Genre> search(String search) throws SQLException {
        return mapListToObjectList(new SQLSelect().select(ALL).from(GENRE_TABLE).innerJoin(GENRE_TRANSLATE_TABLE).using(GENRE_ID).
                where("GENRE_NAME collate UTF8_GENERAL_CI").like("% "+ search + "%").
        or(GENRE_DESCRIPTION).like("%" + search + "%").executeSelect());
    }
}

