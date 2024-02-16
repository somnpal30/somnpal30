package com.sample.springwebsecurity.security;

import com.sample.springwebsecurity.filter.CustomFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Slf4j
@Configuration
/*@EnableWebSecurity(debug = true)*/
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.addFilterBefore(new CustomFilter(), BasicAuthenticationFilter.class);
        httpSecurity.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/actuator/**").permitAll()
                .anyRequest().authenticated()
        ).csrf(csrfConfigurer -> csrfConfigurer.disable());
        //httpSecurity.addFilterAfter(new CustomFilter(), BasicAuthenticationFilter.class);
        httpSecurity.httpBasic(Customizer.withDefaults());
        return httpSecurity.build();
    }
}
