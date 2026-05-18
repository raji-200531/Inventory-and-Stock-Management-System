package com.inventory.stockmanagement.controller;

import com.inventory.stockmanagement.model.user.*;
import com.inventory.stockmanagement.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public String list(@RequestParam(required = false) String q, Model m) {
        m.addAttribute("users", service.search(q));
        m.addAttribute("q", q);
        return "user/user-list";
    }

    @GetMapping("/add")
    public String addForm(Model m) {
        m.addAttribute("userAccount", new UserAccount());
        m.addAttribute("roles", UserRole.values());
        return "user/user-add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute UserAccount u, RedirectAttributes r) {
        service.save(u);
        r.addFlashAttribute("success", "User saved");
        return "redirect:/users";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model m) {
        m.addAttribute("userAccount", service.findById(id));
        m.addAttribute("roles", UserRole.values());
        return "user/user-edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Long id, @ModelAttribute UserAccount u, RedirectAttributes r) {
        u.setId(id);
        service.save(u);
        r.addFlashAttribute("success", "User updated");
        return "redirect:/users";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes r) {
        service.delete(id);
        r.addFlashAttribute("success", "User deleted");
        return "redirect:/users";
    }
}
