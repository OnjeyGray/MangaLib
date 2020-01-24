package com.epam.mangalib.database;

import com.epam.mangalib.database.jsql.*;
import com.epam.mangalib.entity.Chapter;
import com.epam.mangalib.entity.Language;

import java.sql.SQLException;
import java.util.List;

import static com.epam.mangalib.util.MangaLibDAOConstant.*;

public class ChapterDAO extends SQLDAO<Chapter> {

    public void updateTranslate(Chapter chapter) throws SQLException {
        new SQLUpdate().update(CHAPTER_TRANSLATE_TABLE).set(CHAPTER_NAME).eq(chapter.getName()).
                where(CHAPTER_ID).eq(chapter.getId()).and(LANGUAGE_ID).eq(chapter.getLanguageId()).executeUpdate();
    }

    public Chapter getChapterByIdAndLanguage(long chapterId, long languageId) throws SQLException {
        return mapListToObject(new SQLSelect().
                select(ALL).from(CHAPTER_TABLE).innerJoin(CHAPTER_TRANSLATE_TABLE).using(CHAPTER_ID).
                where(CHAPTER_ID).eq(chapterId).and(LANGUAGE_ID).eq(languageId).executeSelect());
    }

    public List<Chapter> getChapterListByManga(long mangaId, long languageId) throws SQLException {
        return mapListToObjectList(new SQLSelect().select(ALL).from(CHAPTER_TABLE).
                innerJoin(CHAPTER_TRANSLATE_TABLE).using(CHAPTER_ID).where(MANGA_ID).eq(mangaId).
                and(LANGUAGE_ID).eq(languageId).executeSelect());
    }

    public void addChapter(long mangaId) throws SQLException {
        long next = (long) mapListToObject(new SQLCall().select("@next := max(CHAPTER_ID) + 1").as(NEXT).from(CHAPTER_TABLE).
                executeCall()).get(NEXT);
        long chapterId = (next == 0 ? 1 : next);
        new SQLAlter().alterTable(CHAPTER_TABLE).param(AUTO_INCREMENT).eq(chapterId).executeAlter();
        new SQLInsert().insertInto(CHAPTER_TABLE).variables(CHAPTER_ID, MANGA_ID).values(chapterId, mangaId).executeInsert();
        for(Language language : new LanguageDAO().getLanguageList()) {
            new SQLInsert().insertInto(CHAPTER_TRANSLATE_TABLE).variables(CHAPTER_ID, LANGUAGE_ID, CHAPTER_NAME)
                    .values(chapterId, language.getId(), language.getName()).executeInsert();
        }
    }

    public void deleteChapter(long chapterId) throws SQLException {
        new SQLDelete().deleteFrom(CHAPTER_TABLE).where(CHAPTER_ID).eq(chapterId).executeDelete();
    }
}

