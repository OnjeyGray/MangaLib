package com.epam.mangalib.service;

import com.epam.mangalib.database.UserDAO;
import com.epam.mangalib.entity.User;
import com.epam.mangalib.exception.ValidationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;

import static com.epam.mangalib.security.Password.checkPassword;
import static com.epam.mangalib.security.Password.hashPassword;
import static com.epam.mangalib.util.MangaLibConstant.UPLOAD_FOLDER_CONTEXT_PARAM;
import static com.epam.mangalib.util.MangaLibEntityAttribute.*;
import static com.epam.mangalib.util.MangaLibParameterAttribute.*;
import static com.epam.mangalib.util.MangaLibShowURI.SHOW_MAIN_URI;
import static com.epam.mangalib.validation.Validator.*;

public class EditUserService implements Service {
    private static final Logger ROOT_LOGGER = LogManager.getRootLogger();

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException, InterruptedException, ValidationException {
        String attribute = req.getParameter(ATTRIBUTE);
        switch (attribute) {
            case USER_NAME_ATTRIBUTE:
                editUserName(req, resp);
                break;
            case USER_EMAIL_ATTRIBUTE:
                editUserEmail(req, resp);
                break;
            case USER_PASSWORD_ATTRIBUTE:
                editUserPassword(req, resp);
                break;
            case USER_AVATAR_ATTRIBUTE:
                editUserAvatar(req, resp);
                break;
            case USER_ATTRIBUTE:
                editUser(req, resp);
                break;
        }
    }

    void editUserName(HttpServletRequest req, HttpServletResponse resp) throws ValidationException, SQLException, IOException, ServletException {
        String userName = validateName(req.getParameter(USER_NAME_ATTRIBUTE));
        long userId = Long.parseLong(req.getParameter(USER_ID_ATTRIBUTE));
        User currentUser = new UserDAO().getUserById(userId);
        if(!currentUser.getName().equals(userName)) {
            UserDAO userDAO = new UserDAO();
            User user = userDAO.getUserByName(userName);
            if(user == null) {
                currentUser.setName(userName);
                userDAO.updateUser(currentUser);
                User authorizedUser = (User) req.getSession().getAttribute(AUTHORIZED_USER_ATTRIBUTE);
                authorizedUser = userDAO.getUserById(authorizedUser.getId());
                req.getSession().setAttribute(AUTHORIZED_USER_ATTRIBUTE, authorizedUser);
                req.setAttribute(USER_NAME_SUCCESS_ATTRIBUTE, SUCCESS_CHANGES_SAVED);
            } else {
                req.setAttribute(USER_NAME_ERROR_ATTRIBUTE, ERROR_USER_ALREADY_EXIST);
            }
        } else {
            req.setAttribute(USER_NAME_WARNING_ATTRIBUTE, WARNING_NO_CHANGES_DETECTED);
        }
        req.getRequestDispatcher(String.valueOf(req.getSession().getAttribute(CURRENT_PAGE_ATTRIBUTE))).forward(req, resp);
    }

    void editUserEmail(HttpServletRequest req, HttpServletResponse resp) throws ValidationException, SQLException, IOException, ServletException {
        String userEmail = validateUserEmail(req.getParameter(USER_EMAIL_ATTRIBUTE));
        long userId = Long.parseLong(req.getParameter(USER_ID_ATTRIBUTE));
        User currentUser = new UserDAO().getUserById(userId);
        if(!currentUser.getEmail().equals(userEmail)) {
            UserDAO userDAO = new UserDAO();
            User user = userDAO.getUserByEmail(userEmail);
            if(user == null) {
                currentUser.setEmail(userEmail);
                userDAO.updateUser(currentUser);
                User authorizedUser = (User) req.getSession().getAttribute(AUTHORIZED_USER_ATTRIBUTE);
                authorizedUser = userDAO.getUserById(authorizedUser.getId());
                req.getSession().setAttribute(AUTHORIZED_USER_ATTRIBUTE, authorizedUser);
                req.setAttribute(USER_EMAIL_SUCCESS_ATTRIBUTE, SUCCESS_CHANGES_SAVED);
            } else {
                req.setAttribute(USER_EMAIL_ERROR_ATTRIBUTE, ERROR_EMAIL_ALREADY_USED);
            }
        } else {
            req.setAttribute(USER_EMAIL_WARNING_ATTRIBUTE, WARNING_NO_CHANGES_DETECTED);
        }
        req.getRequestDispatcher(String.valueOf(req.getSession().getAttribute(CURRENT_PAGE_ATTRIBUTE))).forward(req, resp);
    }

