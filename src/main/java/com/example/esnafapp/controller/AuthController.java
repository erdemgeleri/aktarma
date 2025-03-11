package com.example.esnafapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.esnafapp.Model.User;
import com.example.esnafapp.Service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        Model model,
                        HttpSession session) {

        User user = userService.findByUsername(username);
        
        if (user != null && user.getPassword().equals(password)) {
            session.setAttribute("loggedInUser", user.getUsername());
            session.setAttribute("role", user.getRole());
            if(user.getRole().equals("ADMIN")) {
            	return "redirect:/";
            }else {
            	return "redirect:/";
            }
        } else {
            model.addAttribute("error", "Geçersiz kullanıcı adı veya şifre.");
            return "login";  
        }
    }

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String email,
                           @RequestParam String password,
                           Model model) {

        if (userService.findByUsername(username) != null) {
            model.addAttribute("error", "Bu kullanıcı adı zaten alınmış.");
            return "register";
        }

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setEmail(email); 
        newUser.setRole("USER");

        userService.saveUser(newUser);

        return "redirect:/auth/login"; 
    }
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); 
        return "redirect:/";  
    }
}
