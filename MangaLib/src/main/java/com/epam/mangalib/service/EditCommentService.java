package com.epam.mangalib.service;

import com.epam.mangalib.database.CommentDAO;
import com.epam.mangalib.entity.Comment;
import com.epam.mangalib.exception.ValidationException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static com.epam.mangalib.util.MangaLibEntityAttribute.*;
import static com.epam.mangalib.util.MangaLibParameterAttribute.*;
import static com.epam.mangalib.validation.Validator.validateDescription;

public class EditCommentService implements Service {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException, InterruptedException, ValidationException {
        String action = req.getParameter(ACTION);
        CommentDAO commentDAO = new CommentDAO();
        switch (action) {
            case ADD:

                String content = validateDescription(req.getParameter(COMMENT_CONTENT));
                long chapterId = Long.parseLong(req.getParameter(CHAPTER_ID_ATTRIBUTE));
                long userId = Long.parseLong(req.getParameter(USER_ID_ATTRIBUTE));
                Comment comment = new Comment();
                comment.setChapterId(chapterId);
                comment.setUserId(userId);
                comment.setContent(content);
                commentDAO.addComment(comment);
                resp.sendRedirect((String) req.getSession().getAttribute(CURRENT_PAGE_ATTRIBUTE));
                break;
            case DELETE:
                long commentId = Long.parseLong(req.getParameter(COMMENT_ID));
                commentDAO.deleteComment(commentId);
                resp.sendRedirect((String) req.getSession().getAttribute(CURRENT_PAGE_ATTRIBUTE));
                break;
        }
    }
}
