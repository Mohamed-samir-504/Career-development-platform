package org.sumerge.userservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.sumerge.userservice.entity.UserImage;

public interface UserImageRepository extends MongoRepository<UserImage, String> {
}
