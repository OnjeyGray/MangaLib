package com.epam.mangalib.service;

import com.epam.mangalib.database.MangaDAO;
import com.epam.mangalib.database.UserDAO;
import com.epam.mangalib.entity.Language;
import com.epam.mangalib.entity.Manga;
import com.epam.mangalib.entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static com.epam.mangalib.util.MangaLibEntityAttribute.*;
import static com.epam.mangalib.util.MangaLibJSP.USER_JSP;

public class ShowUserService implements Service {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        saveCurrentPageInSession(req);
        long userId = Long.parseLong(req.getParameter(USER_ID_ATTRIBUTE));
        Language language = (Language) req.getSession().getAttribute(CURRENT_LANGUAGE_ATTRIBUTE);
        long languageId = language.getId();
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUserById(userId);
        List<Manga> mangaList = new MangaDAO().getMangaListByUser(userId, languageId);
        user.setMangaList(mangaList);
        req.setAttribute(USER_ATTRIBUTE, user);
        req.getRequestDispatcher(USER_JSP).forward(req, resp);
    }
}
