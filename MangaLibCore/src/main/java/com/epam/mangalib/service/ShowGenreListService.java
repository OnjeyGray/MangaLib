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
import java.util.List;

import static com.epam.mangalib.util.MangaLibEntityAttribute.CURRENT_LANGUAGE_ATTRIBUTE;
import static com.epam.mangalib.util.MangaLibEntityAttribute.GENRE_LIST_ATTRIBUTE;
import static com.epam.mangalib.util.MangaLibJSP.GENRE_LIST_JSP;

public class ShowGenreListService implements Service {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException, InterruptedException, ValidationException {
        saveCurrentPageInSession(req);
        Language language = (Language) req.getSession().getAttribute(CURRENT_LANGUAGE_ATTRIBUTE);
        GenreDAO genreDAO = new GenreDAO();
        List<Genre> genreList = genreDAO.getGenreListByLanguage(language.getId());
        MangaDAO mangaDAO = new MangaDAO();
        for(Genre genre : genreList) {
            genre.setMangaList(mangaDAO.getMangaListByGenre(genre.getId(), language.getId()));
        }
        req.setAttribute(GENRE_LIST_ATTRIBUTE, genreList);
        req.getRequestDispatcher(GENRE_LIST_JSP).forward(req, resp);
    }
}
