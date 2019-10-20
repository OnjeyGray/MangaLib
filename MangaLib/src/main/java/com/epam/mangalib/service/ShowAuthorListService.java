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
import java.util.List;

import static com.epam.mangalib.util.MangaLibEntityAttribute.AUTHOR_LIST_ATTRIBUTE;
import static com.epam.mangalib.util.MangaLibEntityAttribute.CURRENT_LANGUAGE_ATTRIBUTE;
import static com.epam.mangalib.util.MangaLibJSP.AUTHOR_LIST_JSP;

public class ShowAuthorListService implements Service {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException, InterruptedException, ValidationException {
        saveCurrentPageInSession(req);
        Language language = (Language) req.getSession().getAttribute(CURRENT_LANGUAGE_ATTRIBUTE);
        AuthorDAO authorDAO = new AuthorDAO();
        List<Author> authorList = authorDAO.getAuthorListByLanguage(language.getId());
        req.setAttribute(AUTHOR_LIST_ATTRIBUTE, authorList);
        req.getRequestDispatcher(AUTHOR_LIST_JSP).forward(req, resp);
    }
}
