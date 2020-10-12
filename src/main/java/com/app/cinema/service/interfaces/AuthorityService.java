package com.app.cinema.service.interfaces;

import com.app.cinema.helper.AuthorityType;
import com.app.cinema.model.Authority;

import java.util.List;

public interface AuthorityService {
    Authority findByType(AuthorityType authorityType);
    void saveAuthority(Authority authority);
    Authority deleteAuthority(Authority authority);
    List<Authority> createOrGetAuthorities(AuthorityType[] types);
}
