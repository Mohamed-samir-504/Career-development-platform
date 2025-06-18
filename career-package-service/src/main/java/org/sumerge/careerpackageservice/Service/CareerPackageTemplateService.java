
package org.sumerge.careerpackageservice.Service;

import org.sumerge.careerpackageservice.Entity.CareerPackageTemplate;
import org.sumerge.careerpackageservice.Repository.CareerPackageTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CareerPackageTemplateService {

    @Autowired
    private CareerPackageTemplateRepository repository;

    public List<CareerPackageTemplate> getAll() {
        return repository.findAll();
    }

    public Optional<CareerPackageTemplate> getById(UUID id) {
        return repository.findById(id);
    }

    public CareerPackageTemplate create(CareerPackageTemplate obj) {
        return repository.save(obj);
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }

//    public List<CareerPackageTemplate> getByTitle(String value) {
//        return repository.findAll().stream()
//                .filter(obj -> obj.getTitle().equalsIgnoreCase(value))
//                .toList();
//    }
}
