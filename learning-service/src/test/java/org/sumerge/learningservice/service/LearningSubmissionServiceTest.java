package org.sumerge.learningservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.kafka.core.KafkaTemplate;
import org.sumerge.learningservice.dto.SendNotificationRequest;
import org.sumerge.learningservice.dto.submission.LearningSubmissionDTO;
import org.sumerge.learningservice.entity.*;
import org.sumerge.learningservice.enums.SubmissionStatus;
import org.sumerge.learningservice.mapper.LearningMaterialMapper;
import org.sumerge.learningservice.repository.LearningMaterialTemplateRepository;
import org.sumerge.learningservice.repository.LearningSubmissionRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LearningSubmissionServiceTest {

    @Mock private LearningSubmissionRepository submissionRepository;
    @Mock private LearningMaterialTemplateRepository templateRepository;
    @Mock private LearningMaterialMapper mapper;
    @Mock private KafkaTemplate<String, SendNotificationRequest> kafkaTemplate;
    @Mock private LearningScoreService scoreService;
    @Mock private LearningMaterialService learningMaterialService;

    @InjectMocks
    private LearningSubmissionService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getSubmissionsByUser_shouldMapList() {
        UUID userId = UUID.randomUUID();
        List<LearningSubmission> submissions = List.of(new LearningSubmission());

        when(submissionRepository.findByUserId(userId)).thenReturn(submissions);
        when(mapper.toDto((LearningSubmission) any())).thenReturn(new LearningSubmissionDTO());

        List<LearningSubmissionDTO> result = service.getSubmissionsByUser(userId);

        assertEquals(1, result.size());
        verify(submissionRepository).findByUserId(userId);
    }

    @Test
    void getByUserAndTemplate_shouldReturnMappedDTO() {
        UUID userId = UUID.randomUUID();
        UUID templateId = UUID.randomUUID();
        LearningSubmission submission = new LearningSubmission();

        when(submissionRepository.findByUserIdAndTemplateId(userId, templateId)).thenReturn(Optional.of(submission));
        when(mapper.toDto(submission)).thenReturn(new LearningSubmissionDTO());

        LearningSubmissionDTO result = service.getByUserAndTemplate(userId, templateId);
        assertNotNull(result);
    }

    @Test
    void getByUserAndTemplate_shouldThrowIfNotFound() {
        UUID userId = UUID.randomUUID();
        UUID templateId = UUID.randomUUID();

        when(submissionRepository.findByUserIdAndTemplateId(userId, templateId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> service.getByUserAndTemplate(userId, templateId));
    }

    @Test
    void saveSubmission_shouldSetTemplateAndSave() {
        UUID templateId = UUID.randomUUID();
        LearningSubmissionDTO dto = new LearningSubmissionDTO();
        dto.setTemplateId(templateId);

        LearningMaterialTemplate template = new LearningMaterialTemplate();
        LearningSubmission entity = new LearningSubmission();
        entity.setSectionResponses(new ArrayList<>(List.of(new LearningSectionResponse())));

        when(mapper.toEntity(dto)).thenReturn(entity);
        when(templateRepository.findById(templateId)).thenReturn(Optional.of(template));
        when(submissionRepository.save(any())).thenReturn(entity);
        when(mapper.toDto((LearningSubmission) any())).thenReturn(dto);

        LearningSubmissionDTO result = service.saveSubmission(dto);

        assertEquals(dto, result);
        verify(submissionRepository).save(entity);
    }

    @Test
    void getSubmissionsByTemplate_shouldMapList() {
        UUID templateId = UUID.randomUUID();
        when(submissionRepository.findByTemplateId(templateId)).thenReturn(List.of(new LearningSubmission()));
        when(mapper.toDto((LearningSubmission) any())).thenReturn(new LearningSubmissionDTO());

        List<LearningSubmissionDTO> result = service.getSubmissionsByTemplate(templateId);
        assertEquals(1, result.size());
    }

    @Test
    void getSubmissionsByManagerId_shouldMapList() {
        UUID managerId = UUID.randomUUID();
        when(submissionRepository.findByManagerId(managerId)).thenReturn(List.of(new LearningSubmission()));
        when(mapper.toDto((LearningSubmission) any())).thenReturn(new LearningSubmissionDTO());

        List<LearningSubmissionDTO> result = service.getSubmissionsByManagerId(managerId);
        assertEquals(1, result.size());
    }

    @Test
    void getSubmission_shouldReturnDto() {
        UUID id = UUID.randomUUID();
        LearningSubmission submission = new LearningSubmission();

        when(submissionRepository.findById(id)).thenReturn(Optional.of(submission));
        when(mapper.toDto(submission)).thenReturn(new LearningSubmissionDTO());

        LearningSubmissionDTO result = service.getSubmission(id);
        assertNotNull(result);
    }

    @Test
    void deleteSubmission_shouldCallRepository() {
        UUID id = UUID.randomUUID();
        service.deleteSubmission(id);
        verify(submissionRepository).deleteById(id);
    }

    @Test
    void reviewSubmission_shouldApproveAndAddPoints() {
        UUID id = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        LearningMaterialTemplate template = new LearningMaterialTemplate();
        template.setPoints(10);

        LearningSubmission submission = new LearningSubmission();
        submission.setStatus(SubmissionStatus.PENDING);
        submission.setUserId(userId);
        submission.setTemplate(template);

        when(submissionRepository.findById(id)).thenReturn(Optional.of(submission));
        when(submissionRepository.save(submission)).thenReturn(submission);
        when(mapper.toDto(submission)).thenReturn(new LearningSubmissionDTO());

        LearningSubmissionDTO result = service.reviewSubmission(id, true);

        assertNotNull(result);
        assertEquals(SubmissionStatus.APPROVED, submission.getStatus());
        verify(scoreService).addPoints(userId, 10);
    }

    @Test
    void reviewSubmission_shouldThrowIfAlreadyReviewed() {
        UUID id = UUID.randomUUID();
        LearningSubmission submission = new LearningSubmission();
        submission.setStatus(SubmissionStatus.APPROVED);

        when(submissionRepository.findById(id)).thenReturn(Optional.of(submission));

        assertThrows(IllegalStateException.class, () -> service.reviewSubmission(id, false));
    }

    @Test
    void sendNotificationToManager_shouldSendKafkaMessage() {
        LearningSubmissionDTO dto = new LearningSubmissionDTO();
        dto.setUserId(UUID.randomUUID());
        dto.setManagerId(UUID.randomUUID());

        service.sendNotificationToManager(dto);

        verify(kafkaTemplate).send(eq("learning-material-submitted"), any(SendNotificationRequest.class));
    }

    @Test
    void sendNotificationToUser_shouldSendKafkaMessage() {
        LearningSubmissionDTO dto = new LearningSubmissionDTO();
        dto.setUserId(UUID.randomUUID());
        dto.setManagerId(UUID.randomUUID());

        service.sendNotificationToUser(dto);

        verify(kafkaTemplate).send(eq("learning-submission-reviewed"), any(SendNotificationRequest.class));
    }
}
