package com.ratatui.notes.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors().disable().csrf().disable()
                .authorizeHttpRequests(requests -> {
                            requests
                                    .requestMatchers(
                                            "/about",
                                            "/contacts",
                                            "/login",
                                            "/img/**",
                                            "/css/**",
                                            "/note/share/**",
                                            "/error/**"
                                    )
                                    .permitAll();
                            requests
                                    .requestMatchers("/").permitAll()
                                    .anyRequest()
                                    .authenticated();
                        }
                )
                .formLogin(AbstractAuthenticationFilterConfigurer::permitAll)
                .formLogin(a -> a.loginPage("/login"))
                .formLogin(a -> a.defaultSuccessUrl("/", true).permitAll())
                .logout(LogoutConfigurer::permitAll);
        return http.build();
    }
}