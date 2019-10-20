package com.epam.mangalib.enumeration;

public enum Role {
    GUEST(0),
    USER(1),
    ADMIN(2);

    private long id;

    Role(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public static Role getRoleByRoleId(long id) throws IllegalArgumentException {
        for(Role role : values()) {
            if(role.getId() == id) {
                return role;
            }
        }
        throw new IllegalArgumentException();
    }
}
