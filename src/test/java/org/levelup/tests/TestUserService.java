package org.levelup.tests;

import org.levelup.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;

/**
 * Чтобы в тестах не лезть в ДБ, можно создать сервис-заглушку, который будет возвращасть готового пользователя.
 * Затем его можно через @Bean прописать в TestConfiguration (стр. 24), или пометить как компонент, заэксклудить UserRoleService через
 * фильты в тестовой конфигурации и подключить этот сервис.
 * (в данном проекте не используется)
 */
public class TestUserService implements UserDetailsService {
    @Autowired
    private PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username.equals("test-user")) {
            return new User(username, encoder.encode("123"),
                    Arrays.asList(new SimpleGrantedAuthority("ROLE_" + UserRole.USER))
            );
        }

        throw new UsernameNotFoundException("User " + username + " not found.");
    }
}
