package com.inventory.stockmanagement.controller;

import com.inventory.stockmanagement.model.user.StaffUser;
import com.inventory.stockmanagement.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {
    private final UserService users;
    public AuthController(UserService users) { this.users = users; }

    @GetMapping({"/", "/login"})
    public String loginPage(HttpSession session) { return session.getAttribute("loggedUser") == null ? "auth/login" : "redirect:/dashboard"; }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session, Model model) {
        return users.authenticate(username, password).map(u -> { session.setAttribute("loggedUser", u); return "redirect:/dashboard"; }).orElseGet(() -> { model.addAttribute("error", "Invalid username or password"); return "auth/login"; });
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) { session.invalidate(); return "redirect:/login?logout=true"; }

    @GetMapping("/register")
    public String registerPage(HttpSession session) {
        return session.getAttribute("loggedUser") == null ? "auth/register" : "redirect:/dashboard";
    }

    @PostMapping("/register")
    public String register(
            @RequestParam String name,
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String confirmPassword,
            @RequestParam(required = false, defaultValue = "") String email,
            @RequestParam(required = false, defaultValue = "") String phone,
            HttpSession session, Model model) {

        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match");
            model.addAttribute("name", name);
            model.addAttribute("username", username);
            model.addAttribute("email", email);
            model.addAttribute("phone", phone);
            return "auth/register";
        }
        try {
            StaffUser newUser = new StaffUser(name, email, phone, "", username, password);
            var saved = users.save(newUser);
            session.setAttribute("loggedUser", saved);
            return "redirect:/dashboard";
        } catch (Exception ex) {
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("name", name);
            model.addAttribute("username", username);
            model.addAttribute("email", email);
            model.addAttribute("phone", phone);
            return "auth/register";
        }
    }
}
