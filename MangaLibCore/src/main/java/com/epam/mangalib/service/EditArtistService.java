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

import static com.epam.mangalib.util.MangaLibEntityAttribute.*;
import static com.epam.mangalib.util.MangaLibParameterAttribute.*;
import static com.epam.mangalib.util.MangaLibShowURI.SHOW_ARTIST_LIST_URI;
import static com.epam.mangalib.validation.Validator.validateDescription;
import static com.epam.mangalib.validation.Validator.validateName;

public class EditArtistService implements Service {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException, InterruptedException, ValidationException {
        String attribute = req.getParameter(ATTRIBUTE);
        switch (attribute) {
            case ARTIST_NAME_ATTRIBUTE:
                editAuthorName(req, resp);
                break;
            case ARTIST_DESCRIPTION_ATTRIBUTE:
                editAuthorDescription(req, resp);
                break;
            case ARTIST_ATTRIBUTE:
                editArtist(req, resp);
                break;
            default:
                throw new ValidationException(ATTRIBUTE_ERROR);
        }
    }

    void editAuthorName(HttpServletRequest req, HttpServletResponse resp) throws ValidationException, SQLException, IOException {
        String artistName = validateName(req.getParameter(ARTIST_NAME_ATTRIBUTE));
        ArtistDAO artistDAO = new ArtistDAO();
        Artist artist = getArtist(req, artistDAO);
        artist.setName(artistName);
        artistDAO.updateTranslate(artist);
        resp.sendRedirect((String) req.getSession().getAttribute(CURRENT_PAGE_ATTRIBUTE));
    }

    void editAuthorDescription(HttpServletRequest req, HttpServletResponse resp) throws ValidationException, SQLException, IOException {
        String artistDescription = validateDescription(req.getParameter(ARTIST_DESCRIPTION_ATTRIBUTE));
        ArtistDAO artistDAO = new ArtistDAO();
        Artist artist = getArtist(req, artistDAO);
        artist.setDescription(artistDescription);
        artistDAO.updateTranslate(artist);
        resp.sendRedirect((String) req.getSession().getAttribute(CURRENT_PAGE_ATTRIBUTE));
    }

    void editArtist(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException, ValidationException {
        ArtistDAO artistDAO = new ArtistDAO();
        String action = req.getParameter(ACTION);
        switch (action) {
            case ADD:
                artistDAO.addArtist();
                resp.sendRedirect((String) req.getSession().getAttribute(CURRENT_PAGE_ATTRIBUTE));
                break;
            case DELETE:
                long genreId = Long.parseLong(req.getParameter(ARTIST_ID_ATTRIBUTE));
                artistDAO.deleteArtist(genreId);
                resp.sendRedirect(SHOW_ARTIST_LIST_URI);
                break;
            default:
                throw new ValidationException(ATTRIBUTE_ERROR);
        }
    }

    Artist getArtist(HttpServletRequest req, ArtistDAO artistDAO) throws SQLException {
        Language language = (Language) req.getSession().getAttribute(CURRENT_LANGUAGE_ATTRIBUTE);
        long languageId = language.getId();
        long artistId = Long.parseLong(req.getParameter(ARTIST_ID_ATTRIBUTE));
        Artist artist = artistDAO.getArtistByIdAndLanguage(artistId, languageId);
        return artist;
    }
}
