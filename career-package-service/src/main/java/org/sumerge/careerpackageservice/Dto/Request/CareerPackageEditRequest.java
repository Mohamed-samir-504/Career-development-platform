package org.sumerge.careerpackageservice.Dto.Request;

import lombok.Data;
import org.sumerge.careerpackageservice.Dto.SectionFieldTemplateDTO;
import org.sumerge.careerpackageservice.Dto.SectionTemplateDTO;

import java.util.List;
import java.util.UUID;

@Data
public class CareerPackageEditRequest {

    private String title;
    private String description;

    private List<SectionTemplateDTO> updatedSections;
    private List<SectionTemplateDTO> newSections;
    private List<UUID> deletedSectionIds;

    private List<SectionFieldTemplateDTO>updatedFields;
    private List<SectionFieldTemplateDTO> newFields;
    private List<UUID> deletedFieldIds;

}
