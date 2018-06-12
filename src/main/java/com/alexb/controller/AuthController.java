package com.alexb.controller;

import com.alexb.model.AuthorizedUser;
import com.alexb.model.dto.UserRegistrationDto;
import com.alexb.security.UserContext;
import com.alexb.service.UserRegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserContext userContext;

    private final UserRegistrationService registrationService;

    @GetMapping(value = "/login")
    public String getLoginPage(@AuthenticationPrincipal AuthorizedUser authorizedUser) {
        return isNull(authorizedUser) ? "/login-page" : "redirect:/";
    }

    @GetMapping(value = "/islogged")
    @ResponseBody
    public boolean isUserLogged(@AuthenticationPrincipal AuthorizedUser authorizedUser) {
        return nonNull(authorizedUser);
    }

    @PostMapping(value = "/register")
    @ResponseBody
    public void registerUser(@Valid @RequestBody UserRegistrationDto userRegistrationDto) {
        AuthorizedUser registeredUser = registrationService.registerUser(userRegistrationDto);
        userContext.setCurrentUser(registeredUser);
    }

    @GetMapping(value = "/")
    public String getIndexPage(@AuthenticationPrincipal AuthorizedUser authorizedUser, ModelMap model) {
        model.addAttribute("username", authorizedUser.getUsername());
        model.addAttribute("firstname", authorizedUser.getFirstName());
        model.addAttribute("lastname", authorizedUser.getLastName());
        model.addAttribute("authorities", authorizedUser.getAuthorities());
        return "/index";
    }

    @DeleteMapping(value = "/delete-user")
    @ResponseBody
    public void deleteUserByName(@RequestParam String username) {
        userContext.deleteUser(username);
    }

    @GetMapping(value = "/token")
    @ResponseBody
    public String token() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
