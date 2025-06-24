package org.sumerge.learningservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.sumerge.learningservice.entity.BlogWiki;
import org.sumerge.learningservice.enums.ContentType;
import org.sumerge.learningservice.repository.BlogWikiRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BlogWikiService {

    private final BlogWikiRepository repository;

    public BlogWiki createBlog(String title, String content, String attachmentId) {
        BlogWiki blog = BlogWiki.builder()
                .title(title)
                .content(content)
                .attachmentId(attachmentId)
                .type(ContentType.BLOG)
                .build();

        return repository.save(blog);
    }

    public BlogWiki createWiki(String title, String content, String attachmentId) {
        BlogWiki wiki = BlogWiki.builder()
                .title(title)
                .content(content)
                .attachmentId(attachmentId)
                .type(ContentType.WIKI)
                .build();

        return repository.save(wiki);
    }

    public List<BlogWiki> getByType(ContentType type) {
        return repository.findByType(type);
    }

    public BlogWiki getById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entry not found"));
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }
}
