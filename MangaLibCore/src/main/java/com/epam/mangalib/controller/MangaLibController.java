package com.epam.mangalib.controller;

import com.epam.mangalib.exception.ValidationException;
import com.epam.mangalib.service.ServiceFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static com.epam.mangalib.util.MangaLibParameterAttribute.ERROR_ATTRIBUTE;

public class MangaLibController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger ROOT_LOGGER = LogManager.getRootLogger();

//    Hello

    @Override
    public void init() throws ServletException {
        super.init();
        ROOT_LOGGER.info("Server started");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, NumberFormatException {
        try {
            ServiceFactory.getInstance().getService(req.getRequestURI()).execute(req, resp);
        } catch (SQLException | InterruptedException | NumberFormatException e) {
            ROOT_LOGGER.error(e);
        } catch (ValidationException e) {
            ROOT_LOGGER.error(e);
            e.sendError(req, resp, ERROR_ATTRIBUTE);
        }
    }
}
