package com.scm.scm20.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

// import com.scm.services.impl.SecurityCustomUserDetailService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.scm.scm20.services.impl.SecurityCustomerUserDetailsService;

@Configuration
public class SecurityConfig {

    // user create and login using java code with in memory service
    // @Bean
    // public UserDetailsService userDetailsService() {
    // // create user
    // UserDetails user1 =
    // User.withDefaultPasswordEncoder().username("admin123").password("admin123").roles("ADMIN","USER").build();
    // UserDetails user2 =
    // User.withDefaultPasswordEncoder().username("user123").password("user123").roles("ADMIN","USER").build();
    // var inMemoryUserDetailsManager = new InMemoryUserDetailsManager(user1,user2);
    // return inMemoryUserDetailsManager;
    // }

    @Autowired
    private SecurityCustomerUserDetailsService userDetailsService;

    // configuration of authentication provider for spring security
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        // user details service ka object
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        // password encoder ka object
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        // configuration

        // url's configure done ki kon se public rahenge aur konse private rahenge.
        httpSecurity.authorizeHttpRequests(authorize -> {
            // authorize.requestMatchers("/home", "/signup", "/services").permitAll();
            authorize.requestMatchers("/user/**").authenticated();
            authorize.anyRequest().permitAll();
        });

        // form default login
        // agar hame kuch bhi change karna hua to hame yaha aayenge
        httpSecurity.formLogin(formLogin -> {
            formLogin.loginPage("/login");
            formLogin.loginProcessingUrl("/authenticate");
            formLogin.successForwardUrl("/user/dashboard");
            formLogin.failureForwardUrl("/login?error=true");
            formLogin.usernameParameter("email");
            formLogin.passwordParameter("password");
            formLogin.failureHandler(new AuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {

            throw new UnsupportedOperationException("Unimplemented method
            'onAuthenticationFailure'");
            }
            });
            formLogin.successHandler(new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
            throw new UnsupportedOperationException("Unimplemented method
            'onAuthenticationSuccess'");
            }
            });

        }
        );
        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
