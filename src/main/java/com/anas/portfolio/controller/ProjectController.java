package com.anas.portfolio.controller;

import com.anas.portfolio.model.Project;
import com.anas.portfolio.service.FileStorageService;
import com.anas.portfolio.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/admin/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private FileStorageService fileStorageService;

    @GetMapping
    public String listProjects(Model model) {
        model.addAttribute("projects", projectService.getAllProjects());
        return "admin/project-list";
    }

    @GetMapping("/new")
    public String showProjectForm(Model model) {
        model.addAttribute("project", new Project());
        model.addAttribute("isNew", true);
        return "admin/project-form";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Project> project = projectService.getProjectById(id);

        if (project.isPresent()) {
            model.addAttribute("project", project.get());
            model.addAttribute("isNew", false);
            return "admin/project-form";
        } else {
            redirectAttributes.addFlashAttribute("error", "Project not found");
            return "redirect:/admin/projects";
        }
    }

    @PostMapping("/save")
    public String saveProject(@ModelAttribute Project project,
                             @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
                             @RequestParam(value = "deleteExistingImage", required = false) boolean deleteExistingImage,
                             RedirectAttributes redirectAttributes) {
        try {
            // Handle image file upload
            if (imageFile != null && !imageFile.isEmpty()) {
                // If there's an existing image and a new one is being uploaded, delete the old one
                if (project.getImageUrl() != null && !project.getImageUrl().isEmpty()) {
                    fileStorageService.deleteFile(project.getImageUrl());
                }

                // Store the new image and update the project
                String imageUrl = fileStorageService.storeFile(imageFile);
                project.setImageUrl(imageUrl);
            } else if (deleteExistingImage && project.getImageUrl() != null) {
                // If the delete checkbox is checked, delete the image and clear the URL
                fileStorageService.deleteFile(project.getImageUrl());
                project.setImageUrl(null);
            }

            // Save the project
            projectService.saveProject(project);
            redirectAttributes.addFlashAttribute("success", "Project saved successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error saving project: " + e.getMessage());
        }

        return "redirect:/admin/projects";
    }

    @GetMapping("/delete/{id}")
    public String deleteProject(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            // Get the project to access its image URL before deletion
            Optional<Project> projectOpt = projectService.getProjectById(id);
            if (projectOpt.isPresent()) {
                Project project = projectOpt.get();

                // Delete the associated image file if it exists
                if (project.getImageUrl() != null && !project.getImageUrl().isEmpty()) {
                    fileStorageService.deleteFile(project.getImageUrl());
                }
            }

            // Delete the project from the database
            projectService.deleteProject(id);
            redirectAttributes.addFlashAttribute("success", "Project deleted successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error deleting project: " + e.getMessage());
        }

        return "redirect:/admin/projects";
    }
}
