package com.epam.mangalib.service;

import com.epam.mangalib.database.ChapterDAO;
import com.epam.mangalib.database.ImageDAO;
import com.epam.mangalib.entity.Chapter;
import com.epam.mangalib.entity.Image;
import com.epam.mangalib.entity.Language;
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
import static com.epam.mangalib.util.MangaLibShowURI.SHOW_MAIN_URI;
import static com.epam.mangalib.validation.Validator.validateFile;
import static com.epam.mangalib.validation.Validator.validateName;

public class EditChapterService implements Service {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException, InterruptedException, ValidationException {
        String attribute = req.getParameter(ATTRIBUTE);
        switch (attribute) {
            case CHAPTER_ATTRIBUTE:
                editChapter(req, resp);
                break;
            case CHAPTER_IMAGE_ATTRIBUTE:
                editChapterImage(req, resp);
                break;
            case IMAGE_URL_ATTRIBUTE:
                editImageURL(req, resp);
                break;
            case CHAPTER_NAME_ATTRIBUTE:
                editChapterName(req, resp);
                break;
            default:
                throw new ValidationException(ATTRIBUTE_ERROR);
        }
    }

    void editImageURL(HttpServletRequest req, HttpServletResponse resp) throws ValidationException, SQLException, IOException, ServletException {
        ChapterDAO chapterDAO = new ChapterDAO();
        Chapter chapter = getChapter(req, chapterDAO);
        Language language = (Language) req.getSession().getAttribute(CURRENT_LANGUAGE_ATTRIBUTE);
        long languageId = language.getId();
        long imageId = Long.parseLong(req.getParameter(IMAGE_ID_ATTRIBUTE));
        ImageDAO imageDAO = new ImageDAO();
        Image image = imageDAO.getImageByIdAndLanguage(imageId, languageId);
        Part part = req.getPart(IMAGE_URL_ATTRIBUTE);
        validateFile(part);
        String fileName = part.getSubmittedFileName();
        fileName = "Chapter" + chapter.getId() + "Image" + imageId + "Language" + languageId + fileName.substring(fileName.indexOf('.'));
        String filePath = req.getServletContext().getInitParameter(UPLOAD_FOLDER_CONTEXT_PARAM);
        File file = new File(filePath, fileName);
        Files.copy(part.getInputStream(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        image.setURL(fileName);
        imageDAO.updateTranslate(image);
        resp.sendRedirect((String) req.getSession().getAttribute(CURRENT_PAGE_ATTRIBUTE));
    }

    void editChapterName(HttpServletRequest req, HttpServletResponse resp) throws ValidationException, SQLException, IOException {
        String chapterName = validateName(req.getParameter(CHAPTER_NAME_ATTRIBUTE));
        ChapterDAO chapterDAO = new ChapterDAO();
        Chapter chapter = getChapter(req, chapterDAO);
        chapter.setName(chapterName);
        chapterDAO.updateTranslate(chapter);
        resp.sendRedirect((String) req.getSession().getAttribute(CURRENT_PAGE_ATTRIBUTE));
    }

    void editChapter(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, ValidationException {
        ChapterDAO chapterDAO = new ChapterDAO();
        String action = req.getParameter(ACTION);
        switch (action) {
            case ADD:
                long mangaId = Long.parseLong(req.getParameter(MANGA_ID_ATTRIBUTE));
                chapterDAO.addChapter(mangaId);
                resp.sendRedirect((String) req.getSession().getAttribute(CURRENT_PAGE_ATTRIBUTE));
                break;
            case DELETE:
                long chapterId = Long.parseLong(req.getParameter(CHAPTER_ID_ATTRIBUTE));
                chapterDAO.deleteChapter(chapterId);
                resp.sendRedirect(SHOW_MAIN_URI);
                break;
            default:
                throw new ValidationException(ATTRIBUTE_ERROR);
        }
    }

    void editChapterImage(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, ValidationException {
        String action = req.getParameter(ACTION);
        ImageDAO imageDAO = new ImageDAO();
        switch (action) {
            case ADD:
                long chapterId = Long.parseLong(req.getParameter(CHAPTER_ID_ATTRIBUTE));
                imageDAO.addImage(chapterId);
                resp.sendRedirect((String) req.getSession().getAttribute(CURRENT_PAGE_ATTRIBUTE));
                break;
            case DELETE:
                long imageId = Long.parseLong(req.getParameter(IMAGE_ID_ATTRIBUTE));
                imageDAO.deleteImage(imageId);
                resp.sendRedirect((String) req.getSession().getAttribute(CURRENT_PAGE_ATTRIBUTE));
                break;
            default:
                throw new ValidationException(ATTRIBUTE_ERROR);
        }
    }

    Chapter getChapter(HttpServletRequest req, ChapterDAO chapterDAO) throws SQLException {
        long chapterId = Long.parseLong(req.getParameter(CHAPTER_ID_ATTRIBUTE));
        Language language = (Language) req.getSession().getAttribute(CURRENT_LANGUAGE_ATTRIBUTE);
        Chapter chapter = chapterDAO.getChapterByIdAndLanguage(chapterId,language.getId());
        return chapter;
    }
}
