package com.anas.portfolio.controller;

import com.anas.portfolio.model.Skill;
import com.anas.portfolio.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/admin/skills")
public class SkillController {
    
    @Autowired
    private SkillService skillService;
    
    @GetMapping
    public String listSkills(Model model) {
        model.addAttribute("skills", skillService.getAllSkills());
        return "admin/skill-list";
    }
    
    @GetMapping("/new")
    public String showSkillForm(Model model) {
        model.addAttribute("skill", new Skill());
        model.addAttribute("isNew", true);
        return "admin/skill-form";
    }
    
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Skill> skill = skillService.getSkillById(id);
        
        if (skill.isPresent()) {
            model.addAttribute("skill", skill.get());
            model.addAttribute("isNew", false);
            return "admin/skill-form";
        } else {
            redirectAttributes.addFlashAttribute("error", "Skill not found");
            return "redirect:/admin/skills";
        }
    }
    
    @PostMapping("/save")
    public String saveSkill(@ModelAttribute Skill skill, RedirectAttributes redirectAttributes) {
        skillService.saveSkill(skill);
        redirectAttributes.addFlashAttribute("success", "Skill saved successfully");
        return "redirect:/admin/skills";
    }
    
    @GetMapping("/delete/{id}")
    public String deleteSkill(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        skillService.deleteSkill(id);
        redirectAttributes.addFlashAttribute("success", "Skill deleted successfully");
        return "redirect:/admin/skills";
    }
}
