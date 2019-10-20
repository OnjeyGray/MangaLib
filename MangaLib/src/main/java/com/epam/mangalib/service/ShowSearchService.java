package com.epam.mangalib.service;

import com.epam.mangalib.database.ArtistDAO;
import com.epam.mangalib.database.AuthorDAO;
import com.epam.mangalib.database.GenreDAO;
import com.epam.mangalib.database.MangaDAO;
import com.epam.mangalib.entity.Artist;
import com.epam.mangalib.entity.Author;
import com.epam.mangalib.entity.Genre;
import com.epam.mangalib.entity.Manga;
import com.epam.mangalib.exception.ValidationException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static com.epam.mangalib.util.MangaLibEntityAttribute.*;
import static com.epam.mangalib.util.MangaLibJSP.SEARCH_JSP;
import static com.epam.mangalib.util.MangaLibParameterAttribute.SEARCH_ATTRIBUTE;
import static com.epam.mangalib.validation.Validator.validateName;

public class ShowSearchService implements Service {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException, ValidationException {
        saveCurrentPageInSession(req);
        if(req.getParameter(SEARCH_ATTRIBUTE) != null) {
            String search = validateName(req.getParameter(SEARCH_ATTRIBUTE));
            MangaDAO mangaDAO = new MangaDAO();
            List<Manga> mangaList = mangaDAO.search(search);
            req.setAttribute(MANGA_LIST_ATTRIBUTE, mangaList);
            GenreDAO genreDAO = new GenreDAO();
            List<Genre> genreList = genreDAO.search(search);
            req.setAttribute(GENRE_LIST_ATTRIBUTE, genreList);
            AuthorDAO authorDAO = new AuthorDAO();
            List<Author> authorList = authorDAO.search(search);
            req.setAttribute(AUTHOR_LIST_ATTRIBUTE, authorList);
            ArtistDAO artistDAO = new ArtistDAO();
            List<Artist> artistList = artistDAO.search(search);
            req.setAttribute(ARTIST_LIST_ATTRIBUTE, artistList);
        }
        req.getRequestDispatcher(SEARCH_JSP).forward(req, resp);
    }
}
