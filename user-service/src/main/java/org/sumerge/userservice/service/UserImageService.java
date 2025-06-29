package org.sumerge.userservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.sumerge.userservice.entity.UserImage;
import org.sumerge.userservice.repository.UserImageRepository;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class UserImageService {

    private final UserImageRepository userImageRepository;

    public String uploadFile(MultipartFile file, String userId) throws IOException {
        UserImage image = UserImage.builder()
                .fileName(file.getOriginalFilename())
                .contentType(file.getContentType())
                .content(file.getBytes())
                .userId(userId)
                .build();

        return userImageRepository.save(image).getId();
    }

    public UserImage getFile(String id) {
        return userImageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("File not found"));
    }
}
