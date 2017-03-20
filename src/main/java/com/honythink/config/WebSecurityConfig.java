package com.honythink.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.honythink.biz.base.service.impl.CustomUserServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Autowired
    private  CustomUserServiceImpl customUserService;

//    public static void main(String[] args) {
//        System.out.println(new BCryptPasswordEncoder().encode("mapanpan"));
//    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserService).passwordEncoder(new BCryptPasswordEncoder());
    }
    
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//      web
//        .ignoring()
//           .antMatchers("/resources/static/**"); //
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
            .logout()
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
                .and()
            .rememberMe()
                .tokenValiditySeconds(31536000)
                .and()
            .headers()
                // do not use any default headers unless explicitly listed
                .defaultsDisabled()
                .cacheControl()
                .and()
                .and()
            .csrf().disable();
    }

//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("jiangminglei").password("niubi").roles("ADMIN")
//                .and()
//                .withUser("wangbo").password("niubi").roles("ADMIN")
//                .and()
//                .withUser("zhouxing").password("niubi").roles("ADMIN")
//                .and()
//                .withUser("honythink").password("niubi").roles("USER");
//    }

}