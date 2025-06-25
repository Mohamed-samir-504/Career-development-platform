package org.sumerge.learningservice.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.sumerge.learningservice.entity.LearningDocument;

public interface LearningDocumentRepository extends MongoRepository<LearningDocument, String> {
}
