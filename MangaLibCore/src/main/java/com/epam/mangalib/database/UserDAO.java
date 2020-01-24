package com.epam.mangalib.database;

import com.epam.mangalib.database.jsql.*;
import com.epam.mangalib.entity.User;

import java.sql.SQLException;
import java.util.List;

import static com.epam.mangalib.util.MangaLibDAOConstant.*;

public class UserDAO extends SQLDAO<User>{

    public List<User> getUserList() throws SQLException {
        return mapListToObjectList(new SQLSelect().
                select(ALL).from(USER_TABLE).
                executeSelect());
    }

    public List<User> getUserListByManga(long mangaId) throws SQLException {
        return mapListToObjectList(new SQLSelect().
                select(ALL).from(USER_TABLE).innerJoin(MANGA_USER_TABLE).using(USER_ID).
                where(MANGA_ID).eq(mangaId).orderBy(USER_NAME).executeSelect());
    }

    public User getUserByComment(long commentId) throws SQLException{
        return  mapListToObject(new SQLSelect().
                select(ALL).from(USER_TABLE).where(USER_ID).in(new SQLSelect().
                select(USER_ID).from(COMMENT_TABLE).where(COMMENT_ID).eq(commentId).
                executeInner()).executeSelect());
    }

    public User getUserById(long id) throws SQLException {
        return mapListToObject(new SQLSelect().
                select(ALL).from(USER_TABLE).where(USER_ID).eq(id).
                executeSelect());
    }

    public User getUserByName(String name) throws SQLException  {
        return mapListToObject(new SQLSelect().
                select(ALL).from(USER_TABLE).where(USER_NAME).eq(name).
                executeSelect());
    }

    public User getUserByEmail(String email) throws SQLException {
        return mapListToObject(new SQLSelect().
                select(ALL).from(USER_TABLE).where(USER_EMAIL).eq(email).
                executeSelect());
    }

    public void updateUser(User user) throws SQLException {
        new SQLUpdate().update(USER_TABLE).set(USER_NAME).eq(user.getName()).
                comma(USER_EMAIL).eq(user.getEmail()).comma(USER_PASSWORD).eq(user.getPassword()).
                comma(USER_ROLE_ID).eq(user.getRole().getId()).
                comma(USER_AVATAR_URL).eq(user.getAvatarURL()).
                comma(USER_REGISTRATION_DATE).eq(user.getRegistrationDate()).
                where(USER_ID).eq(user.getId()).executeUpdate();
    }

    public void addUser(User user) throws SQLException {
        long next = (long) mapListToObject(new SQLCall().select("@next := max(USER_ID) + 1").as(NEXT).from(USER_TABLE).
                executeCall()).get(NEXT);
        long userId = (next == 0 ? 1 : next);
        new SQLAlter().alterTable(USER_TABLE).param(AUTO_INCREMENT).eq(userId).executeAlter();
        new SQLInsert().insertInto(USER_TABLE).variables(USER_ID, USER_NAME, USER_EMAIL, USER_PASSWORD,
                USER_ROLE_ID, USER_AVATAR_URL, USER_REGISTRATION_DATE).values(userId, user.getName(),
                user.getEmail(), user.getPassword(), user.getRole().getId(), user.getAvatarURL(),
                user.getRegistrationDate()).executeInsert();
    }

    public void deleteUserById(long id) throws SQLException {
        new SQLDelete().deleteFrom(USER_TABLE).where(USER_ID).eq(id).executeDelete();
    }
}

