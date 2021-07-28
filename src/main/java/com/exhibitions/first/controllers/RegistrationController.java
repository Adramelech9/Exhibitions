package com.exhibitions.first.controllers;

import com.exhibitions.first.models.User;
import com.exhibitions.first.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Map;

@Controller
public class RegistrationController {
    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@RequestParam("password2") String passwordConfirm, @Valid User user, BindingResult bindingResult, Model model) {
        if (user.getPassword() != null && !user.getPassword().equals(passwordConfirm)) {
            model.addAttribute("passwordError", "Пароли не совпадают либо поле пароля пустое");
            return "registration";
        }
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.addAttribute("Errors", errors);
            return "registration";
        }
        if (!userService.addUser(user)) {
            model.addAttribute("usernameError","User exists!" );
            return "registration";
        }

        return "redirect:/login";
    }
}
