package com.inventory.stockmanagement.controller;

import com.inventory.stockmanagement.service.DashboardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {
    private final DashboardService dashboard;
    public DashboardController(DashboardService dashboard) { this.dashboard = dashboard; }
    @GetMapping("/dashboard") public String dashboard(Model model) { model.addAttribute("dashboard", dashboard.getDashboard()); return "dashboard/dashboard"; }
}
