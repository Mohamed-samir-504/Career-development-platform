package org.sumerge.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.sumerge.userservice.entity.UserImage;
import org.sumerge.userservice.service.UserImageService;

import java.io.IOException;

@RestController
@RequestMapping("/users/images")
@RequiredArgsConstructor
public class UserImageController {

    private final UserImageService userImageService;

    @PostMapping("/upload")
    public ResponseEntity<String> upload(
            @RequestParam("file") MultipartFile file,
            @RequestParam String userId) throws IOException {

        String id = userImageService.uploadFile(file, userId);
        return ResponseEntity.ok(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> download(@PathVariable String id) {
        UserImage doc = userImageService.getFile(id);
        return ResponseEntity.ok()
                .header("Content-Type", doc.getContentType())
                .header("Content-Disposition", "attachment; filename=" + doc.getFileName())
                .body(doc.getContent());
    }

}
