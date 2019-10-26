package com.epam.mangalib.service;

import com.epam.mangalib.database.AuthorDAO;
import com.epam.mangalib.entity.Author;
import com.epam.mangalib.entity.Language;
import com.epam.mangalib.exception.ValidationException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static com.epam.mangalib.util.MangaLibEntityAttribute.*;
import static com.epam.mangalib.util.MangaLibParameterAttribute.*;
import static com.epam.mangalib.util.MangaLibShowURI.SHOW_AUTHOR_LIST_URI;
import static com.epam.mangalib.validation.Validator.validateDescription;
import static com.epam.mangalib.validation.Validator.validateName;

public class EditAuthorService implements Service {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException, InterruptedException, ValidationException {
        String attribute = req.getParameter(ATTRIBUTE);
        switch (attribute) {
            case AUTHOR_NAME_ATTRIBUTE:
                editAuthorName(req, resp);
                break;
            case AUTHOR_DESCRIPTION_ATTRIBUTE:
                editAuthorDescription(req, resp);
                break;
            case AUTHOR_ATTRIBUTE:
                editAuthor(req, resp);
                break;
            default:
                throw new ValidationException(ATTRIBUTE_ERROR);
        }
    }

    void editAuthorName(HttpServletRequest req, HttpServletResponse resp) throws ValidationException, SQLException, IOException {
        String authorName = validateName(req.getParameter(AUTHOR_NAME_ATTRIBUTE));
        AuthorDAO authorDAO = new AuthorDAO();
        Author author = getAuthor(req, authorDAO);
        author.setName(authorName);
        authorDAO.updateTranslate(author);
        resp.sendRedirect((String) req.getSession().getAttribute(CURRENT_PAGE_ATTRIBUTE));
    }

    void editAuthorDescription(HttpServletRequest req, HttpServletResponse resp) throws ValidationException, SQLException, IOException {
        String authorDescription = validateDescription(req.getParameter(AUTHOR_DESCRIPTION_ATTRIBUTE));
        AuthorDAO authorDAO = new AuthorDAO();
        Author author = getAuthor(req, authorDAO);
        author.setDescription(authorDescription);
        authorDAO.updateTranslate(author);
        resp.sendRedirect((String) req.getSession().getAttribute(CURRENT_PAGE_ATTRIBUTE));
    }

    void editAuthor(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException, ValidationException {
        AuthorDAO authorDAO = new AuthorDAO();
        String action = req.getParameter(ACTION);
        switch (action) {
            case ADD:
                authorDAO.addAuthor();
                resp.sendRedirect((String) req.getSession().getAttribute(CURRENT_PAGE_ATTRIBUTE));
                break;
            case DELETE:
                long genreId = Long.parseLong(req.getParameter(AUTHOR_ID_ATTRIBUTE));
                authorDAO.deleteAuthor(genreId);
                resp.sendRedirect(SHOW_AUTHOR_LIST_URI);
                break;
            default:
                throw new ValidationException(ATTRIBUTE_ERROR);
        }
    }

    Author getAuthor(HttpServletRequest req, AuthorDAO authorDAO) throws SQLException {
        Language language = (Language) req.getSession().getAttribute(CURRENT_LANGUAGE_ATTRIBUTE);
        long languageId = language.getId();
        long authorId = Long.parseLong(req.getParameter(AUTHOR_ID_ATTRIBUTE));
        Author author = authorDAO.getAuthorByIdAndLanguage(authorId, languageId);
        return author;
    }
}
