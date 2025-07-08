package org.sumerge.learningservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.sumerge.learningservice.dto.BlogWikiDTO;
import org.sumerge.learningservice.entity.BlogWiki;
import org.sumerge.learningservice.enums.ContentType;
import org.sumerge.learningservice.repository.BlogWikiRepository;

import jakarta.persistence.EntityNotFoundException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BlogWikiServiceTest {

    @Mock
    private BlogWikiRepository repository;

    @InjectMocks
    private BlogWikiService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createBlog_shouldSaveBlogWithTypeBlog() {
        BlogWiki blog = BlogWiki.builder()
                .title("Title")
                .content("Content")
                .attachmentId("123")
                .type(ContentType.BLOG)
                .build();

        when(repository.save(any(BlogWiki.class))).thenReturn(blog);

        BlogWiki result = service.createBlog("Title", "Content", "123");

        assertEquals("Title", result.getTitle());
        assertEquals(ContentType.BLOG, result.getType());
        verify(repository).save(any(BlogWiki.class));
    }

    @Test
    void createWiki_shouldSaveWikiWithTypeWiki() {
        BlogWiki wiki = BlogWiki.builder()
                .title("Title")
                .content("Content")
                .attachmentId("456")
                .type(ContentType.WIKI)
                .build();

        when(repository.save(any(BlogWiki.class))).thenReturn(wiki);

        BlogWiki result = service.createWiki("Title", "Content", "456");

        assertEquals("Title", result.getTitle());
        assertEquals(ContentType.WIKI, result.getType());
        verify(repository).save(any(BlogWiki.class));
    }

    @Test
    void getByType_shouldReturnCorrectList() {
        List<BlogWiki> expected = List.of(
                BlogWiki.builder().title("Blog 1").type(ContentType.BLOG).build(),
                BlogWiki.builder().title("Blog 2").type(ContentType.BLOG).build()
        );

        when(repository.findByType(ContentType.BLOG)).thenReturn(expected);

        List<BlogWiki> result = service.getByType(ContentType.BLOG);

        assertEquals(2, result.size());
        verify(repository).findByType(ContentType.BLOG);
    }

    @Test
    void getById_shouldReturnBlogWiki() {
        UUID id = UUID.randomUUID();
        BlogWiki blog = BlogWiki.builder().id(id).title("Test").build();

        when(repository.findById(id)).thenReturn(Optional.of(blog));

        BlogWiki result = service.getById(id);

        assertEquals("Test", result.getTitle());
        verify(repository).findById(id);
    }

    @Test
    void getById_shouldThrowIfNotFound() {
        UUID id = UUID.randomUUID();
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> service.getById(id));
        verify(repository).findById(id);
    }

    @Test
    void delete_shouldCallRepository() {
        UUID id = UUID.randomUUID();

        service.delete(id);

        verify(repository).deleteById(id);
    }

    @Test
    void update_shouldModifyAndSaveEntity() {
        UUID id = UUID.randomUUID();
        BlogWiki existing = BlogWiki.builder().id(id).title("Old").build();

        when(repository.findById(id)).thenReturn(Optional.of(existing));

        BlogWikiDTO dto = new BlogWikiDTO();
        dto.setTitle("New");
        dto.setContent("Updated content");
        dto.setAttachmentId("999");

        service.update(id, dto);

        assertEquals("New", existing.getTitle());
        assertEquals("Updated content", existing.getContent());
        assertEquals("999", existing.getAttachmentId());

        verify(repository).save(existing);
    }

    @Test
    void update_shouldThrowIfNotFound() {
        UUID id = UUID.randomUUID();
        when(repository.findById(id)).thenReturn(Optional.empty());

        BlogWikiDTO dto = new BlogWikiDTO();

        assertThrows(EntityNotFoundException.class, () -> service.update(id, dto));
    }
}
