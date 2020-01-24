package com.epam.mangalib.service;

import com.epam.mangalib.exception.ValidationException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static com.epam.mangalib.util.MangaLibEntityAttribute.CURRENT_PAGE_ATTRIBUTE;

public interface Service {
    void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException, InterruptedException, ValidationException;

    default void saveCurrentPageInSession(HttpServletRequest req) {
        req.getSession().setAttribute(CURRENT_PAGE_ATTRIBUTE, req.getRequestURI() + "?" + req.getQueryString());
    }
}
