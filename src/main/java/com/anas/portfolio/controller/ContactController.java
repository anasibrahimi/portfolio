package com.anas.portfolio.controller;

import com.anas.portfolio.model.Contact;
import com.anas.portfolio.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/admin/contacts")
public class ContactController {
    
    @Autowired
    private ContactService contactService;
    
    @GetMapping
    public String listContacts(Model model) {
        model.addAttribute("contacts", contactService.getAllContacts());
        model.addAttribute("unreadCount", contactService.getUnreadCount());
        return "admin/contact-list";
    }
    
    @GetMapping("/{id}")
    public String viewContact(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Contact> contactOpt = contactService.getContactById(id);
        
        if (contactOpt.isPresent()) {
            Contact contact = contactOpt.get();
            // Mark as read if it's not already
            if (!contact.isRead()) {
                contactService.markAsRead(id);
            }
            model.addAttribute("contact", contact);
            return "admin/contact-detail";
        } else {
            redirectAttributes.addFlashAttribute("error", "Contact message not found");
            return "redirect:/admin/contacts";
        }
    }
    
    @GetMapping("/delete/{id}")
    public String deleteContact(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        contactService.deleteContact(id);
        redirectAttributes.addFlashAttribute("success", "Contact message deleted successfully");
        return "redirect:/admin/contacts";
    }
}
