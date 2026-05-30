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

import java.util.ArrayList;
import java.util.Arrays;

/*
   SecurityFilterChain: flow
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

        // user1234 : fhioahgiohafhigh29492489294
        System.out.println("password encode "+passwordEncoder.encode("user1234"));
        // user 1
        UserDetails user =
                User.builder()
                        .username("ashish")
                        .password(
                                passwordEncoder.encode("user1234"))
                        .roles("USER","MANAGER")
                        .build();

        // user 2
        UserDetails admin =
                User.builder()
                        .username("ashwin")
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

                // //user   //product
                //  /public : all url / api allow ex: /public/abc, /public/products
                //  /admin   : /admin/user   /admin/data /admin/product
                //  /user       : /user/data /user/profile
                //  /product  ;   /product/info  /product/quantity

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
