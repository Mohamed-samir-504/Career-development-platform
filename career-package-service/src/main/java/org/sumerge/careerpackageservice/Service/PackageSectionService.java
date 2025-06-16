
package org.sumerge.careerpackageservice.Service;

import org.sumerge.careerpackageservice.Entity.PackageSection;
import org.sumerge.careerpackageservice.Repository.PackageSectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PackageSectionService {

    @Autowired
    private PackageSectionRepository repository;

    public List<PackageSection> getAll() {
        return repository.findAll();
    }

    public PackageSection save(PackageSection section) {
        return repository.save(section);
    }
}
