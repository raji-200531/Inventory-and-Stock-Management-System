package com.inventory.stockmanagement.controller;

import com.inventory.stockmanagement.model.product.Product;
import com.inventory.stockmanagement.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/products")
public class ProductController {
    private final ProductService products; private final CategoryService categories; private final SupplierService suppliers;
    public ProductController(ProductService products, CategoryService categories, SupplierService suppliers) { this.products = products; this.categories = categories; this.suppliers = suppliers; }
    @GetMapping public String list(@RequestParam(required=false) String q, Model m) { m.addAttribute("products", products.search(q)); m.addAttribute("q", q); m.addAttribute("lowStockProducts", products.findLowStockProducts()); return "product/product-list"; }
    @GetMapping("/add") public String addForm(Model m) { m.addAttribute("product", new Product()); lookups(m); return "product/product-add"; }
    @PostMapping("/add") public String add(@ModelAttribute Product p, RedirectAttributes r) { products.save(p); r.addFlashAttribute("success","Product saved"); return "redirect:/products"; }
    @GetMapping("/edit/{id}") public String editForm(@PathVariable Long id, Model m) { m.addAttribute("product", products.findById(id)); lookups(m); return "product/product-edit"; }
    @PostMapping("/edit/{id}") public String edit(@PathVariable Long id, @ModelAttribute Product p, RedirectAttributes r) { p.setId(id); products.save(p); r.addFlashAttribute("success","Product updated"); return "redirect:/products"; }
    @PostMapping("/delete/{id}") public String delete(@PathVariable Long id, RedirectAttributes r) { products.delete(id); r.addFlashAttribute("success","Product deleted"); return "redirect:/products"; }
    private void lookups(Model m) { m.addAttribute("categories", categories.findAll()); m.addAttribute("suppliers", suppliers.findAll()); }
}
