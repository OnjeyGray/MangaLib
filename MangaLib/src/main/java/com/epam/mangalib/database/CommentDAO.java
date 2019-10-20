package com.epam.mangalib.database;

import com.epam.mangalib.database.jsql.SQLDAO;
import com.epam.mangalib.database.jsql.SQLDelete;
import com.epam.mangalib.database.jsql.SQLInsert;
import com.epam.mangalib.database.jsql.SQLSelect;
import com.epam.mangalib.entity.Comment;

import java.sql.SQLException;
import java.util.List;

import static com.epam.mangalib.util.MangaLibDAOConstant.*;

public class CommentDAO extends SQLDAO<Comment> {

    public List<Comment> getCommentListByChapter(long chapterId) throws SQLException {
        return mapListToObjectList(new SQLSelect().select(ALL).from(COMMENT_TABLE).where(CHAPTER_ID).
                eq(chapterId).orderBy(COMMENT_ID).executeSelect());
    }

    public void addComment(Comment comment) throws SQLException {
        new SQLInsert().insertInto(COMMENT_TABLE).variables(CHAPTER_ID, USER_ID, COMMENT_CONTENT).
                values(comment.getChapterId(), comment.getUserId(), comment.getContent()).executeInsert();
    }

    public void deleteComment(long commentId) throws SQLException {
        new SQLDelete().deleteFrom(COMMENT_TABLE).where(COMMENT_ID).eq(commentId).executeDelete();
    }
}

