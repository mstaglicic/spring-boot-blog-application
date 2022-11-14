package com.brightstraining.springbootblogapplication.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true) //enables Spring Security pre/post annotations and determines if the @Secured annotation should be enabled
public class WebSecurityConfig {

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //every url in the array is available
    private static final String[] WHITELIST = {
            "/posts",
            "/posts/register",
            "/posts/login",
            "/css/*",
            "/posts/*"

    };

    @Bean   //method-level annotation
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(WHITELIST).permitAll()
                .antMatchers(HttpMethod.GET, "/posts/*").permitAll()   //anyone should have access to read
                .anyRequest().authenticated();

        http
                .formLogin()
                .loginPage("/posts/login")                                  //url for login
                .loginProcessingUrl("/posts/login")
                .usernameParameter("email")                                 //what is used for login
                .passwordParameter("password")
                .defaultSuccessUrl("/posts/", true)   //redirect to if the login is ok
                .failureForwardUrl("/posts/login?error")                    //redirect to if the login is not ok
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/posts/logout")                                 //url for logout
                .logoutSuccessUrl("/posts/")                    //redirect after logout
                .and()
                .httpBasic();

        return http.build();

    }

}
