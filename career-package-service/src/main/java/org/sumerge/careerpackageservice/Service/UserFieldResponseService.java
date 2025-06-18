
package org.sumerge.careerpackageservice.Service;

import org.sumerge.careerpackageservice.Entity.UserFieldResponse;
import org.sumerge.careerpackageservice.Repository.UserFieldResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserFieldResponseService {

    @Autowired
    private UserFieldResponseRepository repository;

    public List<UserFieldResponse> getAll() {
        return repository.findAll();
    }

    public Optional<UserFieldResponse> getById(UUID id) {
        return repository.findById(id);
    }

    public UserFieldResponse create(UserFieldResponse obj) {
        return repository.save(obj);
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }
}
