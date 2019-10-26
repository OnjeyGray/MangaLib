package com.epam.mangalib.security;

import org.mindrot.jbcrypt.BCrypt;

public final class Password {
    public static final String HASHED_PASSWORD_START = "$2a$";
    private static final int workload = 12;

    private Password() {}

    public static String hashPassword(String password) {
        String salt = BCrypt.gensalt(workload);
        return BCrypt.hashpw(password, salt);
    }

    public static boolean checkPassword(String password, String hashedPassword) throws InterruptedException {
        if(hashedPassword == null || !hashedPassword.startsWith(HASHED_PASSWORD_START)) {
            throw new InterruptedException();
        }
        return BCrypt.checkpw(password, hashedPassword);
    }
}
