package com.epam.mangalib.service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static com.epam.mangalib.util.MangaLibConstant.UPLOAD_FOLDER_CONTEXT_PARAM;
import static com.epam.mangalib.util.MangaLibEntityAttribute.FILE_NAME_ATTRIBUTE;

public class LoadFileService implements Service {
    public static final String CONTENT_TYPE_HEADER = "Content-Type";
    public static final String CONTENT_LENGTH_HEADER = "Content-Length";
    public static final String CONTENT_DISPOSITION_HEADER = "Content-Disposition";
    public static final String CONTENT_DISPOSITION_INLINE = "inline; filename=";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fileName = req.getParameter(FILE_NAME_ATTRIBUTE);
        String filePath = req.getServletContext().getInitParameter(UPLOAD_FOLDER_CONTEXT_PARAM);
        File file = new File(filePath, fileName);
        resp.setHeader(CONTENT_TYPE_HEADER, req.getServletContext().getMimeType(fileName));
        resp.setHeader(CONTENT_LENGTH_HEADER, String.valueOf(file.length()));
        resp.setHeader(CONTENT_DISPOSITION_HEADER, CONTENT_DISPOSITION_INLINE + fileName);
        Files.copy(file.toPath(), resp.getOutputStream());
    }
}
