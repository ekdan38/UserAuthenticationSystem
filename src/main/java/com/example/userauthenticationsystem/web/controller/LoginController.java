package com.example.userauthenticationsystem.web.controller;

import com.example.userauthenticationsystem.security.userdetails.CustomUserDetails;
import com.example.userauthenticationsystem.web.dto.AccountDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "exception", required = false) String errorMessage,
                        Model model){
        model.addAttribute("error", error);
        model.addAttribute("exception", errorMessage);
        return "/login/login";
    }

    @GetMapping("/api/login")
    public String login(){
        return "/rest/login";
    }


    @GetMapping("/signup")
    public String signup(){
        return "/login/signup";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response,
                         @CurrentSecurityContext SecurityContext securityContext){
        Authentication authentication = securityContext.getAuthentication();
        if(authentication != null){
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/login";
    }

    @GetMapping("/denied")
    public String accessDenied(@RequestParam("exception") String exception,
                               @AuthenticationPrincipal CustomUserDetails details,
                               Model model){
//        CustomUserDetails principal = (CustomUserDetails)SecurityContextHolder
//                .getContextHolderStrategy().getContext().getAuthentication().getPrincipal(); //이렇게도 가능.
        model.addAttribute("exception", exception);
        model.addAttribute("username", details.getAccountDto().getUsername());
        return "/login/denied";

    }
}
