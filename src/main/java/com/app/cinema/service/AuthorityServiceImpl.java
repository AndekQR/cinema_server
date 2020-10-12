package com.app.cinema.service;

import com.app.cinema.helper.AuthorityType;
import com.app.cinema.model.Authority;
import com.app.cinema.repository.AuthorityRepository;
import com.app.cinema.service.interfaces.AuthorityService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorityServiceImpl implements AuthorityService {

    private final AuthorityRepository authorityRepository;

    public AuthorityServiceImpl(AuthorityRepository authorityRepository) {
        this.authorityRepository=authorityRepository;
    }

    @Override
    public Authority findByType(AuthorityType authorityType) {
        return authorityRepository.findByAuthorityType(authorityType).orElse(null);
    }

    @Override
    public void saveAuthority(Authority authority) {
        if (this.findByType(authority.getAuthorityType()) == null) {
            authorityRepository.save(authority);
        }
    }

    @Override
    public Authority deleteAuthority(Authority authority) {
        authorityRepository.delete(authority);
        return authority;
    }

    @Override
    public List<Authority> createOrGetAuthorities(AuthorityType[] types) {
        return Arrays.stream(types).map(element -> {
            Authority authority = this.findByType(element);
            if (authority == null) {
                authority = new Authority(element);
                authorityRepository.save(authority);
                return authority;
            }
            return authority;
        }).collect(Collectors.toList());
    }
}
