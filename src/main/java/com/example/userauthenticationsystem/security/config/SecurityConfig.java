package com.example.userauthenticationsystem.security.config;

import com.example.userauthenticationsystem.security.filters.RestAuthenticationFilter;
import com.example.userauthenticationsystem.security.handler.formloginhandler.FormAccessDeniedHandler;
import com.example.userauthenticationsystem.security.handler.formloginhandler.FormAuthenticationFailHandler;
import com.example.userauthenticationsystem.security.handler.restHandler.RestAuthenticationFailHandler;
import com.example.userauthenticationsystem.security.handler.restHandler.RestAuthenticationSuccessHandler;
import com.example.userauthenticationsystem.security.provider.RestAuthenticationProvider;
import com.example.userauthenticationsystem.security.webauthenticationdetails.FormAuthenticationDetailsSource;
import com.example.userauthenticationsystem.security.handler.formloginhandler.FormAuthenticationSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationProvider authenticationProvider;
    private final RestAuthenticationProvider restAuthenticationProvider;
    private final FormAuthenticationDetailsSource formAuthenticationDetailsSource;
    private final FormAuthenticationSuccessHandler formAuthenticationSuccessHandler;
    private final RestAuthenticationSuccessHandler restAuthenticationSuccessHandler;
    private final RestAuthenticationFailHandler restAuthenticationFailHandler;
    private final FormAuthenticationFailHandler formAuthenticationFailHandler;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**", "/images/**", "/js/**", "/favicon.*", "/*/icon-*").permitAll()
                        .requestMatchers("/","/login*").permitAll()
                        .requestMatchers("/signup").anonymous()
                        .requestMatchers("/logout").hasRole("USER")
                        .requestMatchers("/user").hasRole("USER")
                        .requestMatchers("/manager").hasRole("MANAGER")
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .authenticationDetailsSource(formAuthenticationDetailsSource)
                        .successHandler(formAuthenticationSuccessHandler)
                        .failureHandler(formAuthenticationFailHandler)
                        .permitAll()
                )
                .authenticationProvider(authenticationProvider)
                .exceptionHandling(exception -> exception.accessDeniedHandler(new FormAccessDeniedHandler("/denied")))

        ;
        return http.build();
    }

    @Bean
    @Order(1)
    public SecurityFilterChain restSecurityFilterChain(HttpSecurity http) throws Exception {

        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.authenticationProvider(restAuthenticationProvider);
        AuthenticationManager authenticationManager = builder.build();

        http
                .securityMatcher("/api/login")
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**", "/images/**", "/js/**", "/favicon.*", "/*/icon-*").permitAll()
                        .anyRequest().permitAll()
                )
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(restAuthenticationFilter(authenticationManager), UsernamePasswordAuthenticationFilter.class)
                .authenticationManager(authenticationManager);

        ;
        return http.build();
    }

    private RestAuthenticationFilter restAuthenticationFilter(AuthenticationManager authenticationManager){
        RestAuthenticationFilter restAuthenticationFilter = new RestAuthenticationFilter();
        restAuthenticationFilter.setAuthenticationManager(authenticationManager);
        restAuthenticationFilter.setAuthenticationSuccessHandler(restAuthenticationSuccessHandler);
        restAuthenticationFilter.setAuthenticationFailureHandler(restAuthenticationFailHandler);
        return restAuthenticationFilter;
    }

}
