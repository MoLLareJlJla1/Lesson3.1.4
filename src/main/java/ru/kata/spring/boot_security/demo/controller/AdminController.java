package ru.kata.spring.boot_security.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;


@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class    AdminController {
    private final UserService userService;
    private final RoleService roleService;

    @GetMapping("")
    public String showAllUser(Model model, Principal principal) {
        model.addAttribute("showAllAttributes", userService.showAll());
        model.addAttribute("princ",userService.loadUserByUsername(principal.getName()));
        return "showAllUser";
    }
    @GetMapping("/{id}")
    public String showUserInfo(Principal principal,Model model){
        model.addAttribute("princ",userService.loadUserByUsername(principal.getName()));
        return "userInAdmin";
    }

    @GetMapping("/add")
    public String addUser(Model model,Principal principal) {
        model.addAttribute("princ",userService.loadUserByUsername(principal.getName()));
        model.addAttribute("newUser", new User());
        return "add";
    }

    @PostMapping("/save")
    public String saveUser(@ModelAttribute("newUser") User user) {

        userService.saveUser(user);
        return "redirect:/admin/";
    }

    @PutMapping("/update")
    public String save(@ModelAttribute("newUser") User user,
                       @RequestParam(required = false) String adminRoles, @RequestParam(required = false) String userRoles) {
        if (userRoles != null && userRoles.equals("USER")) {
            user.addRole(roleService.findById(2L));
        }
        if (adminRoles != null && adminRoles.equals("ADMIN")) {
            user.addRole(roleService.findById(1L));
        }

        userService.saveUser(user);
        return "redirect:/admin/";
    }


    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        userService.deleteUser(id);
        return "redirect:/admin/";
    }

}
