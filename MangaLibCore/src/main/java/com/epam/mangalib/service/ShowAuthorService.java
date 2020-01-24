package com.epam.mangalib.service;

import com.epam.mangalib.database.AuthorDAO;
import com.epam.mangalib.database.MangaDAO;
import com.epam.mangalib.entity.Author;
import com.epam.mangalib.entity.Language;
import com.epam.mangalib.exception.ValidationException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static com.epam.mangalib.util.MangaLibEntityAttribute.*;
import static com.epam.mangalib.util.MangaLibJSP.AUTHOR_JSP;

public class ShowAuthorService implements Service {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException, InterruptedException, ValidationException {
        saveCurrentPageInSession(req);
        long authorId = Long.parseLong(req.getParameter(AUTHOR_ID_ATTRIBUTE));
        Language language = (Language) req.getSession().getAttribute(CURRENT_LANGUAGE_ATTRIBUTE);
        long languageId = language.getId();
        AuthorDAO authorDAO = new AuthorDAO();
        Author author = authorDAO.getAuthorByIdAndLanguage(authorId, languageId);
        MangaDAO mangaDAO = new MangaDAO();
        author.setMangaList(mangaDAO.getMangaListByAuthor(authorId, languageId));
        req.setAttribute(AUTHOR_ATTRIBUTE, author);
        req.getRequestDispatcher(AUTHOR_JSP).forward(req, resp);
    }
}
