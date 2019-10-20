package com.epam.mangalib.service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static com.epam.mangalib.util.MangaLibEntityAttribute.AUTHORIZED_USER_ATTRIBUTE;
import static com.epam.mangalib.util.MangaLibShowURI.SHOW_MAIN_URI;

public class LogoutService implements Service {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException, InterruptedException {
        req.getSession().setAttribute(AUTHORIZED_USER_ATTRIBUTE, null);
        resp.sendRedirect(SHOW_MAIN_URI);
    }
}
