package cs.ubb.hrelperbe.Constants;

import org.springframework.security.core.GrantedAuthority;

public enum UserType implements GrantedAuthority {
    HIRING_MANAGER, RECRUITER, ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
