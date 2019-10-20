package com.epam.mangalib.service;

import com.epam.mangalib.database.GenreDAO;
import com.epam.mangalib.database.MangaDAO;
import com.epam.mangalib.entity.Genre;
import com.epam.mangalib.entity.Language;
import com.epam.mangalib.exception.ValidationException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static com.epam.mangalib.util.MangaLibEntityAttribute.*;
import static com.epam.mangalib.util.MangaLibJSP.GENRE_JSP;

public class ShowGenreService implements Service {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException, InterruptedException, ValidationException {
        saveCurrentPageInSession(req);
        long genreId = Long.parseLong(req.getParameter(GENRE_ID_ATTRIBUTE));
        Language language = (Language) req.getSession().getAttribute(CURRENT_LANGUAGE_ATTRIBUTE);
        long languageId = language.getId();
        GenreDAO genreDAO = new GenreDAO();
        Genre genre = genreDAO.getGenreByIdAndLanguage(genreId, languageId);
        MangaDAO mangaDAO = new MangaDAO();
        genre.setMangaList(mangaDAO.getMangaListByGenre(genreId, languageId));
        req.setAttribute(GENRE_ATTRIBUTE, genre);
        req.getRequestDispatcher(GENRE_JSP).forward(req, resp);
    }
}
