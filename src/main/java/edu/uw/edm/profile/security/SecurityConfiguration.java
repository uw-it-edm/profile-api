package edu.uw.edm.profile.security;

import org.springframework.boot.actuate.autoconfigure.security.EndpointRequest;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * @author Maxime Deravet Date: 10/13/17
 */
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


    // ... other stuff for application security

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
                .requestMatchers(EndpointRequest.toAnyEndpoint())
                .permitAll()
                .antMatchers("/**")
                .permitAll();

        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS);

    }


}
