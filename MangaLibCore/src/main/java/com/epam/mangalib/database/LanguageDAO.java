package com.epam.mangalib.database;

import com.epam.mangalib.database.jsql.SQLDAO;
import com.epam.mangalib.database.jsql.SQLSelect;
import com.epam.mangalib.entity.Language;

import java.sql.SQLException;
import java.util.List;

import static com.epam.mangalib.util.MangaLibDAOConstant.*;

public class LanguageDAO extends SQLDAO<Language> {

    public List<Language> getLanguageList() throws SQLException {
        return mapListToObjectList(new SQLSelect().select(ALL).from(LANGUAGE_TABLE).executeSelect());
    }

    public Language getLanguageById(long id) throws SQLException {
        return mapListToObject(new SQLSelect().select(ALL).from(LANGUAGE_TABLE).where(LANGUAGE_ID)
                .eq(id).executeSelect());
    }
}

