package org.sumerge.learningservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.sumerge.learningservice.config.TestSecurityConfig;
import org.sumerge.learningservice.dto.BlogWikiDTO;
import org.sumerge.learningservice.entity.BlogWiki;
import org.sumerge.learningservice.enums.ContentType;
import org.sumerge.learningservice.service.BlogWikiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BlogWikiController.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = {BlogWikiController.class, TestSecurityConfig.class})
class BlogWikiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BlogWikiService blogWikiService;

    @Autowired
    private ObjectMapper objectMapper;

    private BlogWikiDTO sampleDto;
    private BlogWiki sampleEntity;

    @BeforeEach
    void setup() {
        sampleDto = new BlogWikiDTO();
        sampleDto.setTitle("Test Title");
        sampleDto.setContent("Test Content");
        sampleDto.setAttachmentId("att123");

        sampleEntity = BlogWiki.builder()
                .id(UUID.randomUUID())
                .title("Test Title")
                .content("Test Content")
                .attachmentId("att123")
                .type(ContentType.BLOG)
                .build();
    }

    @Test
    void createBlog_shouldReturnBlogWiki() throws Exception {
        Mockito.when(blogWikiService.createBlog(any(), any(), any())).thenReturn(sampleEntity);

        mockMvc.perform(post("/content/blogs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Title"))
                .andExpect(jsonPath("$.type").value("BLOG"));
    }

    @Test
    void createWiki_shouldReturnBlogWiki() throws Exception {
        sampleEntity.setType(ContentType.WIKI);
        Mockito.when(blogWikiService.createWiki(any(), any(), any())).thenReturn(sampleEntity);

        mockMvc.perform(post("/content/wikis")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.type").value("WIKI"));
    }

    @Test
    void getAllBlogs_shouldReturnListOfBlogs() throws Exception {
        Mockito.when(blogWikiService.getByType(ContentType.BLOG)).thenReturn(List.of(sampleEntity));

        mockMvc.perform(get("/content/blogs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Test Title"));
    }

    @Test
    void getAllWikis_shouldReturnListOfWikis() throws Exception {
        sampleEntity.setType(ContentType.WIKI);
        Mockito.when(blogWikiService.getByType(ContentType.WIKI)).thenReturn(List.of(sampleEntity));

        mockMvc.perform(get("/content/wikis"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].type").value("WIKI"));
    }

    @Test
    void getEntryById_shouldReturnEntry() throws Exception {
        UUID id = UUID.randomUUID();
        Mockito.when(blogWikiService.getById(id)).thenReturn(sampleEntity);

        mockMvc.perform(get("/content/entry/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Title"));
    }

    @Test
    void deleteEntry_shouldReturnNoContent() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(delete("/content/entry/" + id))
                .andExpect(status().isNoContent());

        Mockito.verify(blogWikiService).delete(id);
    }

    @Test
    void update_shouldReturnNoContent() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(put("/content/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleDto)))
                .andExpect(status().isNoContent());

        Mockito.verify(blogWikiService).update(eq(id), any(BlogWikiDTO.class));
    }
}
