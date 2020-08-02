package com.javariga6.yugioh.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class SecurityConfiguration {

    @Configuration
    @EnableWebSecurity
    @EnableGlobalMethodSecurity(prePostEnabled = true)
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
                    .antMatchers(HttpMethod.PUT, "/users").permitAll() // allow creation of users
                    .antMatchers(HttpMethod.POST, "/login").permitAll() // allow login for anonymous user
                    .anyRequest().authenticated(); // deny all other requests by default
        }
    }
}
