package com.crio.rentRead.configs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.crio.rentRead.repositoryServices.UserRepositoryService;
import com.crio.rentRead.services.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/users/**").permitAll() // Public access
                .requestMatchers(HttpMethod.POST, "/books").hasAuthority("ADMIN") // Allow POST only for ADMIN
                .requestMatchers(HttpMethod.PUT, "/books/{id}").hasAuthority("ADMIN") // Allow PUT only for ADMIN
                .requestMatchers(HttpMethod.DELETE, "/books/{id}").hasAuthority("ADMIN") // Allow DELETE only for ADMIN
                .anyRequest().authenticated() // All other requests need authentication
            )
            .httpBasic(Customizer.withDefaults()); // Enable basic authentication

        logger.info("Security filter chain configured successfully.");
        return httpSecurity.build();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepositoryService userRepositoryService) {
        logger.info("UserDetailsService bean created.");
        return new UserDetailsServiceImpl(userRepositoryService);
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        logger.info("AuthenticationProvider bean created.");
        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        logger.info("PasswordEncoder bean created.");
        return new BCryptPasswordEncoder();
    }

    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults() {
        logger.info("GrantedAuthorityDefaults bean created, removing default 'ROLE_' prefix.");
        return new GrantedAuthorityDefaults(""); // Remove the default "ROLE_" prefix from role name
    }
}
