package com.epam.mangalib.service;

import com.epam.mangalib.database.MangaDAO;
import com.epam.mangalib.exception.ValidationException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static com.epam.mangalib.util.MangaLibEntityAttribute.*;
import static com.epam.mangalib.util.MangaLibParameterAttribute.*;

public class EditFavoriteService implements Service {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException, InterruptedException, ValidationException {
        String action = req.getParameter(ACTION);
        MangaDAO mangaDAO = new MangaDAO();
        long mangaId = Long.parseLong(req.getParameter(MANGA_ID_ATTRIBUTE));
        long userId = Long.parseLong(req.getParameter(USER_ID_ATTRIBUTE));
        switch (action) {
            case ADD:
                mangaDAO.favorite(mangaId, userId);
                resp.sendRedirect((String) req.getSession().getAttribute(CURRENT_PAGE_ATTRIBUTE));
                break;
            case DELETE:
                mangaDAO.unfavorite(mangaId, userId);
                resp.sendRedirect((String) req.getSession().getAttribute(CURRENT_PAGE_ATTRIBUTE));
                break;
            default:
                throw new ValidationException(ATTRIBUTE_ERROR);
        }
    }
}
