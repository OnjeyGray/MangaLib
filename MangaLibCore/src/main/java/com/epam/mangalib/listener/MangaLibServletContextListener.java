package com.epam.mangalib.listener;

import com.epam.mangalib.database.LanguageDAO;
import com.epam.mangalib.entity.Language;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.epam.mangalib.util.MangaLibEntityAttribute.LANGUAGE_LIST_ATTRIBUTE;

public class MangaLibServletContextListener implements ServletContextListener {
    private static final Logger ROOT_LOGGER = LogManager.getRootLogger();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        LanguageDAO languageDAO = new LanguageDAO();
        List<Language> languageList = new ArrayList<>();
        try {
            languageList = languageDAO.getLanguageList();
        } catch (SQLException e) {
            ROOT_LOGGER.error(e);
        }
        servletContext.setAttribute(LANGUAGE_LIST_ATTRIBUTE, languageList);
    }
}
