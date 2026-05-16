package com.inventory.stockmanagement.config;

import com.inventory.stockmanagement.model.product.*;
import com.inventory.stockmanagement.model.supplier.Supplier;
import com.inventory.stockmanagement.model.user.*;
import com.inventory.stockmanagement.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataInitializer implements CommandLineRunner {
    private final CategoryService categories;
    private final SupplierService suppliers;
    private final ProductService products;
    private final UserService users;

    public DataInitializer(CategoryService categories, SupplierService suppliers, ProductService products, UserService users) {
        this.categories = categories;
        this.suppliers = suppliers;
        this.products = products;
        this.users = users;
    }

    @Override
    public void run(String... args) {
        if (categories.findAll().isEmpty()) {
            categories.save(new Category("Electronics", "Electronic devices and accessories"));
            categories.save(new Category("Stationery", "Office and school stationery"));
        }
        if (suppliers.findAll().isEmpty()) {
            suppliers.save(new Supplier("Nimal Perera", "ABC Suppliers", "abc@example.com", "0711234567", "Colombo"));
            suppliers.save(new Supplier("Kamal Silva", "Global Traders", "global@example.com", "0777654321", "Negombo"));
        }
        if (products.findAll().isEmpty()) {
            products.save(new Product("PRD-001", "USB Keyboard", "Standard wired keyboard", new BigDecimal("2500.00"), 15, 5, categories.findAll().get(0).getId(), suppliers.findAll().get(0).getId()));
            products.save(new Product("PRD-002", "Notebook", "A4 ruled notebook", new BigDecimal("350.00"), 4, 5, categories.findAll().get(1).getId(), suppliers.findAll().get(1).getId()));
        }
        if (users.findAll().isEmpty()) {
            users.save(new AdminUser("System Admin", "admin@example.com", "0700000000", "Main Office", "admin", "admin123"));
            users.save(new StaffUser("Stock Staff", "staff@example.com", "0711111111", "Store Room", "staff", "staff123"));
        }
    }
}
