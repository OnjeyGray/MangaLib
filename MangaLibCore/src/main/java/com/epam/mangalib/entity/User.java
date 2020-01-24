package com.epam.mangalib.entity;

import com.epam.mangalib.database.jsql.SQLObject;
import com.epam.mangalib.enumeration.Role;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static com.epam.mangalib.util.MangaLibDAOConstant.*;

public class User extends SQLObject {
    private List<Manga> mangaList;

    public User() {}

    public User(Map<String, Object> objectMap) {
        super(objectMap);
    }

    public long getId() {
        return (long) get(USER_ID);
    }

    public void setId(long id) {
        set(USER_ID, id);
    }

    public String getName() {
        return (String) get(USER_NAME);
    }

    public void setName(String name) {
        set(USER_NAME, name);
    }

    public String getEmail() {
        return (String) get(USER_EMAIL);
    }

    public void setEmail(String email) {
        set(USER_EMAIL, email);
    }

    public String getPassword() {
        return (String) get(USER_PASSWORD);
    }

    public void setPassword(String password) {
        set(USER_PASSWORD, password);
    }

    public Role getRole() {
        return Role.getRoleByRoleId((Long) get(USER_ROLE_ID));
    }

    public void setRole(Role role) {
        set(USER_ROLE_ID, role.getId());
    }

    public String getAvatarURL() {
        return (String) get(USER_AVATAR_URL);
    }

    public void setAvatarURL(String avatarURL) {
        set(USER_AVATAR_URL, avatarURL);
    }

    public LocalDate getRegistrationDate() {
        return LocalDate.parse(get(USER_REGISTRATION_DATE).toString());
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        set(USER_REGISTRATION_DATE, registrationDate);
    }

    public List<Manga> getMangaList() {
        return mangaList;
    }

    public void setMangaList(List<Manga> mangaList) {
        this.mangaList = mangaList;
    }

}
