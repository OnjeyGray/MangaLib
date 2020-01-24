package com.epam.mangalib.service;

import com.epam.mangalib.database.GenreDAO;
import com.epam.mangalib.entity.Genre;
import com.epam.mangalib.entity.Language;
import com.epam.mangalib.exception.ValidationException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static com.epam.mangalib.util.MangaLibEntityAttribute.*;
import static com.epam.mangalib.util.MangaLibParameterAttribute.*;
import static com.epam.mangalib.util.MangaLibShowURI.SHOW_GENRE_LIST_URI;
import static com.epam.mangalib.validation.Validator.validateDescription;
import static com.epam.mangalib.validation.Validator.validateName;

public class EditGenreService implements Service {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException, InterruptedException, ValidationException {
        String attribute = req.getParameter(ATTRIBUTE);
        switch (attribute) {
            case GENRE_NAME_ATTRIBUTE:
                editGenreName(req, resp);
                break;
            case GENRE_DESCRIPTION_ATTRIBUTE:
                editGenreDescription(req, resp);
                break;
            case GENRE_ATTRIBUTE:
                editGenre(req, resp);
                break;
            default:
                throw new ValidationException(ATTRIBUTE_ERROR);
        }
    }

    void editGenreName(HttpServletRequest req, HttpServletResponse resp) throws ValidationException, SQLException, ServletException, IOException {
        String genreName = validateName(req.getParameter(GENRE_NAME_ATTRIBUTE));
        GenreDAO genreDAO = new GenreDAO();
        Genre genre = getGenre(req, genreDAO);
        genre.setName(genreName);
        genreDAO.updateTranslate(genre);
        resp.sendRedirect((String) req.getSession().getAttribute(CURRENT_PAGE_ATTRIBUTE));
    }

    void editGenreDescription(HttpServletRequest req, HttpServletResponse resp) throws ValidationException, SQLException, ServletException, IOException {
        String genreDescription = validateDescription(req.getParameter(GENRE_DESCRIPTION_ATTRIBUTE));
        GenreDAO genreDAO = new GenreDAO();
        Genre genre = getGenre(req, genreDAO);
        genre.setDescription(genreDescription);
        genreDAO.updateTranslate(genre);
        resp.sendRedirect((String) req.getSession().getAttribute(CURRENT_PAGE_ATTRIBUTE));
    }

    void editGenre(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException, ValidationException {
        GenreDAO genreDAO = new GenreDAO();
        String action = req.getParameter(ACTION);
        switch (action) {
            case ADD:
                genreDAO.addGenre();
                resp.sendRedirect((String) req.getSession().getAttribute(CURRENT_PAGE_ATTRIBUTE));
                break;
            case DELETE:
                long genreId = Long.parseLong(req.getParameter(GENRE_ID_ATTRIBUTE));
                genreDAO.deleteGenre(genreId);
                resp.sendRedirect(SHOW_GENRE_LIST_URI);
                break;
            default:
                throw new ValidationException(ATTRIBUTE_ERROR);
        }
    }


    Genre getGenre(HttpServletRequest req, GenreDAO genreDAO) throws SQLException {
        Language language = (Language) req.getSession().getAttribute(CURRENT_LANGUAGE_ATTRIBUTE);
        long languageId = language.getId();
        long genreId = Long.parseLong(req.getParameter(GENRE_ID_ATTRIBUTE));
        Genre genre = genreDAO.getGenreByIdAndLanguage(genreId, languageId);
        return genre;
    }
}
