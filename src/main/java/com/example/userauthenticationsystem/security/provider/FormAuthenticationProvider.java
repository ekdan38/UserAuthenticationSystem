package com.example.userauthenticationsystem.security.provider;

import com.example.userauthenticationsystem.security.webauthenticationdetails.FormAuthenticationDetails;
import com.example.userauthenticationsystem.security.userdetailsservice.FormUserDetailsService;
import com.example.userauthenticationsystem.security.exception.SecretException;
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

@Component("authenticationProvider")
@RequiredArgsConstructor
@Slf4j
public class FormAuthenticationProvider implements AuthenticationProvider {

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

        String secretKey = ((FormAuthenticationDetails) authentication.getDetails()).getSecretKey();
        String remoteAddress = ((FormAuthenticationDetails) authentication.getDetails()).getRemoteAddress();
        String sessionId = ((FormAuthenticationDetails) authentication.getDetails()).getSessionId();
        log.info("IP : [ " + remoteAddress + " ]");
        log.info("SESSIONID : [ " + sessionId + " ]");

        if(secretKey == null || !secretKey.equals("secret")){
            throw new SecretException("Invalid secret");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        //userDails 대신 userDtails의 accoutdto도 가능하다.
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
        //form 인증 방식이기 때문에 usernamePasswordAuthenticationToken이면 진행한다.
    }
}
