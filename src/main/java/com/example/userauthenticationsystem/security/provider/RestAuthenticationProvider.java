package com.example.userauthenticationsystem.security.provider;

import com.example.userauthenticationsystem.security.exception.SecretException;
import com.example.userauthenticationsystem.security.token.RestAuthenticationToken;
import com.example.userauthenticationsystem.security.userdetailsservice.FormUserDetailsService;
import com.example.userauthenticationsystem.security.webauthenticationdetails.FormAuthenticationDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component("restAuthenticationProvider")
@RequiredArgsConstructor
@Slf4j
public class RestAuthenticationProvider implements AuthenticationProvider {

    private final FormUserDetailsService formUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //provider의 역할이 userDetailsService => Authentication(usernamePasswordAuthenticationToken) 객체 들고오기
        String username = authentication.getName();
        String password = (String)authentication.getCredentials();


        UserDetails userDetails = formUserDetailsService.loadUserByUsername(username);
        //비밀번호 확인
        if(!passwordEncoder.matches(password, userDetails.getPassword())){
            throw new BadCredentialsException("Invalid password");
        }

        return new RestAuthenticationToken(userDetails.getAuthorities(), null, userDetails);
        //userDails 대신 userDtails의 accoutdto도 가능하다.
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(RestAuthenticationToken.class);
        //form 인증 방식이기 때문에 usernamePasswordAuthenticationToken이면 진행한다.
    }
}
