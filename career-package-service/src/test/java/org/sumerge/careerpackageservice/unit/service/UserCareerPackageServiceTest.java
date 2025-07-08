package org.sumerge.careerpackageservice.unit.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.kafka.core.KafkaTemplate;
import org.sumerge.careerpackageservice.Dto.Request.AssignCareerPackageRequest;
import org.sumerge.careerpackageservice.Dto.Request.SendNotificationRequest;
import org.sumerge.careerpackageservice.Dto.UserCareerPackageDTO;
import org.sumerge.careerpackageservice.Entity.CareerPackageTemplate;
import org.sumerge.careerpackageservice.Entity.UserCareerPackage;
import org.sumerge.careerpackageservice.Enums.PackageStatus;
import org.sumerge.careerpackageservice.Exception.KafkaPublishException;
import org.sumerge.careerpackageservice.Exception.PackageNotFoundException;
import org.sumerge.careerpackageservice.Mapper.UserCareerPackageMapper;
import org.sumerge.careerpackageservice.Repository.CareerPackageTemplateRepository;
import org.sumerge.careerpackageservice.Repository.UserCareerPackageRepository;
import org.sumerge.careerpackageservice.Service.UserCareerPackageService;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserCareerPackageServiceTest {

    @Mock
    private UserCareerPackageRepository userCareerPackageRepository;

    @Mock
    private CareerPackageTemplateRepository templateRepository;

    @Mock
    private UserCareerPackageMapper mapper;

    @Mock
    private KafkaTemplate<String, SendNotificationRequest> kafkaTemplate;

    @InjectMocks
    private UserCareerPackageService service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAssignPackage_success() {
        UUID userId = UUID.randomUUID();
        UUID reviewerId = UUID.randomUUID();
        UUID templateId = UUID.randomUUID();
        PackageStatus status = PackageStatus.IN_PROGRESS;

        CareerPackageTemplate template = new CareerPackageTemplate("Title", "Desc", new ArrayList<>());
        when(templateRepository.findById(templateId)).thenReturn(Optional.of(template));

        AssignCareerPackageRequest request = new AssignCareerPackageRequest(userId, reviewerId, templateId, status);

        UserCareerPackage saved = new UserCareerPackage(userId, reviewerId, status, template);
        UserCareerPackageDTO dto = new UserCareerPackageDTO();

        when(userCareerPackageRepository.save(any(UserCareerPackage.class))).thenReturn(saved);
        when(mapper.toDto(any(UserCareerPackage.class))).thenReturn(dto);

        UserCareerPackageDTO result = service.assignPackage(request);

        assertNotNull(result);
        verify(templateRepository).findById(templateId);
        verify(userCareerPackageRepository).save(any(UserCareerPackage.class));
        verify(mapper).toDto(saved);
    }

    @Test
    void testAssignPackage_templateNotFound() {
        UUID templateId = UUID.randomUUID();
        AssignCareerPackageRequest request = new AssignCareerPackageRequest(UUID.randomUUID(), UUID.randomUUID(), templateId, PackageStatus.IN_PROGRESS);

        when(templateRepository.findById(templateId)).thenReturn(Optional.empty());

        assertThrows(PackageNotFoundException.class, () -> service.assignPackage(request));
    }

    @Test
    void testUpdateUserCareerPackage_notFound() {
        UUID id = UUID.randomUUID();
        UserCareerPackage request = new UserCareerPackage(id, UUID.randomUUID(), PackageStatus.UNDER_REVIEW, null);

        when(userCareerPackageRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(PackageNotFoundException.class, () -> service.updateUserCareerPackage(id, request));
    }

    @Test
    void testSendNotificationToEmployee_approval() {
        UUID userId = UUID.randomUUID();
        UUID reviewerId = UUID.randomUUID();
        UserCareerPackage userCareerPackage = new UserCareerPackage(userId, reviewerId, PackageStatus.APPROVED, null);

        service.sendNotificationToEmployee(userCareerPackage);

        verify(kafkaTemplate).send(eq("career-package-submitted"), argThat(req ->
                req.getSenderId().equals(reviewerId) &&
                        req.getReceiverId().equals(userId) &&
                        req.getMessage().contains("approved") &&
                        req.getType().equals("APPROVAL")
        ));

    }

    @Test
    void testSendNotificationToEmployee_rejection() {
        UUID userId = UUID.randomUUID();
        UUID reviewerId = UUID.randomUUID();
        UserCareerPackage userCareerPackage = new UserCareerPackage(userId, reviewerId, PackageStatus.REJECTED, null);

        service.sendNotificationToEmployee(userCareerPackage);

        verify(kafkaTemplate).send(eq("career-package-submitted"), argThat(req ->
                req.getSenderId().equals(reviewerId) &&
                        req.getReceiverId().equals(userId) &&
                        req.getMessage().contains("rejected") &&
                        req.getType().equals("REJECTION")
        ));

    }

    @Test
    void testSendNotificationToManager_kafkaFails() {
        UUID userId = UUID.randomUUID();
        UUID reviewerId = UUID.randomUUID();
        UserCareerPackage userCareerPackage = new UserCareerPackage(userId, reviewerId, PackageStatus.UNDER_REVIEW, null);

        doThrow(new RuntimeException("Kafka down")).when(kafkaTemplate).send(anyString(), any(SendNotificationRequest.class));

        assertThrows(KafkaPublishException.class, () -> service.sendNotificationToManager(userCareerPackage));
    }
}

