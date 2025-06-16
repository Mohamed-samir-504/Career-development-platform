
package org.sumerge.careerpackageservice.Service;

import org.sumerge.careerpackageservice.Entity.CareerPackage;
import org.sumerge.careerpackageservice.Repository.CareerPackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CareerPackageService {

    @Autowired
    private CareerPackageRepository repository;

    public List<CareerPackage> getAll() {
        return repository.findAll();
    }

    public Optional<CareerPackage> getById(Long id) {
        return repository.findById(id);
    }

    public CareerPackage save(CareerPackage careerPackage) {
        return repository.save(careerPackage);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
