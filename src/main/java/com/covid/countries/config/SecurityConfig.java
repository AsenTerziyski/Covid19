package com.covid.countries.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity security) throws Exception {
        security
                .httpBasic()
                .disable();

        // достъп до h2 базата:
        security
//                .authorizeRequests()
//                .antMatchers("/**")
//                .permitAll()
//                .and()
                .authorizeRequests()
                .antMatchers("/console/**")
                .permitAll();
        security
                .csrf()
                .disable();
        security
                .headers()
                .frameOptions()
                .disable();
    }

}