    void editUserPassword(HttpServletRequest req, HttpServletResponse resp) throws ValidationException, SQLException, IOException, InterruptedException, ServletException {
        String userPassword = validateUserPassword(req.getParameter(USER_PASSWORD_ATTRIBUTE));
        long userId = Long.parseLong(req.getParameter(USER_ID_ATTRIBUTE));
        User currentUser = new UserDAO().getUserById(userId);
        if(!checkPassword(userPassword, currentUser.getPassword())) {
            UserDAO userDAO = new UserDAO();
            currentUser.setPassword(hashPassword(userPassword));
            userDAO.updateUser(currentUser);
            User authorizedUser = (User) req.getSession().getAttribute(AUTHORIZED_USER_ATTRIBUTE);
            authorizedUser = userDAO.getUserById(authorizedUser.getId());
            req.getSession().setAttribute(AUTHORIZED_USER_ATTRIBUTE, authorizedUser);
            req.setAttribute(USER_PASSWORD_SUCCESS_ATTRIBUTE, SUCCESS_CHANGES_SAVED);
        } else {
            req.setAttribute(USER_PASSWORD_WARNING_ATTRIBUTE, WARNING_NO_CHANGES_DETECTED);
        }
        req.getRequestDispatcher(String.valueOf(req.getSession().getAttribute(CURRENT_PAGE_ATTRIBUTE))).forward(req, resp);
    }

    void editUserAvatar(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException, SQLException, ValidationException {
        long userId = Long.parseLong(req.getParameter(USER_ID_ATTRIBUTE));
        User currentUser = new UserDAO().getUserById(userId);
        Part part = req.getPart(USER_AVATAR_ATTRIBUTE);
        validateFile(part);
        String fileName = part.getSubmittedFileName();
        fileName = "UserAvatar" + currentUser.getId() + fileName.substring(fileName.indexOf("."));
        String filePath = req.getServletContext().getInitParameter(UPLOAD_FOLDER_CONTEXT_PARAM);
        File file = new File(filePath, fileName);
        Files.copy(part.getInputStream(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        UserDAO userDAO = new UserDAO();
        currentUser.setAvatarURL(fileName);
        userDAO.updateUser(currentUser);
        User authorizedUser = (User) req.getSession().getAttribute(AUTHORIZED_USER_ATTRIBUTE);
        authorizedUser = userDAO.getUserById(authorizedUser.getId());
        req.getSession().setAttribute(AUTHORIZED_USER_ATTRIBUTE, authorizedUser);
        req.setAttribute(USER_AVATAR_SUCCESS_ATTRIBUTE, SUCCESS_CHANGES_SAVED);
        req.getRequestDispatcher(String.valueOf(req.getSession().getAttribute(CURRENT_PAGE_ATTRIBUTE))).forward(req, resp);
    }

    public void editUser(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        String action = req.getParameter(ACTION);
        if (action.equals(DELETE)) {
            long userId = Long.parseLong(req.getParameter(USER_ID_ATTRIBUTE));
            UserDAO userDAO = new UserDAO();
            userDAO.deleteUserById(userId);
            User authorizedUser = (User) req.getSession().getAttribute(AUTHORIZED_USER_ATTRIBUTE);
            if(authorizedUser.getId() == userId) {
                req.getSession().setAttribute(AUTHORIZED_USER_ATTRIBUTE, null);
            }
            resp.sendRedirect(SHOW_MAIN_URI);
        }
    }
}
