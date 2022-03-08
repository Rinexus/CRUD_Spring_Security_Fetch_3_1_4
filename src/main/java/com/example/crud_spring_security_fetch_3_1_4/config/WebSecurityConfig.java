package com.example.crud_spring_security_fetch_3_1_4.config;

import com.example.crud_spring_security_fetch_3_1_4.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final SuccessUserHandler successUserHandler;
    private final UserService userService;
    private final PasswordEncoderBean passwordConfig;

    @Autowired
    public WebSecurityConfig(SuccessUserHandler successUserHandler, UserService userService, PasswordEncoderBean passwordConfig) {
        this.successUserHandler = successUserHandler;
        this.userService = userService;
        this.passwordConfig = passwordConfig;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().and()// для авторизации в Postman
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/", "/index").permitAll()
            .antMatchers("/admin/**").hasRole("ADMIN")
            .antMatchers("/user").hasAnyRole("ADMIN", "USER")
            .anyRequest().authenticated()
            .and()
            .formLogin().successHandler(successUserHandler)
            .permitAll()
            .and()
            .logout()
            .permitAll();
    }

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordConfig.passwordEncoder());
    }
}