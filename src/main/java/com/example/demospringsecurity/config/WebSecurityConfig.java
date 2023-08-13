package com.example.demospringsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.List;

/**
 * @author thangncph26123
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService authentication() {

        PasswordEncoder encoder = passwordEncoder();

        List<UserDetails> listUserDetails = new ArrayList<>();

        UserDetails userDetails = User.builder().username("thangnc").password(encoder.encode("123456")).build();
        listUserDetails.add(userDetails);

        UserDetails admin = User.builder().username("admin").password(encoder.encode("admin")).roles("ADMIN").build();
        listUserDetails.add(admin);

        return new InMemoryUserDetailsManager(listUserDetails);
    }

    @Bean
    public SecurityFilterChain authorization(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.
                authorizeHttpRequests(req -> req.
                        requestMatchers("/admin/*").hasRole("ADMIN")
                        .anyRequest().permitAll()
                )
                .formLogin(login -> login.
                        loginProcessingUrl("/login"))
        ;

        return httpSecurity.build();
    }
}
