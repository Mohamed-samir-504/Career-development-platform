package org.sumerge.learningservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.sumerge.learningservice.enums.ContentType;

import java.util.UUID;

@Entity
@Table(name = "blog_wiki_entries")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlogWiki {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String attachmentId; // reference to MongoDB file

    @Enumerated(EnumType.STRING)
    private ContentType type;
}