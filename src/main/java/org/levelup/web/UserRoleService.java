package org.levelup.web;

import org.levelup.model.User;
import org.levelup.model.UserRole;
import org.levelup.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserRoleService implements UserDetailsService {
    @Autowired
    UsersRepository users;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = users.findByLogin(login);
        if (user == null) {
            throw new UsernameNotFoundException("User " + login + "is not found");
        }
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority("ROLE_" + UserRole.USER));
        if (user.getRole().getName() == UserRole.ADMIN) {
            roles.add(new SimpleGrantedAuthority("ROLE_" + UserRole.ADMIN));
        }
        return new org.springframework.security.core.userdetails.User(login, user.getPassword(), roles);
    }
}
