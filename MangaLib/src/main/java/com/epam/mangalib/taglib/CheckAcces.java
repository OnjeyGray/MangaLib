package com.epam.mangalib.taglib;

import com.epam.mangalib.enumeration.Role;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class CheckAcces extends TagSupport {
    private Role currentRole;
    private String accessRole;
    private long currentId;
    private long accessId;

    public void setCurrentRole(Role currentRole) {
        this.currentRole = currentRole;
    }

    public void setAccessRole(String accessRole) {
        this.accessRole = accessRole;
    }

    public void setCurrentId(long currentId) {
        this.currentId = currentId;
    }

    public void setAccessId(long accessId) {
        this.accessId = accessId;
    }

    @Override
    public int doStartTag() throws JspException {
        if(currentRole == null) {
            currentRole = Role.GUEST;
        }
        Role accessRole = Role.valueOf(this.accessRole);
        if((currentRole.getId() >= accessRole.getId()) || (currentId == accessId && currentId != 0 && accessId != 0)) {
            return EVAL_BODY_INCLUDE;
        }
        return SKIP_BODY;
    }
}
