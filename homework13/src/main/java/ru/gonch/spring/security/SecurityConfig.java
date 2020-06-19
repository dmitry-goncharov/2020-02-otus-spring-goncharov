package ru.gonch.spring.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.gonch.spring.service.UserServiceImpl;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserServiceImpl userService;

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/static/**");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests().antMatchers("/genre/add", "/genre/edit").hasAnyRole("EDITOR", "ADMIN")
                .and()
                .authorizeRequests().antMatchers("/author/add", "/author/edit").hasAnyRole("EDITOR", "ADMIN")
                .and()
                .authorizeRequests().antMatchers("/book/add", "/book/edit").hasAnyRole("EDITOR", "ADMIN")
                .and()
                .authorizeRequests().antMatchers("/comment/add", "/comment/edit").hasAnyRole("USER", "EDITOR", "ADMIN")
                .and()
                .authorizeRequests().antMatchers("/user/**").hasRole("ADMIN")
                .and()
                .formLogin()
                .and()
                .logout()
                .and()
                .exceptionHandling().accessDeniedPage("/403");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }
}
