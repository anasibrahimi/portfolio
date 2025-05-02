package com.anas.portfolio.controller;

import com.anas.portfolio.service.ContactService;
import com.anas.portfolio.service.ProjectService;
import com.anas.portfolio.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private SkillService skillService;

    @Autowired
    private ContactService contactService;

    @GetMapping
    public String adminDashboard(Model model) {
        model.addAttribute("projectCount", projectService.getAllProjects().size());
        model.addAttribute("skillCount", skillService.getAllSkills().size());
        model.addAttribute("contactCount", contactService.getAllContacts().size());
        model.addAttribute("unreadCount", contactService.getUnreadCount());
        return "admin/dashboard";
    }
}
