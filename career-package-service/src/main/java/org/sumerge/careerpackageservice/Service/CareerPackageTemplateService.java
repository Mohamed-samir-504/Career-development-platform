
package org.sumerge.careerpackageservice.Service;

import org.sumerge.careerpackageservice.Entity.CareerPackageTemplate;
import org.sumerge.careerpackageservice.Repository.CareerPackageTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CareerPackageTemplateService {

    @Autowired
    private CareerPackageTemplateRepository repository;

    public List<CareerPackageTemplate> getAll() {
        return repository.findAll();
    }

    public Optional<CareerPackageTemplate> getById(Long id) {
        return repository.findById(id);
    }

    public CareerPackageTemplate save(CareerPackageTemplate careerPackage) {
        return repository.save(careerPackage);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
