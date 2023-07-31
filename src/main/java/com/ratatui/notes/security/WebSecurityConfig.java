package com.ratatui.notes.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(requests -> {
                    requests
                            .requestMatchers("/about", "/contacts", "/login")
                            .permitAll();
                    requests
                                    .requestMatchers("/").permitAll()
                                    .anyRequest()
                                    .authenticated();
                        }
                )
                .formLogin(AbstractAuthenticationFilterConfigurer::permitAll)
                .formLogin(a->a.loginPage("/login"))
                .formLogin(a -> a.defaultSuccessUrl("/",  true).permitAll())
                .logout(LogoutConfigurer::permitAll);
        return http.build();
    }
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user =
                User.withDefaultPasswordEncoder()
                        .username("cat@doc.com")
                        .password("12345")
                        .roles("USER")
                        .build();
        return new InMemoryUserDetailsManager(user);
    }
}