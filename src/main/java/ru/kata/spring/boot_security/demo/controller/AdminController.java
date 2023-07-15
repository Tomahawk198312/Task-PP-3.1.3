package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;


@Controller
@RequestMapping
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/admin")
    public String showAllUser(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin";
    }

    @RequestMapping("/add")
    public String add(Model model) {
        User user = new User();
        model.addAttribute("newUser", user);
        model.addAttribute("allRoles", userService.getAllRoles());
        return "edit";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String create(@ModelAttribute User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @RequestMapping("/edit/{id}")
    public String editUser(@PathVariable(value = "id") Integer id, Model model) {
        model.addAttribute("newUser", userService.getUserById(id));
        model.addAttribute("allRoles", userService.getAllRoles());
        return "edit";
    }

    @RequestMapping(value = "/delete/{id}")
    public String deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

}