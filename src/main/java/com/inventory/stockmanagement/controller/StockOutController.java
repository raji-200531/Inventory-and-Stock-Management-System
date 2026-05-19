package com.inventory.stockmanagement.controller;

import com.inventory.stockmanagement.model.stock.StockOut;
import com.inventory.stockmanagement.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/stock-out")
public class StockOutController {
    private final StockOutService service; private final ProductService products;
    public StockOutController(StockOutService service, ProductService products) { this.service = service; this.products = products; }
    @GetMapping public String list(Model m) { m.addAttribute("stockOuts", service.findAll()); return "stockout/stockout-list"; }
    @GetMapping("/add") public String addForm(Model m) { m.addAttribute("stockOut", new StockOut()); m.addAttribute("products", products.findAll()); return "stockout/stockout-add"; }
    @PostMapping("/add") public String add(@ModelAttribute StockOut s, RedirectAttributes r) { service.create(s); r.addFlashAttribute("success","Stock-out saved and product quantity reduced"); return "redirect:/stock-out"; }
    @GetMapping("/edit/{id}") public String editForm(@PathVariable Long id, Model m) { m.addAttribute("stockOut", service.findById(id)); m.addAttribute("products", products.findAll()); return "stockout/stockout-edit"; }
    @PostMapping("/edit/{id}") public String edit(@PathVariable Long id, @ModelAttribute StockOut s, RedirectAttributes r) { service.update(id, s); r.addFlashAttribute("success","Stock-out updated"); return "redirect:/stock-out"; }
    @PostMapping("/delete/{id}") public String delete(@PathVariable Long id, RedirectAttributes r) { service.delete(id); r.addFlashAttribute("success","Stock-out deleted and product quantity restored"); return "redirect:/stock-out"; }
}
