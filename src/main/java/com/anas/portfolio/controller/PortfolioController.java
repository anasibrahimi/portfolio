package com.anas.portfolio.controller;

import com.anas.portfolio.model.Contact;
import com.anas.portfolio.model.Project;
import com.anas.portfolio.model.Skill;
import com.anas.portfolio.service.ContactService;
import com.anas.portfolio.service.ProjectService;
import com.anas.portfolio.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class PortfolioController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private SkillService skillService;

    @Autowired
    private ContactService contactService;

    @GetMapping("/")
    public String showPortfolio(Model model) {
        // Get all projects
        List<Project> projects = projectService.getAllProjects();
        model.addAttribute("projects", projects);

        // Get all skills and group by category
        List<Skill> skills = skillService.getAllSkills();
        Map<String, List<Skill>> skillsByCategory = skills.stream()
                .collect(Collectors.groupingBy(Skill::getCategory));

        model.addAttribute("skillsByCategory", skillsByCategory);

        // Add sorted skills list for expertise section (sorted by proficiency in descending order)
        List<Skill> sortedSkills = skills.stream()
                .sorted(Comparator.comparing(Skill::getProficiency).reversed())
                .collect(Collectors.toList());
        model.addAttribute("skills", sortedSkills);

        // Add empty contact object for the contact form
        model.addAttribute("contact", new Contact());

        return "portfolio";
    }

    @PostMapping("/contact")
    public String submitContactForm(@ModelAttribute Contact contact, RedirectAttributes redirectAttributes) {
        try {
            contactService.saveContact(contact);
            redirectAttributes.addFlashAttribute("success", "Your message has been sent successfully! I'll get back to you soon.");
            return "redirect:/contact-success";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "There was an error sending your message. Please try again later.");
            return "redirect:/";
        }
    }

    @GetMapping("/contact-success")
    public String contactSuccess() {
        return "contact-success";
    }
}
