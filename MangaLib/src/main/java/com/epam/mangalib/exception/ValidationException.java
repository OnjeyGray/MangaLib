package com.epam.mangalib.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.mangalib.util.MangaLibEntityAttribute.CURRENT_PAGE_ATTRIBUTE;
import static com.epam.mangalib.util.MangaLibParameterAttribute.ERROR_INVALID_VALUE;

public class ValidationException extends Exception {
    public ValidationException(String message) {
        super(message);
    }

    public void sendError(HttpServletRequest req, HttpServletResponse resp, String errorAttribute) throws IOException {
        resp.sendRedirect(req.getSession().getAttribute(CURRENT_PAGE_ATTRIBUTE)
                + "&" + errorAttribute  + "=" + ERROR_INVALID_VALUE);
    }
}
