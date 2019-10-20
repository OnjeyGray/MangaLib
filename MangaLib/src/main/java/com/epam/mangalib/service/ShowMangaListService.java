package com.epam.mangalib.service;

import com.epam.mangalib.database.MangaDAO;
import com.epam.mangalib.entity.Language;
import com.epam.mangalib.entity.Manga;
import com.epam.mangalib.exception.ValidationException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static com.epam.mangalib.util.MangaLibEntityAttribute.CURRENT_LANGUAGE_ATTRIBUTE;
import static com.epam.mangalib.util.MangaLibEntityAttribute.MANGA_LIST_ATTRIBUTE;
import static com.epam.mangalib.util.MangaLibJSP.MANGA_LIST_JSP;

public class ShowMangaListService implements Service {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException, InterruptedException, ValidationException {
        saveCurrentPageInSession(req);
        Language language = (Language) req.getSession().getAttribute(CURRENT_LANGUAGE_ATTRIBUTE);
        MangaDAO mangaDAO = new MangaDAO();
        List<Manga> mangaList = mangaDAO.getMangaListByLanguage(language.getId());
        req.setAttribute(MANGA_LIST_ATTRIBUTE, mangaList);
        req.getRequestDispatcher(MANGA_LIST_JSP).forward(req, resp);
    }
}