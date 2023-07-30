package com.ratatui.notes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.
                authorizeHttpRequests(requests -> {
                    requests.requestMatchers(new AntPathRequestMatcher("/"),
                            new AntPathRequestMatcher("/about/**"),
                            new AntPathRequestMatcher("/contacts/**"),
                            new AntPathRequestMatcher("/error/**")).permitAll();
                    requests.requestMatchers(new AntPathRequestMatcher("/account/**"),
                            new AntPathRequestMatcher("/note/**")).authenticated().anyRequest();

                }).formLogin((form) -> form
                        .defaultSuccessUrl("/", true)
                        .loginPage("/login")
                        .permitAll()
                )
                .logout((logout) -> logout.permitAll());

        return http.build();

    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user =
                User.withDefaultPasswordEncoder()
                        .username("user")
                        .password("12345")
                        .roles("USER")
                        .build();
        return new InMemoryUserDetailsManager(user);
    }
}