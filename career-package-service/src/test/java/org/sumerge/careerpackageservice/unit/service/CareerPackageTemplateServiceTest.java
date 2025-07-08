package org.sumerge.careerpackageservice.unit.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.sumerge.careerpackageservice.Dto.*;
import org.sumerge.careerpackageservice.Dto.Request.CareerPackageEditRequest;
import org.sumerge.careerpackageservice.Dto.Request.CreateCareerPackageRequest;
import org.sumerge.careerpackageservice.Entity.*;
import org.sumerge.careerpackageservice.Enums.SectionType;
import org.sumerge.careerpackageservice.Exception.PackageNotFoundException;
import org.sumerge.careerpackageservice.Mapper.UserCareerPackageMapper;
import org.sumerge.careerpackageservice.Repository.*;
import org.sumerge.careerpackageservice.Service.CareerPackageTemplateService;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CareerPackageTemplateServiceTest {

    @Mock
    private CareerPackageTemplateRepository careerPackageTemplateRepository;

    @Mock
    private SectionTemplateRepository sectionTemplateRepository;

    @Mock
    private SectionFieldTemplateRepository sectionFieldTemplateRepository;

    @Mock
    private UserCareerPackageMapper mapper;

    @InjectMocks
    private CareerPackageTemplateService service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAll() {
        List<CareerPackageTemplate> mockEntities = List.of(
                new CareerPackageTemplate("Title", "Description", new ArrayList<>())
        );
        List<CareerPackageTemplateDTO> mockDTOs = List.of(new CareerPackageTemplateDTO());

        when(careerPackageTemplateRepository.findAll()).thenReturn(mockEntities);
        when(mapper.toCareerPackageDtoList(mockEntities)).thenReturn(mockDTOs);

        List<CareerPackageTemplateDTO> result = service.getAll();

        assertEquals(1, result.size());
        verify(careerPackageTemplateRepository).findAll();
        verify(mapper).toCareerPackageDtoList(mockEntities);
    }

    @Test
    void testCreateNewPackage() {
        CreateCareerPackageRequest request = new CreateCareerPackageRequest("New Title", "New Description");

        CareerPackageTemplate savedEntity = new CareerPackageTemplate("New Title", "New Description", new ArrayList<>());
        CareerPackageTemplateDTO dto = new CareerPackageTemplateDTO();

        when(careerPackageTemplateRepository.save(any(CareerPackageTemplate.class))).thenReturn(savedEntity);
        when(mapper.toDto(savedEntity)).thenReturn(dto);

        CareerPackageTemplateDTO result = service.createNewPackage(request);

        assertNotNull(result);
        verify(careerPackageTemplateRepository).save(any(CareerPackageTemplate.class));
        verify(mapper).toDto(savedEntity);
    }

    @Test
    void testSyncChanges_updatesFieldsAndSections() {
        UUID packageId = UUID.randomUUID();

        CareerPackageTemplate template = new CareerPackageTemplate("Title", "Desc", new ArrayList<>());
        when(careerPackageTemplateRepository.findById(packageId)).thenReturn(Optional.of(template));

        UUID sectionId = UUID.randomUUID();
        SectionTemplateDTO sectionDTO = new SectionTemplateDTO(
                sectionId,
                "Updated Section",
                SectionType.EXPERIENCE,
                "Do this",
                new ArrayList<>()
        );
        SectionTemplate sectionEntity = new SectionTemplate(
                sectionId,
                "Updated Section",
                SectionType.EXPERIENCE,
                "Do this",
                new ArrayList<>()
        );
        when(sectionTemplateRepository.findById(sectionId)).thenReturn(Optional.of(sectionEntity));

        UUID fieldId = UUID.randomUUID();
        SectionFieldTemplateDTO fieldDTO = new SectionFieldTemplateDTO(fieldId, "Label", "key", "TEXT", true, sectionId);
        SectionFieldTemplate fieldEntity = new SectionFieldTemplate(
                fieldId,
                "Old Label",
                "oldKey",
                "TEXT",
                false
        );

        when(sectionFieldTemplateRepository.findById(fieldId)).thenReturn(Optional.of(fieldEntity));

        CareerPackageEditRequest request = new CareerPackageEditRequest(
                "Updated Title",
                "Updated Description",
                List.of(sectionDTO),
                List.of(),
                List.of(),
                List.of(fieldDTO),
                List.of(),
                List.of()
        );

        service.syncChanges(packageId, request);

        assertEquals("Updated Title", template.getTitle());
        assertEquals("Updated Description", template.getDescription());

        assertEquals("Updated Section", sectionEntity.getTitle());
        assertEquals("Do this", sectionEntity.getInstructions());

        assertEquals("Label", fieldEntity.getLabel());
        assertEquals("key", fieldEntity.getFieldKey());
        assertEquals("TEXT", fieldEntity.getFieldType());
        assertTrue(fieldEntity.isRequired());

        verify(sectionTemplateRepository).save(sectionEntity);
        verify(sectionFieldTemplateRepository).save(fieldEntity);
        verify(careerPackageTemplateRepository).save(template);
    }

    @Test
    void testSyncChanges_throwsIfPackageNotFound() {
        UUID packageId = UUID.randomUUID();
        when(careerPackageTemplateRepository.findById(packageId)).thenReturn(Optional.empty());

        CareerPackageEditRequest request = new CareerPackageEditRequest();

        PackageNotFoundException ex = assertThrows(PackageNotFoundException.class, () -> {
            service.syncChanges(packageId, request);
        });

        assertEquals("Package not found", ex.getMessage());
    }
}
