package com.epam.mangalib.service;

import com.epam.mangalib.database.*;
import com.epam.mangalib.entity.*;
import com.epam.mangalib.exception.ValidationException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static com.epam.mangalib.util.MangaLibEntityAttribute.*;
import static com.epam.mangalib.util.MangaLibJSP.MANGA_JSP;

public class ShowMangaService implements Service {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException, InterruptedException, ValidationException {
        saveCurrentPageInSession(req);
        long mangaId = Long.parseLong(req.getParameter(MANGA_ID_ATTRIBUTE));
        Language language = (Language) req.getSession().getAttribute(CURRENT_LANGUAGE_ATTRIBUTE);
        long languageId = language.getId();
        MangaDAO mangaDAO = new MangaDAO();
        Manga manga = mangaDAO.getMangaByIdAndLanguage(mangaId, languageId);
        UserDAO userDAO = new UserDAO();
        List<User> userList = userDAO.getUserListByManga(mangaId);
        manga.setUserList(userList);
        GenreDAO genreDAO = new GenreDAO();
        List<Genre> genreList = genreDAO.getGenreListByManga(mangaId, languageId);
        manga.setGenreList(genreList);
        AuthorDAO authorDAO = new AuthorDAO();
        List<Author> authorList = authorDAO.getAuthorListByManga(mangaId, languageId);
        manga.setAuthorList(authorList);
        ArtistDAO artistDAO = new ArtistDAO();
        List<Artist> artistList = artistDAO.getArtistListByManga(mangaId, languageId);
        manga.setArtistList(artistList);
        ChapterDAO chapterDAO = new ChapterDAO();
        List<Chapter> chapterList = chapterDAO.getChapterListByManga(mangaId, languageId);
        manga.setChapterList(chapterList);
        req.setAttribute(MANGA_ATTRIBUTE, manga);
        List<User> allUserList = userDAO.getUserList();
        for(User user : userList) {
            allUserList.remove(user);
        }
        req.setAttribute(USER_LIST_ATTRIBUTE, allUserList);
        List<Genre> allGenreList = genreDAO.getGenreListByLanguage(languageId);
        for(Genre genre : genreList) {
            allGenreList.remove(genre);
        }
        req.setAttribute(GENRE_LIST_ATTRIBUTE, allGenreList);
        List<Artist> allArtistList = artistDAO.getArtistListByLanguage(languageId);
        for(Artist artist : artistList) {
            allArtistList.remove(artist);
        }
        req.setAttribute(ARTIST_LIST_ATTRIBUTE, allArtistList);
        List<Author> allAuthorList = authorDAO.getAuthorListByLanguage(languageId);
        for(Author author : authorList) {
            allAuthorList.remove(author);
        }
        req.setAttribute(AUTHOR_LIST_ATTRIBUTE, allAuthorList);
        req.getRequestDispatcher(MANGA_JSP).forward(req, resp);
    }
}
