
package org.sumerge.careerpackageservice.Controller;

import org.springframework.http.ResponseEntity;
import org.sumerge.careerpackageservice.Dto.Request.SubmitSectionResponseRequest;
import org.sumerge.careerpackageservice.Entity.UserSectionResponse;
import org.sumerge.careerpackageservice.Service.UserSectionResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/user-section-response")
public class UserSectionResponseController {

    @Autowired
    private UserSectionResponseService userSectionResponseService;

    @GetMapping
    public List<UserSectionResponse> getAll() {
        return userSectionResponseService.getAll();
    }

    @GetMapping("/{id}")
    public Optional<UserSectionResponse> getById(@PathVariable UUID id) {
        return userSectionResponseService.getById(id);
    }

//    @PostMapping
//    public UserSectionResponse create(@RequestBody UserSectionResponse obj) {
//        return userSectionResponseService.create(obj);
//    }

    @PutMapping
    public UserSectionResponse update(@RequestBody UserSectionResponse obj) {
        return userSectionResponseService.create(obj);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        userSectionResponseService.delete(id);
    }

    @PostMapping
    public ResponseEntity<UserSectionResponse> submit(@RequestBody SubmitSectionResponseRequest request) {
        return ResponseEntity.ok(userSectionResponseService.submitSection(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserSectionResponse> update(@PathVariable UUID id, @RequestBody SubmitSectionResponseRequest request) {
        return ResponseEntity.ok(userSectionResponseService.updateSection(id, request));
    }
}
