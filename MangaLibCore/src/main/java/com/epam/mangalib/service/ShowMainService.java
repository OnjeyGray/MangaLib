package com.epam.mangalib.service;

import com.epam.mangalib.database.MangaDAO;
import com.epam.mangalib.entity.Language;
import com.epam.mangalib.entity.Manga;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static com.epam.mangalib.util.MangaLibEntityAttribute.CURRENT_LANGUAGE_ATTRIBUTE;
import static com.epam.mangalib.util.MangaLibEntityAttribute.MANGA_LIST_ATTRIBUTE;
import static com.epam.mangalib.util.MangaLibJSP.MAIN_JSP;

public class ShowMainService implements Service {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        saveCurrentPageInSession(req);
        Language language = (Language) req.getSession().getAttribute(CURRENT_LANGUAGE_ATTRIBUTE);
        long languageId = language.getId();
        MangaDAO mangaDAO = new MangaDAO();
        List<Manga> mangaList = mangaDAO.getMostPopular(languageId);
        req.setAttribute(MANGA_LIST_ATTRIBUTE, mangaList);
        req.getRequestDispatcher(MAIN_JSP).forward(req, resp);
    }
}
