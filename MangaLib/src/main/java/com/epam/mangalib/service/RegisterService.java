package com.epam.mangalib.service;

import com.epam.mangalib.database.UserDAO;
import com.epam.mangalib.entity.User;
import com.epam.mangalib.enumeration.Role;
import com.epam.mangalib.exception.ValidationException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

import static com.epam.mangalib.security.Password.hashPassword;
import static com.epam.mangalib.util.MangaLibEntityAttribute.*;
import static com.epam.mangalib.util.MangaLibParameterAttribute.*;
import static com.epam.mangalib.util.MangaLibShowURI.SHOW_MAIN_URI;
import static com.epam.mangalib.validation.Validator.*;

public class RegisterService implements Service {
    public static final String DEFAULT_USER_AVATAR = "defaultUserAvatar.jpg";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException, InterruptedException, ValidationException {
        String userName = validateName(req.getParameter(USER_NAME_ATTRIBUTE));
        String userEmail = validateUserEmail(req.getParameter(USER_EMAIL_ATTRIBUTE));
        String userPassword = validateUserPassword(req.getParameter(USER_PASSWORD_ATTRIBUTE));
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUserByName(userName);
        if(user == null) {
            user = userDAO.getUserByEmail(userEmail);
            if(user == null) {
                user = setUser(userName, userEmail, userPassword);
                req.getSession().setAttribute(AUTHORIZED_USER_ATTRIBUTE, user);
                req.getRequestDispatcher(SHOW_MAIN_URI).forward(req, resp);
            } else {
                req.setAttribute(REGISTER_ERROR_ATTRIBUTE, ERROR_EMAIL_ALREADY_USED);
                req.getRequestDispatcher((String) req.getSession().getAttribute(CURRENT_PAGE_ATTRIBUTE)).forward(req, resp);
            }
        } else {
            req.setAttribute(REGISTER_ERROR_ATTRIBUTE, ERROR_USER_ALREADY_EXIST);
            req.getRequestDispatcher((String) req.getSession().getAttribute(CURRENT_PAGE_ATTRIBUTE)).forward(req, resp);
        }
    }

    public User setUser(String userName, String userEmail, String userPassword) throws SQLException {
        User user = new User();
        user.setName(userName);
        user.setEmail(userEmail);
        user.setPassword(hashPassword(userPassword));
        user.setRole(Role.USER);
        user.setAvatarURL(DEFAULT_USER_AVATAR);
        user.setRegistrationDate(LocalDate.now());
        UserDAO userDAO = new UserDAO();
        userDAO.addUser(user);
        user = userDAO.getUserByName(userName);
        return user;
    }
}
