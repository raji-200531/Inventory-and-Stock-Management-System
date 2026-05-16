package com.inventory.stockmanagement.controller;

import com.inventory.stockmanagement.model.product.Category;
import com.inventory.stockmanagement.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService service;
    public CategoryController(CategoryService service) { this.service = service; }
    @GetMapping public String list(@RequestParam(required=false) String q, Model m) { m.addAttribute("categories", service.search(q)); m.addAttribute("q", q); return "category/category-list"; }
    @GetMapping("/add") public String addForm(Model m) { m.addAttribute("category", new Category()); return "category/category-add"; }
    @PostMapping("/add") public String add(@ModelAttribute Category c, RedirectAttributes r) { service.save(c); r.addFlashAttribute("success","Category saved"); return "redirect:/categories"; }
    @GetMapping("/edit/{id}") public String editForm(@PathVariable Long id, Model m) { m.addAttribute("category", service.findById(id)); return "category/category-edit"; }
    @PostMapping("/edit/{id}") public String edit(@PathVariable Long id, @ModelAttribute Category c, RedirectAttributes r) { c.setId(id); service.save(c); r.addFlashAttribute("success","Category updated"); return "redirect:/categories"; }
    @PostMapping("/delete/{id}") public String delete(@PathVariable Long id, RedirectAttributes r) { service.delete(id); r.addFlashAttribute("success","Category deleted"); return "redirect:/categories"; }
}
