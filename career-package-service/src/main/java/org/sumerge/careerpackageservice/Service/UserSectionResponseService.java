
package org.sumerge.careerpackageservice.Service;

import org.sumerge.careerpackageservice.Entity.UserSectionResponse;
import org.sumerge.careerpackageservice.Repository.UserSectionResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserSectionResponseService {

    @Autowired
    private UserSectionResponseRepository repository;

    public List<UserSectionResponse> getAll() {
        return repository.findAll();
    }

    public Optional<UserSectionResponse> getById(UUID id) {
        return repository.findById(id);
    }

    public UserSectionResponse create(UserSectionResponse obj) {
        return repository.save(obj);
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }
}
