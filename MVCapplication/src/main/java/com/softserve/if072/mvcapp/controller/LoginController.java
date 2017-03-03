package com.softserve.if072.mvcapp.controller;

import com.softserve.if072.mvcapp.dto.UserLoginForm;
import com.softserve.if072.mvcapp.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;


/**
 * The controller contains methods that handle user login process
 *
 * @author Igor Parada
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    @Value("login.invalidCredentials")
    private String invalidCredentialsMessage;

    private LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    /**
     * Fills in the login page model with empty UserLoginForm instance.
     */
    @GetMapping
    public String getLoginPage(Model model) {
        model.addAttribute("loginForm", new UserLoginForm());
        return "login";
    }

    /**
     * Handles login process
     */
    @PostMapping
    public String postLoginPage(@Validated @ModelAttribute("loginForm") UserLoginForm loginForm, BindingResult result,
                                Model model, HttpServletResponse httpServletResponse) {
        if (result.hasErrors()) {
            model.addAttribute("errorMessages", result.getFieldErrors());
            return "login";
        }

        Cookie cookie = loginService.performLogin(loginForm);
        if(cookie != null) {
            httpServletResponse.addCookie(cookie);
            return "redirect:/home";
        } else {
            model.addAttribute("loginError", invalidCredentialsMessage);
            return "login";
        }
    }
}
