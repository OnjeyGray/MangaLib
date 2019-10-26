package com.epam.mangalib.service;

import com.epam.mangalib.database.MangaDAO;
import com.epam.mangalib.entity.Language;
import com.epam.mangalib.entity.Manga;
import com.epam.mangalib.exception.ValidationException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;

import static com.epam.mangalib.util.MangaLibConstant.UPLOAD_FOLDER_CONTEXT_PARAM;
import static com.epam.mangalib.util.MangaLibEntityAttribute.*;
import static com.epam.mangalib.util.MangaLibParameterAttribute.*;
import static com.epam.mangalib.util.MangaLibShowURI.SHOW_MANGA_LIST_URI;
import static com.epam.mangalib.validation.Validator.*;

public class EditMangaService implements Service {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException, InterruptedException, ValidationException {
        String attribute = req.getParameter(ATTRIBUTE);
        switch (attribute) {
            case MANGA_NAME_ATTRIBUTE:
                editMangaName(req, resp);
                break;
            case MANGA_DESCRIPTION_ATTRIBUTE:
                editMangaDescription(req, resp);
                break;
            case MANGA_IMG_ATTRIBUTE:
                editMangaImg(req, resp);
                break;
            case MANGA_GENRE_ATTRIBUTE:
                editMangaGenre(req, resp);
                break;
            case MANGA_ARTIST_ATTRIBUTE:
                editMangaArtist(req, resp);
                break;
            case MANGA_AUTHOR_ATTRIBUTE:
                editMangaAuthor(req, resp);
                break;
            case MANGA_ATTRIBUTE:
                editManga(req, resp);
                break;
            default:
                throw new ValidationException(ATTRIBUTE_ERROR);
        }
    }

    void editMangaName(HttpServletRequest req, HttpServletResponse resp) throws ValidationException, SQLException, IOException {
        String mangaName = validateName(req.getParameter(MANGA_NAME_ATTRIBUTE));
        MangaDAO mangaDAO = new MangaDAO();
        Manga manga = getManga(req, mangaDAO);
        manga.setName(mangaName);
        mangaDAO.updateTranslate(manga);
        resp.sendRedirect((String) req.getSession().getAttribute(CURRENT_PAGE_ATTRIBUTE));
    }

    void editMangaDescription(HttpServletRequest req, HttpServletResponse resp) throws ValidationException, SQLException, IOException {
        String mangaDescription = validateDescription(req.getParameter(MANGA_DESCRIPTION_ATTRIBUTE));
        MangaDAO mangaDAO = new MangaDAO();
        Manga manga = getManga(req, mangaDAO);
        manga.setDescription(mangaDescription);
        mangaDAO.updateTranslate(manga);
        resp.sendRedirect((String) req.getSession().getAttribute(CURRENT_PAGE_ATTRIBUTE));
    }

    void editMangaImg(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException, SQLException, ValidationException {
        MangaDAO mangaDAO = new MangaDAO();
        Manga manga = getManga(req, mangaDAO);
        Part part = req.getPart(MANGA_IMG_ATTRIBUTE);
        validateFile(part);
        String fileName = part.getSubmittedFileName();
        fileName = "MangaImg" + manga.getId() + fileName.substring(fileName.indexOf('.'));
        String filePath = req.getServletContext().getInitParameter(UPLOAD_FOLDER_CONTEXT_PARAM);
        File file = new File(filePath, fileName);
        Files.copy(part.getInputStream(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        manga.setImgURL(fileName);
        mangaDAO.updateImg(manga);
        resp.sendRedirect((String) req.getSession().getAttribute(CURRENT_PAGE_ATTRIBUTE));
    }

    void editMangaGenre(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, ValidationException {
        MangaDAO mangaDAO = new MangaDAO();
        String action = req.getParameter(ACTION);
        long mangaId = Long.parseLong(req.getParameter(MANGA_ID_ATTRIBUTE));
        long genreId = Long.parseLong(req.getParameter(GENRE_ID_ATTRIBUTE));
        switch(action) {
            case ADD:
                mangaDAO.addGenre(mangaId, genreId);
                resp.sendRedirect((String) req.getSession().getAttribute(CURRENT_PAGE_ATTRIBUTE));
                break;
            case DELETE:
                mangaDAO.deleteGenre(mangaId, genreId);
                resp.sendRedirect((String) req.getSession().getAttribute(CURRENT_PAGE_ATTRIBUTE));
                break;
            default:
                throw new ValidationException(ATTRIBUTE_ERROR);
        }
    }

    void editMangaArtist(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, ValidationException {
        MangaDAO mangaDAO = new MangaDAO();
        String action = req.getParameter(ACTION);
        long mangaId = Long.parseLong(req.getParameter(MANGA_ID_ATTRIBUTE));
        long artistId = Long.parseLong(req.getParameter(ARTIST_ID_ATTRIBUTE));
        switch(action) {
            case ADD:
                mangaDAO.addArtist(mangaId, artistId);
                resp.sendRedirect((String) req.getSession().getAttribute(CURRENT_PAGE_ATTRIBUTE));
                break;
            case DELETE:
                mangaDAO.deleteArtist(mangaId, artistId);
                resp.sendRedirect((String) req.getSession().getAttribute(CURRENT_PAGE_ATTRIBUTE));
                break;
            default:
                throw new ValidationException(ATTRIBUTE_ERROR);
        }
    }

    void editMangaAuthor(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, ValidationException {
        MangaDAO mangaDAO = new MangaDAO();
        String action = req.getParameter(ACTION);
        long mangaId = Long.parseLong(req.getParameter(MANGA_ID_ATTRIBUTE));
        long authorId = Long.parseLong(req.getParameter(AUTHOR_ID_ATTRIBUTE));
        switch(action) {
            case ADD:
                mangaDAO.addAuthor(mangaId, authorId);
                resp.sendRedirect((String) req.getSession().getAttribute(CURRENT_PAGE_ATTRIBUTE));
                break;
            case DELETE:
                mangaDAO.deleteAuthor(mangaId, authorId);
                resp.sendRedirect((String) req.getSession().getAttribute(CURRENT_PAGE_ATTRIBUTE));
                break;
            default:
                throw new ValidationException(ATTRIBUTE_ERROR);
        }
    }

    void editManga(HttpServletRequest req, HttpServletResponse resp) throws IOException, SQLException, ValidationException {
        MangaDAO mangaDAO = new MangaDAO();
        String action = req.getParameter(ACTION);
        switch(action) {
            case ADD:
                mangaDAO.addManga();
                resp.sendRedirect((String) req.getSession().getAttribute(CURRENT_PAGE_ATTRIBUTE));
                break;
            case DELETE:
                long mangaId = Long.parseLong(req.getParameter(MANGA_ID_ATTRIBUTE));
                mangaDAO.deleteManga(mangaId);
                resp.sendRedirect(SHOW_MANGA_LIST_URI);
                break;
            default:
                throw new ValidationException(ATTRIBUTE_ERROR);
        }
    }

    Manga getManga(HttpServletRequest req, MangaDAO mangaDAO) throws SQLException {
        Language language = (Language) req.getSession().getAttribute(CURRENT_LANGUAGE_ATTRIBUTE);
        long languageId = language.getId();
        long mangaId = Long.parseLong(req.getParameter(MANGA_ID_ATTRIBUTE));
        Manga manga = mangaDAO.getMangaByIdAndLanguage(mangaId, languageId);
        return manga;
    }
}
