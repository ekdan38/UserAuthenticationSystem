package com.example.userauthenticationsystem.security.webauthenticationdetails;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

@Getter
public class FormAuthenticationDetails extends WebAuthenticationDetails {

    private String secretKey;

    public FormAuthenticationDetails(HttpServletRequest request) {
        super(request);
        secretKey = request.getParameter("secret_key");
    }
}
