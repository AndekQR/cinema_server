package com.app.cinema.service.interfaces;

import com.app.cinema.helper.AuthorityType;
import com.app.cinema.Entity.Authority;

import java.util.Set;

public interface AuthorityService {
    Authority findByType(AuthorityType authorityType);
    void saveAuthority(Authority authority);
    Authority deleteAuthority(Authority authority);
    Set<Authority> createOrGetAuthorities(AuthorityType[] types);
}
