package com.example.userauthenticationsystem.security.config;

import com.example.userauthenticationsystem.security.formloginhandler.FormAccessDeniedHandler;
import com.example.userauthenticationsystem.security.formloginhandler.FormAuthenticationFailHandler;
import com.example.userauthenticationsystem.security.webauthenticationdetails.FormAuthenticationDetailsSource;
import com.example.userauthenticationsystem.security.formloginhandler.FormAuthenticationSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationProvider authenticationProvider;
    private final FormAuthenticationDetailsSource formAuthenticationDetailsSource;
    private final FormAuthenticationSuccessHandler formAuthenticationSuccessHandler;
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
//                .userDetailsService(userDetailsService)//커스컴 했기에 지정해줘야한다.
                .authenticationProvider(authenticationProvider)
                .exceptionHandling(exception -> exception.accessDeniedHandler(new FormAccessDeniedHandler("/denied")))


        ;
        return http.build();
    }

}
