
package org.sumerge.careerpackageservice.Service;

import org.sumerge.careerpackageservice.Entity.SectionTemplate;
import org.sumerge.careerpackageservice.Repository.SectionTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SectionTemplateService {

    @Autowired
    private SectionTemplateRepository repository;

    public List<SectionTemplate> getAll() {
        return repository.findAll();
    }

    public Optional<SectionTemplate> getById(UUID id) {
        return repository.findById(id);
    }

    public SectionTemplate create(SectionTemplate obj) {
        return repository.save(obj);
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }

//    public List<SectionTemplate> getByTitle(String value) {
//        return repository.findAll().stream()
//                .filter(obj -> obj.getTitle().equalsIgnoreCase(value))
//                .toList();
//    }
}
