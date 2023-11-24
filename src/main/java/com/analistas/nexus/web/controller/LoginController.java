package com.analistas.nexus.web.controller;

// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;

// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {
    
    @GetMapping("/login")
    public String login(){

        return "login";
    }

    // @PostMapping("/logout")
    // public String logout(HttpServletRequest request, HttpServletResponse response) {
    //     org.springframework.security.core.Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    //     if (auth != null){    
    //         new SecurityContextLogoutHandler().logout(request, response, auth);
    //     }
    //     return "redirect:/login?logout";
    // }
}
