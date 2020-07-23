package ru.gonch.spring.rest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.gonch.spring.service.UserService;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public String listPage(Model model) {
        return prepareListPage(model);
    }

    private String prepareListPage(Model model) {
        model.addAttribute("users", userService.getAll());
        return "user-list";
    }
}
