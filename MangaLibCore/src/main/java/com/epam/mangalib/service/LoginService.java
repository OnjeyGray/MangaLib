package com.epam.mangalib.service;

import com.epam.mangalib.database.UserDAO;
import com.epam.mangalib.entity.User;
import com.epam.mangalib.exception.ValidationException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static com.epam.mangalib.security.Password.checkPassword;
import static com.epam.mangalib.util.MangaLibEntityAttribute.*;
import static com.epam.mangalib.util.MangaLibParameterAttribute.*;
import static com.epam.mangalib.util.MangaLibShowURI.SHOW_MAIN_URI;
import static com.epam.mangalib.validation.Validator.validateName;
import static com.epam.mangalib.validation.Validator.validateUserPassword;

public class LoginService implements Service {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException, InterruptedException, ValidationException {
        String userName = validateName(req.getParameter(USER_NAME_ATTRIBUTE));
        String userPassword = validateUserPassword(req.getParameter(USER_PASSWORD_ATTRIBUTE));
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUserByName(userName);
        if(user != null) {
            if(checkPassword(userPassword, user.getPassword())) {
                req.getSession().setAttribute(AUTHORIZED_USER_ATTRIBUTE, user);
                req.getRequestDispatcher(SHOW_MAIN_URI).forward(req, resp);
            } else {
                req.setAttribute(LOGIN_ERROR_ATTRIBUTE, ERROR_WRONG_PASSOWRD);
                req.getRequestDispatcher((String) req.getSession().getAttribute(CURRENT_PAGE_ATTRIBUTE)).forward(req, resp);
            }
        } else {
            req.setAttribute(LOGIN_ERROR_ATTRIBUTE, ERROR_USER_DOES_NOT_EXIST);
            req.getRequestDispatcher((String) req.getSession().getAttribute(CURRENT_PAGE_ATTRIBUTE)).forward(req, resp);
        }
    }
}
