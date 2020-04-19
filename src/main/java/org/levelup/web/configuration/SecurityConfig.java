package org.levelup.web.configuration;

import org.levelup.model.UserRole;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.levelup.web.AppConstants.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/pages/**", "**/*.jsp").denyAll() // не забыть про css, в т.ч. в WebConfiguration
                .antMatchers("/", "/scripts/**", LOGIN_PAGE, LOGIN_ACTION, REGISTRATION_PAGE).permitAll()
                .antMatchers("/" + ADMIN + "/**").hasRole(String.valueOf(UserRole.ADMIN))
                .antMatchers("/api/**").authenticated()
                .anyRequest().denyAll();

        http.formLogin()
                .usernameParameter(USER_NAME_PARAMETER)
                .passwordParameter(PASSWORD_PARAMETER)
                .loginProcessingUrl(LOGIN_ACTION) // -> POST
                .loginPage(LOGIN_PAGE);         // redirect to if not authenticated / not logged in

        http.csrf();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(
            PasswordEncoder encoder,
            UserDetailsService service) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(encoder);
        provider.setUserDetailsService(service);
        return provider;
    }
}
