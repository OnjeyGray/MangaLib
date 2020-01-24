package com.epam.mangalib.service;

import com.epam.mangalib.database.UserDAO;
import com.epam.mangalib.entity.User;
import com.epam.mangalib.exception.ValidationException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static com.epam.mangalib.util.MangaLibEntityAttribute.USER_LIST_ATTRIBUTE;
import static com.epam.mangalib.util.MangaLibJSP.USER_LIST_JSP;

public class ShowUserListService implements Service {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException, InterruptedException, ValidationException {
        saveCurrentPageInSession(req);
        UserDAO userDAO = new UserDAO();
        List<User> userList = userDAO.getUserList();
        req.setAttribute(USER_LIST_ATTRIBUTE, userList);
        req.getRequestDispatcher(USER_LIST_JSP).forward(req, resp);
    }
}
