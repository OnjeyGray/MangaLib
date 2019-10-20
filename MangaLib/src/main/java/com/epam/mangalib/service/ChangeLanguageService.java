package com.epam.mangalib.service;

import com.epam.mangalib.database.LanguageDAO;
import com.epam.mangalib.entity.Language;
import com.epam.mangalib.exception.ValidationException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static com.epam.mangalib.util.MangaLibEntityAttribute.*;

public class ChangeLanguageService implements Service {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException, InterruptedException, ValidationException {
        long languageId = Long.parseLong(req.getParameter(LANGUAGE_ID_ATTRIBUTE));
        LanguageDAO languageDAO = new LanguageDAO();
        Language language = null;
        language = languageDAO.getLanguageById(languageId);
        if(language != null) {
            req.getSession().setAttribute(CURRENT_LANGUAGE_ATTRIBUTE, language);
        }
        resp.sendRedirect((String) req.getSession().getAttribute(CURRENT_PAGE_ATTRIBUTE));
    }
}
