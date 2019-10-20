package com.epam.mangalib.service;

import com.epam.mangalib.database.ChapterDAO;
import com.epam.mangalib.database.CommentDAO;
import com.epam.mangalib.database.ImageDAO;
import com.epam.mangalib.database.UserDAO;
import com.epam.mangalib.entity.Chapter;
import com.epam.mangalib.entity.Comment;
import com.epam.mangalib.entity.Image;
import com.epam.mangalib.entity.Language;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static com.epam.mangalib.util.MangaLibEntityAttribute.*;
import static com.epam.mangalib.util.MangaLibJSP.CHAPTER_JSP;

public class ShowChapterService implements Service {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        saveCurrentPageInSession(req);
        long chapterId = Long.parseLong(req.getParameter(CHAPTER_ID_ATTRIBUTE));
        Language language = (Language) req.getSession().getAttribute(CURRENT_LANGUAGE_ATTRIBUTE);
        long languageId = language.getId();
        ChapterDAO chapterDAO = new ChapterDAO();
        Chapter chapter = chapterDAO.getChapterByIdAndLanguage(chapterId, languageId);
        ImageDAO imageDAO = new ImageDAO();
        List<Image> imageList = imageDAO.getImageListByChapter(chapterId, languageId);
        chapter.setImageList(imageList);
        CommentDAO commentDAO = new CommentDAO();
        List<Comment> commentList = commentDAO.getCommentListByChapter(chapterId);
        for(Comment comment : commentList) {
            comment.setUser(new UserDAO().getUserByComment(comment.getId()));
        }
        chapter.setCommentList(commentList);
        req.setAttribute(CHAPTER_ATTRIBUTE, chapter);
        req.getRequestDispatcher(CHAPTER_JSP).forward(req, resp);
    }
}
