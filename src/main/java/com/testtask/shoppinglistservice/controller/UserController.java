package com.testtask.shoppinglistservice.controller;

import com.testtask.shoppinglistservice.domain.Role;
import com.testtask.shoppinglistservice.domain.User;
import com.testtask.shoppinglistservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/user")

public class UserController {

    @Autowired
    private UserService userService;

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping
    public String userList(Model model){

        model.addAttribute("users", userService.findAll());
        return "userList";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("{user}")
    public String userEditForm(@PathVariable User user, Model model) {

        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return  "userEdit";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping
    public String userSave(
            @RequestParam String username,
            @RequestParam Map<String, String> form,
            @RequestParam("userId") User user
    ) {

        userService.saveUser(user, username, form);

        return "redirect:/user";
    }

    @GetMapping("profile")
    public String getProfile(
            Model model,
            @AuthenticationPrincipal User user
    ) {
        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());

        return "profile";
    }

    @PostMapping("profile")
    public String updateProfile(
            @AuthenticationPrincipal User user,
            @RequestParam String password,
            @RequestParam String email,
            Model model
    ) {

        userService.updateProfile(user, password, email);

        return "redirect:/user/profile";
    }

}
