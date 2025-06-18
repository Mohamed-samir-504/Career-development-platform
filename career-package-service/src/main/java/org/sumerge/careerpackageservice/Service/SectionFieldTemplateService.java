
package org.sumerge.careerpackageservice.Service;

import org.sumerge.careerpackageservice.Entity.SectionFieldTemplate;
import org.sumerge.careerpackageservice.Repository.SectionFieldTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SectionFieldTemplateService {

    @Autowired
    private SectionFieldTemplateRepository repository;

    public List<SectionFieldTemplate> getAll() {
        return repository.findAll();
    }

    public Optional<SectionFieldTemplate> getById(UUID id) {
        return repository.findById(id);
    }

    public SectionFieldTemplate create(SectionFieldTemplate obj) {
        return repository.save(obj);
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }

//    public List<SectionFieldTemplate> getByLabel(String value) {
//        return repository.findAll().stream()
//                .filter(obj -> obj.getLabel().equalsIgnoreCase(value))
//                .toList();
//    }
}
