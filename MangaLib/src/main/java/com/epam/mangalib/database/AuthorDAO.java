package com.epam.mangalib.database;

import com.epam.mangalib.database.jsql.*;
import com.epam.mangalib.entity.Author;
import com.epam.mangalib.entity.Language;

import java.sql.SQLException;
import java.util.List;

import static com.epam.mangalib.util.MangaLibDAOConstant.*;

public class AuthorDAO extends SQLDAO<Author> {

    public List<Author> getAuthorListByLanguage(long languageId) throws SQLException {
        return mapListToObjectList(new SQLSelect().
                select(ALL).from(AUTHOR_TABLE).innerJoin(AUTHOR_TRANSLATE_TABLE).using(AUTHOR_ID).
                where(LANGUAGE_ID).eq(languageId).orderBy(AUTHOR_NAME).executeSelect());
    }

    public Author getAuthorByIdAndLanguage(long id, long languageId) throws SQLException {
        return mapListToObject(new SQLSelect().
                select(ALL).from(AUTHOR_TABLE).innerJoin(AUTHOR_TRANSLATE_TABLE).using(AUTHOR_ID).
                where(AUTHOR_ID).eq(id).and(LANGUAGE_ID).eq(languageId).executeSelect());
    }

    public List<Author> getAuthorListByManga(long mangaId, long languageId) throws SQLException {
        return mapListToObjectList(new SQLSelect().
                select(ALL).from(AUTHOR_TABLE).innerJoin(AUTHOR_TRANSLATE_TABLE).using(AUTHOR_ID).
                where(AUTHOR_ID).in(new SQLSelect().
                select(AUTHOR_ID).from(MANGA_AUTHOR_TABLE).where(MANGA_ID).eq(mangaId).executeInner()).
                and(LANGUAGE_ID).eq(languageId).orderBy(AUTHOR_NAME).executeSelect());
    }

    public void updateTranslate(Author author) throws SQLException {
        new SQLUpdate().update(AUTHOR_TRANSLATE_TABLE).set(AUTHOR_NAME).eq(author.getName()).
                comma(AUTHOR_DESCRIPTION).eq(author.getDescription()).
                where(AUTHOR_ID).eq(author.getId()).and(LANGUAGE_ID).eq(author.getLanguageId()).executeUpdate();
    }

    public void addAuthor() throws SQLException {
        long next = (long) mapListToObject(new SQLCall().select("@next := max(AUTHOR_ID) + 1").as(NEXT).from(AUTHOR_TABLE).
                executeCall()).get(NEXT);
        long authorId = (next == 0 ? 1 : next);
        new SQLAlter().alterTable(AUTHOR_TABLE).param(AUTO_INCREMENT).eq(authorId).executeAlter();
        new SQLInsert().insertInto(AUTHOR_TABLE).variables(AUTHOR_ID).values(authorId).executeInsert();
        for(Language language : new LanguageDAO().getLanguageList()) {
            new SQLInsert().insertInto(AUTHOR_TRANSLATE_TABLE).variables(AUTHOR_ID, LANGUAGE_ID, AUTHOR_NAME,
                    AUTHOR_DESCRIPTION).values(authorId, language.getId(), language.getName(), language.getName()).executeInsert();
        }
    }

    public void deleteAuthor(long authorId) throws SQLException {
        new SQLDelete().deleteFrom(AUTHOR_TABLE).where(AUTHOR_ID).eq(authorId);
    }

    public List<Author> search(String search) throws SQLException {
        return mapListToObjectList(new SQLSelect().select(ALL).from(AUTHOR_TABLE).innerJoin(AUTHOR_TRANSLATE_TABLE).using(AUTHOR_ID).
                where("AUTHOR_NAME collate UTF8_GENERAL_CI").like("%" + search + "%").
        or(AUTHOR_DESCRIPTION).like("%" + search + "%").executeSelect());
    }
}

