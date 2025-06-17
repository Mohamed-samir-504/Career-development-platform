
package org.sumerge.careerpackageservice.Controller;

import org.sumerge.careerpackageservice.Entity.UserFieldResponse;
import org.sumerge.careerpackageservice.Service.UserFieldResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/userfield")
public class UserFieldResponseController {

    @Autowired
    private UserFieldResponseService userFieldResponseService;

    @GetMapping
    public List<UserFieldResponse> getAll() {
        return userFieldResponseService.getAll();
    }

    @GetMapping("/{id}")
    public Optional<UserFieldResponse> getById(@PathVariable Long id) {
        return userFieldResponseService.getById(id);
    }

    @PostMapping
    public UserFieldResponse create(@RequestBody UserFieldResponse obj) {
        return userFieldResponseService.create(obj);
    }

    @PutMapping
    public UserFieldResponse update(@RequestBody UserFieldResponse obj) {
        return userFieldResponseService.create(obj);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userFieldResponseService.delete(id);
    }
}
