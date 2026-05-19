package com.inventory.stockmanagement.controller;

import com.inventory.stockmanagement.model.supplier.Supplier;
import com.inventory.stockmanagement.service.SupplierService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/suppliers")
public class SupplierController {
    private final SupplierService service;
    public SupplierController(SupplierService service) { this.service = service; }
    @GetMapping public String list(@RequestParam(required=false) String q, Model m) { m.addAttribute("suppliers", service.search(q)); m.addAttribute("q", q); return "supplier/supplier-list"; }
    @GetMapping("/add") public String addForm(Model m) { m.addAttribute("supplier", new Supplier()); return "supplier/supplier-add"; }
    @PostMapping("/add") public String add(@ModelAttribute Supplier s, RedirectAttributes r) { service.save(s); r.addFlashAttribute("success","Supplier saved"); return "redirect:/suppliers"; }
    @GetMapping("/edit/{id}") public String editForm(@PathVariable Long id, Model m) { m.addAttribute("supplier", service.findById(id)); return "supplier/supplier-edit"; }
    @PostMapping("/edit/{id}") public String edit(@PathVariable Long id, @ModelAttribute Supplier s, RedirectAttributes r) { s.setId(id); service.save(s); r.addFlashAttribute("success","Supplier updated"); return "redirect:/suppliers"; }
    @PostMapping("/delete/{id}") public String delete(@PathVariable Long id, RedirectAttributes r) { service.delete(id); r.addFlashAttribute("success","Supplier deleted"); return "redirect:/suppliers"; }
}
