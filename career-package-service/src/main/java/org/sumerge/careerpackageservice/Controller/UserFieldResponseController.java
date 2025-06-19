
package org.sumerge.careerpackageservice.Controller;

import org.springframework.http.ResponseEntity;
import org.sumerge.careerpackageservice.Dto.Request.SubmitFieldResponseRequest;
import org.sumerge.careerpackageservice.Entity.UserFieldResponse;
import org.sumerge.careerpackageservice.Service.UserFieldResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/user-field-response")
public class UserFieldResponseController {

    @Autowired
    private UserFieldResponseService userFieldResponseService;

    @GetMapping
    public List<UserFieldResponse> getAll() {
        return userFieldResponseService.getAll();
    }

    @GetMapping("/{id}")
    public Optional<UserFieldResponse> getById(@PathVariable UUID id) {
        return userFieldResponseService.getById(id);
    }

//    @PostMapping
//    public UserFieldResponse create(@RequestBody UserFieldResponse obj) {
//        return userFieldResponseService.create(obj);
//    }

//    @PutMapping
//    public UserFieldResponse update(@RequestBody UserFieldResponse obj) {
//        return userFieldResponseService.create(obj);
//    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        userFieldResponseService.delete(id);
    }

    @PostMapping
    public ResponseEntity<UserFieldResponse> submit(@RequestBody SubmitFieldResponseRequest request) {
        return ResponseEntity.ok(userFieldResponseService.submitField(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserFieldResponse> update(@PathVariable UUID id, @RequestBody SubmitFieldResponseRequest request) {
        return ResponseEntity.ok(userFieldResponseService.updateField(id, request.getValue()));
    }
}
