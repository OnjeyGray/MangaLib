package com.epam.mangalib.filter;

import com.epam.mangalib.database.LanguageDAO;
import com.epam.mangalib.entity.Language;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static com.epam.mangalib.filter.AccesFilter.ACTIVE_INIT_PARAM;
import static com.epam.mangalib.util.MangaLibEntityAttribute.CURRENT_LANGUAGE_ATTRIBUTE;
import static com.epam.mangalib.util.MangaLibEntityAttribute.CURRENT_PAGE_ATTRIBUTE;
import static com.epam.mangalib.util.MangaLibShowURI.SHOW_MAIN_URI;

public class PreloadFilter implements Filter {
    private static final Logger ROOT_LOGGER = LogManager.getRootLogger();
    private boolean active;

    @Override
    public void init(FilterConfig filterConfig) {
        active = Boolean.parseBoolean(filterConfig.getInitParameter(ACTIVE_INIT_PARAM));
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        if(active) {
            Language currentLanguage = (Language) req.getSession().getAttribute(CURRENT_LANGUAGE_ATTRIBUTE);
            if (currentLanguage == null) {
                List<Language> languageList = null;
                try {
                    languageList = new LanguageDAO().getLanguageList();
                } catch (SQLException e) {
                    ROOT_LOGGER.error(e);
                }
                currentLanguage = languageList.get(0);
                req.getSession().setAttribute(CURRENT_LANGUAGE_ATTRIBUTE, currentLanguage);
            }
            if (req.getSession().getAttribute(CURRENT_PAGE_ATTRIBUTE) == null) {
                req.getSession().setAttribute(CURRENT_PAGE_ATTRIBUTE, SHOW_MAIN_URI);
            }
            filterChain.doFilter(req, resp);
        }
    }
}
