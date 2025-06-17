
package org.sumerge.careerpackageservice.Service;

import org.sumerge.careerpackageservice.Entity.PackageSectionTemplate;
import org.sumerge.careerpackageservice.Repository.PackageSectionTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PackageSectionTemplateService {

    @Autowired
    private PackageSectionTemplateRepository repository;

    public List<PackageSectionTemplate> getAll() {
        return repository.findAll();
    }

    public PackageSectionTemplate save(PackageSectionTemplate section) {
        return repository.save(section);
    }
}
