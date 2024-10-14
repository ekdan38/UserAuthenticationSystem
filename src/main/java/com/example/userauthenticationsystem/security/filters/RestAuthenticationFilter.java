package com.example.userauthenticationsystem.security.filters;

import com.example.userauthenticationsystem.security.token.RestAuthenticationToken;
import com.example.userauthenticationsystem.web.dto.AccountDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * 왜 filter를 재정의하냐? 비동기 통신으로 오는지 확인하기 위해서다.
 * 비동기 통신으로만 login을 처리하겠다는 시나리오.
 */
public class RestAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public RestAuthenticationFilter() {
        super(new AntPathRequestMatcher("/api/login", "POST"));// 어떤 url http method에 대해서 이 필터를 실행할것인가.
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        //비동기 통신인지 확인
        String ajaxHeader = request.getHeader("X-Requested-With");
        if(!HttpMethod.POST.name().equals(request.getMethod()) || !"XMLHttpRequest".equals(ajaxHeader)){
            throw new IllegalArgumentException("RestAuthentication required HttpMethod or AjaxHeader is not Supported");
        }
        AccountDto accountDto = objectMapper.readValue(request.getReader(), AccountDto.class);// HttpServletRequest.getReader()->요청 본문 바디 읽어라.
        if(!StringUtils.hasText(accountDto.getUsername()) || !StringUtils.hasText(accountDto.getPassword())){
            throw new AuthenticationServiceException("Username or Password is not provided");
        }

        //Token 만들어주자. 커스텀해야한다.

        RestAuthenticationToken restAuthenticationToken = new RestAuthenticationToken(accountDto.getPassword(), accountDto.getUsername());
        //AuthenticationFilter는 Authenticaion객체를 return 해줘야한다.
        return getAuthenticationManager().authenticate(restAuthenticationToken);
    }
}
