package com.anas.portfolio.service;

import com.anas.portfolio.model.Skill;
import com.anas.portfolio.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SkillService {
    
    @Autowired
    private SkillRepository skillRepository;
    
    public List<Skill> getAllSkills() {
        return skillRepository.findAll();
    }
    
    public Optional<Skill> getSkillById(Long id) {
        return skillRepository.findById(id);
    }
    
    public List<Skill> getSkillsByCategory(String category) {
        return skillRepository.findByCategory(category);
    }
    
    public Skill saveSkill(Skill skill) {
        return skillRepository.save(skill);
    }
    
    public void deleteSkill(Long id) {
        skillRepository.deleteById(id);
    }
}
