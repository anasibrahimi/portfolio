package com.anas.portfolio.repository;

import com.anas.portfolio.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    List<Contact> findByReadOrderByCreatedAtDesc(boolean read);
    List<Contact> findAllByOrderByCreatedAtDesc();
    long countByRead(boolean read);
}
