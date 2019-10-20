package com.epam.mangalib.service;

import java.util.HashMap;
import java.util.Map;

import static com.epam.mangalib.util.MangaLibShowURI.*;
import static com.epam.mangalib.util.MangaLibURI.*;

public class ServiceFactory {
    private static final Map<String, Service> SERVICE_MAP = new HashMap<>();
    private static final ServiceFactory SERVICE_FACTORY = new ServiceFactory();

    private ServiceFactory() {
        init();
    }

    private void init() {
        SERVICE_MAP.put(SHOW_MAIN_URI, new ShowMainService());
        SERVICE_MAP.put(SHOW_CHAPTER_URI, new ShowChapterService());
        SERVICE_MAP.put(SHOW_SEARCH_URI, new ShowSearchService());
        SERVICE_MAP.put(SHOW_USER_URI, new ShowUserService());
        SERVICE_MAP.put(SHOW_LOGIN_URI, new ShowLoginService());
        SERVICE_MAP.put(SHOW_REGISTER_URI, new ShowRegisterService());
        SERVICE_MAP.put(SHOW_MANGA_URI, new ShowMangaService());
        SERVICE_MAP.put(SHOW_ARTIST_URI, new ShowArtistService());
        SERVICE_MAP.put(SHOW_AUTHOR_URI, new ShowAuthorService());
        SERVICE_MAP.put(SHOW_GENRE_URI, new ShowGenreService());
        SERVICE_MAP.put(SHOW_USER_LIST_URI, new ShowUserListService());
        SERVICE_MAP.put(SHOW_MANGA_LIST_URI, new ShowMangaListService());
        SERVICE_MAP.put(SHOW_ARTIST_LIST_URI, new ShowArtistListService());
        SERVICE_MAP.put(SHOW_AUTHOR_LIST_URI, new ShowAuthorListService());
        SERVICE_MAP.put(SHOW_GENRE_LIST_URI, new ShowGenreListService());
        SERVICE_MAP.put(LOAD_FILE_URI, new LoadFileService());
        SERVICE_MAP.put(REGISTER_URI, new RegisterService());
        SERVICE_MAP.put(LOGIN_URI, new LoginService());
        SERVICE_MAP.put(LOGOUT_URI, new LogoutService());
        SERVICE_MAP.put(EDIT_USER_URI, new EditUserService());
        SERVICE_MAP.put(EDIT_MANGA_URI, new EditMangaService());
        SERVICE_MAP.put(EDIT_AUTHOR_URI, new EditAuthorService());
        SERVICE_MAP.put(EDIT_ARTIST_URI, new EditArtistService());
        SERVICE_MAP.put(EDIT_GENRE_URI, new EditGenreService());
        SERVICE_MAP.put(EDIT_FAVORITE_URI, new EditFavoriteService());
        SERVICE_MAP.put(EDIT_CHAPTER_URI, new EditChapterService());
        SERVICE_MAP.put(EDIT_COMMENT_URI, new EditCommentService());
        SERVICE_MAP.put(CHANGE_LANGUAGE_URI, new ChangeLanguageService());
    }

    public static ServiceFactory getInstance() {
        return SERVICE_FACTORY;
    }

    public Service getService(String requestService) {
        return SERVICE_MAP.get(requestService);
    }
}
