package com.epam.mangalib.service;

import com.epam.mangalib.database.ArtistDAO;
import com.epam.mangalib.database.MangaDAO;
import com.epam.mangalib.entity.Artist;
import com.epam.mangalib.entity.Language;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static com.epam.mangalib.util.MangaLibEntityAttribute.*;
import static com.epam.mangalib.util.MangaLibJSP.ARTIST_JSP;

public class ShowArtistService implements Service {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        saveCurrentPageInSession(req);
        long artistId = Long.parseLong(req.getParameter(ARTIST_ID_ATTRIBUTE));
        Language language = (Language) req.getSession().getAttribute(CURRENT_LANGUAGE_ATTRIBUTE);
        long languageId = language.getId();
        ArtistDAO artistDAO = new ArtistDAO();
        Artist artist = artistDAO.getArtistByIdAndLanguage(artistId, languageId);
        MangaDAO mangaDAO = new MangaDAO();
        artist.setMangaList(mangaDAO.getMangaListByArtist(artistId, languageId));
        req.setAttribute(ARTIST_ATTRIBUTE, artist);
        req.getRequestDispatcher(ARTIST_JSP).forward(req, resp);
    }
}
