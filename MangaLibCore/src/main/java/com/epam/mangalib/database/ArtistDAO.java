package com.epam.mangalib.database;

import com.epam.mangalib.database.jsql.*;
import com.epam.mangalib.entity.Artist;
import com.epam.mangalib.entity.Language;

import java.sql.SQLException;
import java.util.List;

import static com.epam.mangalib.util.MangaLibDAOConstant.*;

public class ArtistDAO extends SQLDAO<Artist> {

    public List<Artist> getArtistListByLanguage(long languageId) throws SQLException {
        return mapListToObjectList(new SQLSelect().
                select(ALL).from(ARTIST_TABLE).innerJoin(ARTIST_TRANSLATE_TABLE).using(ARTIST_ID).
                where(LANGUAGE_ID).eq(languageId).orderBy(ARTIST_NAME).executeSelect());
    }

    public Artist getArtistByIdAndLanguage(long id, long languageId) throws SQLException {
        return mapListToObject(new SQLSelect().
                select(ALL).from(ARTIST_TABLE).innerJoin(ARTIST_TRANSLATE_TABLE).using(ARTIST_ID).
                where(ARTIST_ID).eq(id).and(LANGUAGE_ID).eq(languageId).executeSelect());
    }

    public List<Artist> getArtistListByManga(long mangaId, long languageId) throws SQLException {
        return mapListToObjectList(new SQLSelect().
                select(ALL).from(ARTIST_TABLE).innerJoin(ARTIST_TRANSLATE_TABLE).using(ARTIST_ID).
                where(ARTIST_ID).in(new SQLSelect().
                select(ARTIST_ID).from(MANGA_ARTIST_TABLE).where(MANGA_ID).eq(mangaId).executeInner()).
                and(LANGUAGE_ID).eq(languageId).orderBy(ARTIST_NAME).executeSelect());
    }

    public void updateTranslate(Artist artist) throws SQLException {
        new SQLUpdate().update(ARTIST_TRANSLATE_TABLE).set(ARTIST_NAME).eq(artist.getName()).
                comma(ARTIST_DESCRIPTION).eq(artist.getDescription()).
                where(ARTIST_ID).eq(artist.getId()).and(LANGUAGE_ID).eq(artist.getLanguageId()).executeUpdate();
    }

    public void addArtist() throws SQLException {
        long next = (long) mapListToObject(new SQLCall().select("@next := max(ARTIST_ID) + 1").as(NEXT).from(ARTIST_TABLE).
                executeCall()).get(NEXT);
        long artistId = (next == 0 ? 1 : next);
        new SQLAlter().alterTable(ARTIST_TABLE).param(AUTO_INCREMENT).eq(artistId).executeAlter();
        new SQLInsert().insertInto(ARTIST_TABLE).variables(ARTIST_ID).values(artistId).executeInsert();
        for(Language language : new LanguageDAO().getLanguageList()) {
            new SQLInsert().insertInto(ARTIST_TRANSLATE_TABLE).variables(ARTIST_ID, LANGUAGE_ID, ARTIST_NAME,
                    ARTIST_DESCRIPTION).values(artistId, language.getId(), language.getName(), language.getName()).executeInsert();
        }
    }

    public void deleteArtist(long artistId) {
        new SQLDelete().deleteFrom(ARTIST_TABLE).where(ARTIST_ID).eq(artistId);
    }

    public List<Artist> search(String search) throws SQLException {
        return mapListToObjectList(new SQLSelect().select(ALL).from(ARTIST_TABLE).innerJoin(ARTIST_TRANSLATE_TABLE).using(ARTIST_ID).
                where("ARTIST_NAME collate UTF8_GENERAL_CI").like("%" + search + "%").
        or(ARTIST_DESCRIPTION).like("%" + search + "%").executeSelect());
    }
}

