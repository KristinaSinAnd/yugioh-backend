package com.javariga6.yugioh.config;

import com.javariga6.yugioh.filter.JwtAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public JwtAuthFilter jwtAuthFilterBean() throws Exception {
        return new JwtAuthFilter(authenticationManagerBean());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().csrf().disable().cors().and().headers().frameOptions()
                .sameOrigin() // allow frameOptions for H2 console
                .and().exceptionHandling().and().addFilter(jwtAuthFilterBean())

                .authorizeRequests().antMatchers("/h2/**").permitAll() // permit all requests to H2 console, they are secured anyway
                .antMatchers(HttpMethod.POST, "/user/register").permitAll() // allow creation of users
                .antMatchers(HttpMethod.POST, "/user/password/requesttoken").permitAll() // allow creation of users
                .antMatchers(HttpMethod.POST, "/user/password/reset").permitAll() // allow creation of users
                .antMatchers(HttpMethod.POST, "/login").permitAll() // allow login for anonymous user
                .antMatchers(HttpMethod.GET, "/csv/import/**").permitAll() // allow login for anonymous user
                .anyRequest().authenticated(); // deny all other requests by default
    }
}

