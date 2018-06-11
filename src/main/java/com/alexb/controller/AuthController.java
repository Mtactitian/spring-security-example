package com.alexb.controller;

import com.alexb.model.AuthorizedUser;
import com.alexb.model.dto.UserRegistrationDto;
import com.alexb.security.UserContext;
import com.alexb.service.UserRegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserContext userContext;

    private final UserRegistrationService registrationService;

    @GetMapping(value = "/login")
    public String getLoginPage(@AuthenticationPrincipal Authentication authentication) {
        return authentication != null ? "redirect:/" : "/login-page";
    }

    @GetMapping(value = "/islogged")
    @ResponseBody
    public boolean testLoggedIn() {
        return Objects.nonNull(userContext.getCurrentUser());
    }

    @PostMapping(value = "/register")
    @ResponseBody
    public void registerUser(@Valid @RequestBody UserRegistrationDto userRegistrationDto) {
        final AuthorizedUser registeredUser = registrationService.registerUser(userRegistrationDto);
        userContext.setCurrentUser(registeredUser);
    }

    @GetMapping(value = "/")
    public String getIndexPage(ModelMap model) {
        final AuthorizedUser currentUser = userContext.getCurrentUser();

        model.addAttribute("username", currentUser.getUsername());
        model.addAttribute("firstname", currentUser.getFirstName());
        model.addAttribute("lastname", currentUser.getLastName());
        model.addAttribute("authorities", currentUser.getAuthorities());
        return "/index";
    }

    @DeleteMapping(value = "/delete-user")
    @ResponseBody
    public void deleteUserByName(@RequestParam String username) {
        userContext.deleteUser(username);
    }
}
