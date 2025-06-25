package org.sumerge.learningservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.sumerge.learningservice.entity.BlogWiki;
import org.sumerge.learningservice.enums.ContentType;

import java.util.List;
import java.util.UUID;

public interface BlogWikiRepository extends JpaRepository<BlogWiki, UUID> {
    List<BlogWiki> findByType(ContentType type);
}