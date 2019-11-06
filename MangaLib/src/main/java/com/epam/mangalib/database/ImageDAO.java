package com.epam.mangalib.database;

import com.epam.mangalib.database.jsql.*;
import com.epam.mangalib.entity.Image;
import com.epam.mangalib.entity.Language;

import java.sql.SQLException;
import java.util.List;

import static com.epam.mangalib.util.MangaLibConstant.DEFAULT_MANGA_IMG;
import static com.epam.mangalib.util.MangaLibDAOConstant.*;

public class ImageDAO extends SQLDAO<Image> {

    public void updateTranslate(Image image) throws SQLException {
        new SQLUpdate().update(CHAPTER_TRANSLATE_TABLE).set(IMAGE_URL).eq(image.getURL()).
                where(IMAGE_ID).eq(image.getId()).and(LANGUAGE_ID).eq(image.getLanguageId()).executeUpdate();
    }

    public Image getImageByIdAndLanguage(long imageId, long languageId) throws SQLException {
        return mapListToObject(new SQLSelect().
                select(ALL).from(IMAGE_TABLE).innerJoin(IMAGE_TRANSLATE_TABLE).using(IMAGE_ID).
                where(IMAGE_ID).eq(imageId).and(LANGUAGE_ID).eq(languageId).executeSelect());
    }

    public List<Image> getImageListByChapter(long chapterId, long languageId) throws SQLException {
        return mapListToObjectList(new SQLSelect().select(ALL).from(IMAGE_TABLE).
                innerJoin(IMAGE_TRANSLATE_TABLE).using(IMAGE_ID).where(CHAPTER_ID).eq(chapterId).
                and(LANGUAGE_ID).eq(languageId).executeSelect());
    }

    public void addImage(long chapterId) throws SQLException {
        long next = (long) mapListToObject(new SQLCall().select("@next := max(IMAGE_ID) + 1").as(NEXT).from(IMAGE_TABLE).
                executeCall()).get(NEXT);
        long imageId = (next == 0 ? 1 : next);
        new SQLAlter().alterTable(IMAGE_TABLE).param(AUTO_INCREMENT).eq(imageId).executeAlter();
        new SQLInsert().insertInto(IMAGE_TABLE).variables(IMAGE_ID, CHAPTER_ID).values(imageId, chapterId).executeInsert();
        for(Language language : new LanguageDAO().getLanguageList()) {
            new SQLInsert().insertInto(IMAGE_TRANSLATE_TABLE).variables(IMAGE_ID, LANGUAGE_ID, IMAGE_URL)
                    .values(imageId, language.getId(), DEFAULT_MANGA_IMG).executeInsert();
        }
    }

    public void deleteImage(long imageId) throws SQLException {
        new SQLDelete().deleteFrom(IMAGE_TABLE).where(IMAGE_ID).eq(imageId).executeDelete();
    }
}

