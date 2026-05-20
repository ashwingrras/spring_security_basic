package org.example.spring_security_basic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import org.springframework.security.web.SecurityFilterChain;

/*
   SecurityFilterChain:
    1. Client Request

    2. Tomcat / Embedded Server

    3. DelegatingFilterProxy

    4. FilterChainProxy

    5. SecurityFilterChain

    6. [Security Filters]

    7. Controller

    8. Response

    Spring Built in filters:
    1.  DisableEncodeUrlFilter
    2.  WebAsyncManagerIntegrationFilter
    3.  SecurityContextHolderFilter
    4.  HeaderWriterFilter
    5.  CsrfFilter
    6.  LogoutFilter
    7.  UsernamePasswordAuthenticationFilter
    8.  BasicAuthenticationFilter
    9.  RequestCacheAwareFilter
    10. SecurityContextHolderAwareRequestFilter
    11. AnonymousAuthenticationFilter
    12. ExceptionTranslationFilter
    13. AuthorizationFilter



 */

@Configuration
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService(
            PasswordEncoder passwordEncoder) {

        UserDetails user =
                User.builder()
                        .username("user")
                        .password(
                                passwordEncoder.encode("user1234"))
                        .roles("USER")
                        .build();

        UserDetails admin =
                User.builder()
                        .username("admin")
                        .password(
                                passwordEncoder.encode("admin1234"))
                        .roles("ADMIN")
                        .build();

        return new InMemoryUserDetailsManager(
                user,
                admin
        );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        return http
                .csrf(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/public/**")
                        .permitAll()
                        .requestMatchers("/admin/**")
                        .hasRole("ADMIN")
                        .anyRequest()
                        .authenticated())

                .formLogin(Customizer.withDefaults())

                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/public/logout-success")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )

                .build();
    }

}
