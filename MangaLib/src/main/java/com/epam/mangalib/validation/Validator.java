package com.epam.mangalib.validation;

import com.epam.mangalib.exception.ValidationException;

import javax.servlet.http.Part;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.epam.mangalib.util.MangaLibEntityAttribute.FILE_ATTRIBUTE;

public class Validator {
    public static final String EMPTY_STRING = "";
    public static final String SPACE = " ";
    public static final int NAME_MAX_LENGTH = 100;
    public static final int NAME_MIN_LENGTH = 1;
    public static final int DESCRIPTION_MAX_LENGTH = 1000;
    public static final int DESCRIPTION_MIN_LENGTH = 1;
    public static final int EMAIL_MAX_LENGTH = 100;
    public static final int EMAIL_MIN_LENGTH = 1;
    public static final int PASSWORD_MAX_LENGTH = 100;
    public static final int PASSWORD_MIN_LENGTH = 1;
    public static final String NAME_PATTERN = "^[^<>]{1,100}$";
    public static final String DESCRIPTION_PATTERN = "^[^<>]{1,1000}$";
    public static final String PASSWORD_PATTERN = "^[0-9A-Za-zА-Яа-я-_]{1,100}$";
    public static final String EMAIL_PATTERN = "^([a-z0-9_\\.-]+)@([a-z0-9_\\.-]+)\\.([a-z\\.]{2,6})$";
    public static final int MAX_FILE_SIZE = 10485760;
    public static final String IMAGE_ACCEPT = "image/";

    public static String validateName(String name) throws ValidationException {
        if(name == null || name.trim().equals(EMPTY_STRING) ||
                name.length()>NAME_MAX_LENGTH || name.length()<NAME_MIN_LENGTH ||
                !doMatch(name, NAME_PATTERN)) {
            throw new ValidationException(name);
        }
        return name;
    }

    public static String validateDescription(String description) throws ValidationException {
        if(description == null || description.trim().equals(EMPTY_STRING) ||
                description.length()>DESCRIPTION_MAX_LENGTH || description.length()<DESCRIPTION_MIN_LENGTH ||
                !doMatch(description, DESCRIPTION_PATTERN)) {
            throw new ValidationException(description);
        }
        return description;
    }

    public static String validateUserEmail(String email) throws ValidationException {
        if(email == null || email.trim().equals(EMPTY_STRING) ||
                email.length()>EMAIL_MAX_LENGTH || email.length()<EMAIL_MIN_LENGTH ||
                !doMatch(email, EMAIL_PATTERN)) {
            throw new ValidationException(email);
        }
        return email;
    }

    public static String validateUserPassword(String password) throws ValidationException {
        if(password == null || password.trim().equals(EMPTY_STRING) ||
                password.length()>PASSWORD_MAX_LENGTH || password.length()<PASSWORD_MIN_LENGTH ||
                !doMatch(password, PASSWORD_PATTERN)) {
            throw new ValidationException(password);
        }
        return password;
    }

    public static Part validateFile(Part part) throws ValidationException {
        if(part.getSize() > MAX_FILE_SIZE) {
            throw new ValidationException(FILE_ATTRIBUTE);
        }
        if(!part.getContentType().startsWith(IMAGE_ACCEPT)) {
            throw new ValidationException(FILE_ATTRIBUTE);
        }
        return part;
    }

    private static boolean doMatch(String parameter, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(parameter);
        return matcher.matches();
    }
}
