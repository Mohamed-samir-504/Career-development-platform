package org.sumerge.learningservice.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.sumerge.learningservice.dto.template.LearningMaterialTemplateDTO;
import org.sumerge.learningservice.dto.template.LearningSectionTemplateDTO;
import org.sumerge.learningservice.entity.LearningMaterialTemplate;
import org.sumerge.learningservice.entity.LearningSectionTemplate;
import org.sumerge.learningservice.enums.SectionType;
import org.sumerge.learningservice.mapper.LearningMaterialMapper;
import org.sumerge.learningservice.repository.LearningMaterialTemplateRepository;
import org.sumerge.learningservice.repository.LearningSectionResponseRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LearningMaterialServiceTest {

    @Mock
    private LearningMaterialTemplateRepository templateRepository;

    @Mock
    private LearningMaterialMapper mapper;

    @Mock
    private LearningSectionResponseRepository responseRepository;

    @InjectMocks
    private LearningMaterialService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTemplate_shouldMapAndSaveTemplate() {
        LearningMaterialTemplateDTO dto = new LearningMaterialTemplateDTO();
        dto.setTitle("New Template");

        LearningMaterialTemplate entity = new LearningMaterialTemplate();
        entity.setTitle("New Template");

        when(mapper.toEntity(dto)).thenReturn(entity);
        when(templateRepository.save(entity)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(dto);

        LearningMaterialTemplateDTO result = service.createTemplate(dto);

        assertEquals("New Template", result.getTitle());
        verify(templateRepository).save(entity);
    }

    @Test
    void getAllTemplates_shouldReturnMappedList() {
        List<LearningMaterialTemplate> entities = List.of(
                new LearningMaterialTemplate(), new LearningMaterialTemplate()
        );

        when(templateRepository.findAll()).thenReturn(entities);
        when(mapper.toDto((LearningMaterialTemplate) any())).thenReturn(new LearningMaterialTemplateDTO());

        List<LearningMaterialTemplateDTO> result = service.getAllTemplates();

        assertEquals(2, result.size());
        verify(templateRepository).findAll();
        verify(mapper, times(2)).toDto((LearningMaterialTemplate) any());
    }

    @Test
    void getTemplateById_shouldReturnDtoIfExists() {
        UUID id = UUID.randomUUID();
        LearningMaterialTemplate entity = new LearningMaterialTemplate();
        entity.setId(id);

        LearningMaterialTemplateDTO dto = new LearningMaterialTemplateDTO();
        dto.setId(id);

        when(templateRepository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toDto(entity)).thenReturn(dto);

        LearningMaterialTemplateDTO result = service.getTemplateById(id);

        assertEquals(id, result.getId());
        verify(templateRepository).findById(id);
    }

    @Test
    void getTemplateById_shouldThrowIfNotFound() {
        UUID id = UUID.randomUUID();
        when(templateRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> service.getTemplateById(id));
    }

    @Test
    void getTemplateByCareerPackageId_shouldReturnMappedList() {
        UUID careerPackageId = UUID.randomUUID();
        List<LearningMaterialTemplate> entities = List.of(new LearningMaterialTemplate());

        when(templateRepository.findByCareerPackageId(careerPackageId)).thenReturn(entities);
        when(mapper.toDto((LearningMaterialTemplate) any())).thenReturn(new LearningMaterialTemplateDTO());

        List<LearningMaterialTemplateDTO> result = service.getTemplateByCareerPackageId(careerPackageId);

        assertEquals(1, result.size());
        verify(templateRepository).findByCareerPackageId(careerPackageId);
    }

    @Test
    void deleteById_shouldCallRepository() {
        UUID id = UUID.randomUUID();
        service.deleteById(id);
        verify(templateRepository).deleteById(id);
    }

    @Test
    void update_shouldModifyExistingTemplateAndSave() {
        UUID templateId = UUID.randomUUID();

        LearningSectionTemplate existingSection = new LearningSectionTemplate();
        UUID existingSectionId = UUID.randomUUID();
        existingSection.setId(existingSectionId);

        LearningMaterialTemplate existingTemplate = new LearningMaterialTemplate();
        existingTemplate.setId(templateId);
        existingTemplate.setSections(new ArrayList<>(List.of(existingSection)));

        LearningSectionTemplateDTO updatedSectionDTO = new LearningSectionTemplateDTO();
        updatedSectionDTO.setId(existingSectionId);
        updatedSectionDTO.setTitle("Updated Section");
        updatedSectionDTO.setType(SectionType.ARTICLE);
        updatedSectionDTO.setInstructions("Instructions");
        updatedSectionDTO.setContent("Content");
        updatedSectionDTO.setRequiresSubmission(true);
        updatedSectionDTO.setAttachmentId("att-1");

        LearningMaterialTemplateDTO dto = new LearningMaterialTemplateDTO();
        dto.setTitle("Updated Title");
        dto.setPoints(10);
        dto.setDescription("Updated Desc");
        dto.setCareerPackageId(UUID.randomUUID());
        dto.setSections(List.of(updatedSectionDTO));

        when(templateRepository.findById(templateId)).thenReturn(Optional.of(existingTemplate));
        when(responseRepository.existsBySectionTemplateId(existingSectionId)).thenReturn(false);

        service.update(templateId, dto);

        assertEquals("Updated Title", existingTemplate.getTitle());
        assertEquals(1, existingTemplate.getSections().size());
        verify(templateRepository).save(existingTemplate);
    }

    @Test
    void update_shouldThrowIfTemplateNotFound() {
        UUID id = UUID.randomUUID();
        when(templateRepository.findById(id)).thenReturn(Optional.empty());

        LearningMaterialTemplateDTO dto = new LearningMaterialTemplateDTO();
        assertThrows(EntityNotFoundException.class, () -> service.update(id, dto));
    }
}
