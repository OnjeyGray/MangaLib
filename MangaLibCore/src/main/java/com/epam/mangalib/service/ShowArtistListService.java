package com.epam.mangalib.service;

import com.epam.mangalib.database.ArtistDAO;
import com.epam.mangalib.entity.Artist;
import com.epam.mangalib.entity.Language;
import com.epam.mangalib.exception.ValidationException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static com.epam.mangalib.util.MangaLibEntityAttribute.ARTIST_LIST_ATTRIBUTE;
import static com.epam.mangalib.util.MangaLibEntityAttribute.CURRENT_LANGUAGE_ATTRIBUTE;
import static com.epam.mangalib.util.MangaLibJSP.ARTIST_LIST_JSP;

public class ShowArtistListService implements Service {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException, InterruptedException, ValidationException {
        saveCurrentPageInSession(req);
        Language language = (Language) req.getSession().getAttribute(CURRENT_LANGUAGE_ATTRIBUTE);
        ArtistDAO artistDAO = new ArtistDAO();
        List<Artist> artistList = artistDAO.getArtistListByLanguage(language.getId());
        req.setAttribute(ARTIST_LIST_ATTRIBUTE, artistList);
        req.getRequestDispatcher(ARTIST_LIST_JSP).forward(req, resp);
    }
}
