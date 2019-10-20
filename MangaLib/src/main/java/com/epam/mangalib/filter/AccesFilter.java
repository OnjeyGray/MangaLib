package com.epam.mangalib.filter;

import com.epam.mangalib.entity.User;
import com.epam.mangalib.enumeration.Role;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.epam.mangalib.util.MangaLibEntityAttribute.AUTHORIZED_USER_ATTRIBUTE;
import static com.epam.mangalib.util.MangaLibJSP.ACCESS_DENIED_JSP;
import static com.epam.mangalib.util.MangaLibShowURI.*;
import static com.epam.mangalib.util.MangaLibURI.*;

public class AccesFilter implements Filter {
    public static final String ACTIVE_INIT_PARAM = "active";
    private static final Map<String, Role> ACCESS_MAP = new HashMap<>();
    private boolean active;

    @Override
    public void init(FilterConfig filterConfig) {
        active = Boolean.parseBoolean(filterConfig.getInitParameter(ACTIVE_INIT_PARAM));
        ACCESS_MAP.put(SHOW_MAIN_URI, Role.GUEST);
        ACCESS_MAP.put(SHOW_CHAPTER_URI, Role.GUEST);
        ACCESS_MAP.put(SHOW_USER_URI, Role.USER);
        ACCESS_MAP.put(SHOW_SEARCH_URI, Role.GUEST);
        ACCESS_MAP.put(SHOW_LOGIN_URI, Role.GUEST);
        ACCESS_MAP.put(SHOW_REGISTER_URI, Role.GUEST);
        ACCESS_MAP.put(SHOW_MANGA_URI, Role.GUEST);
        ACCESS_MAP.put(SHOW_ARTIST_URI, Role.GUEST);
        ACCESS_MAP.put(SHOW_AUTHOR_URI, Role.GUEST);
        ACCESS_MAP.put(SHOW_GENRE_URI, Role.GUEST);
        ACCESS_MAP.put(SHOW_USER_LIST_URI, Role.ADMIN);
        ACCESS_MAP.put(SHOW_MANGA_LIST_URI, Role.GUEST);
        ACCESS_MAP.put(SHOW_ARTIST_LIST_URI, Role.GUEST);
        ACCESS_MAP.put(SHOW_AUTHOR_LIST_URI, Role.GUEST);
        ACCESS_MAP.put(SHOW_GENRE_LIST_URI, Role.GUEST);
        ACCESS_MAP.put(LOAD_FILE_URI, Role.GUEST);
        ACCESS_MAP.put(REGISTER_URI, Role.GUEST);
        ACCESS_MAP.put(LOGIN_URI, Role.GUEST);
        ACCESS_MAP.put(LOGOUT_URI, Role.USER);
        ACCESS_MAP.put(EDIT_USER_URI, Role.USER);
        ACCESS_MAP.put(EDIT_MANGA_URI, Role.ADMIN);
        ACCESS_MAP.put(EDIT_GENRE_URI, Role.ADMIN);
        ACCESS_MAP.put(EDIT_ARTIST_URI, Role.ADMIN);
        ACCESS_MAP.put(EDIT_AUTHOR_URI, Role.ADMIN);
        ACCESS_MAP.put(EDIT_FAVORITE_URI, Role.USER);
        ACCESS_MAP.put(EDIT_CHAPTER_URI, Role.ADMIN);
        ACCESS_MAP.put(EDIT_COMMENT_URI, Role.USER);
        ACCESS_MAP.put(CHANGE_LANGUAGE_URI, Role.GUEST);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        User authorizedUser = (User) req.getSession().getAttribute(AUTHORIZED_USER_ATTRIBUTE);
        Role currentRole;
        if(authorizedUser == null) {
            currentRole = Role.GUEST;
        } else {
            currentRole = authorizedUser.getRole();
        }
        if(active) {
            Role accessRole = ACCESS_MAP.get(req.getRequestURI());
            if(accessRole != null) {
                if(currentRole.getId() >= accessRole.getId()) {
                    filterChain.doFilter(req, resp);
                } else {
                    req.getRequestDispatcher(ACCESS_DENIED_JSP).forward(req, resp);
                }
            } else {
                req.getRequestDispatcher(ACCESS_DENIED_JSP).forward(req, resp);
            }
        }
    }
}
