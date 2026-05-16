package com.inventory.stockmanagement.controller;

import com.inventory.stockmanagement.model.stock.StockIn;
import com.inventory.stockmanagement.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/stock-in")
public class StockInController {
    private final StockInService service; private final ProductService products; private final SupplierService suppliers;
    public StockInController(StockInService service, ProductService products, SupplierService suppliers) { this.service = service; this.products = products; this.suppliers = suppliers; }
    @GetMapping public String list(Model m) { m.addAttribute("stockIns", service.findAll()); return "stockin/stockin-list"; }
    @GetMapping("/add") public String addForm(Model m) { m.addAttribute("stockIn", new StockIn()); lookups(m); return "stockin/stockin-add"; }
    @PostMapping("/add") public String add(@ModelAttribute StockIn s, RedirectAttributes r) { service.create(s); r.addFlashAttribute("success","Stock-in saved and product quantity increased"); return "redirect:/stock-in"; }
    @GetMapping("/edit/{id}") public String editForm(@PathVariable Long id, Model m) { m.addAttribute("stockIn", service.findById(id)); lookups(m); return "stockin/stockin-edit"; }
    @PostMapping("/edit/{id}") public String edit(@PathVariable Long id, @ModelAttribute StockIn s, RedirectAttributes r) { service.update(id, s); r.addFlashAttribute("success","Stock-in updated"); return "redirect:/stock-in"; }
    @PostMapping("/delete/{id}") public String delete(@PathVariable Long id, RedirectAttributes r) { service.delete(id); r.addFlashAttribute("success","Stock-in deleted and product quantity corrected"); return "redirect:/stock-in"; }
    private void lookups(Model m) { m.addAttribute("products", products.findAll()); m.addAttribute("suppliers", suppliers.findAll()); }
}
