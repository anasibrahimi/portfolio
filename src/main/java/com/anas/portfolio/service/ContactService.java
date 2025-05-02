package com.anas.portfolio.service;

import com.anas.portfolio.model.Contact;
import com.anas.portfolio.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactService {
    
    @Autowired
    private ContactRepository contactRepository;
    
    public Contact saveContact(Contact contact) {
        return contactRepository.save(contact);
    }
    
    public List<Contact> getAllContacts() {
        return contactRepository.findAllByOrderByCreatedAtDesc();
    }
    
    public List<Contact> getUnreadContacts() {
        return contactRepository.findByReadOrderByCreatedAtDesc(false);
    }
    
    public Optional<Contact> getContactById(Long id) {
        return contactRepository.findById(id);
    }
    
    public Contact markAsRead(Long id) {
        Optional<Contact> contactOpt = contactRepository.findById(id);
        if (contactOpt.isPresent()) {
            Contact contact = contactOpt.get();
            contact.setRead(true);
            return contactRepository.save(contact);
        }
        return null;
    }
    
    public void deleteContact(Long id) {
        contactRepository.deleteById(id);
    }
    
    public long getUnreadCount() {
        return contactRepository.countByRead(false);
    }
}
