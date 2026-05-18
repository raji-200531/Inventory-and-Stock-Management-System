package com.inventory.stockmanagement.controller;

import com.inventory.stockmanagement.service.DashboardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReportController {
    private final DashboardService dashboard;
    public ReportController(DashboardService dashboard) { this.dashboard = dashboard; }
    @GetMapping("/report") public String report(Model model) { model.addAttribute("dashboard", dashboard.getDashboard()); return "report/report"; }
}
