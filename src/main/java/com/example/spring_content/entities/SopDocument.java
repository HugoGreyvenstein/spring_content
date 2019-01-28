package com.example.spring_content.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.content.commons.annotations.ContentId;
import org.springframework.content.commons.annotations.ContentLength;
import org.springframework.content.commons.annotations.MimeType;
import org.springframework.versions.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class SopDocument
{
    private @Id
    @GeneratedValue
    Long id;
    private String title;
    private String[] authors, keywords;

    // Spring content managed attribute
    @ContentId
    private String contentId;
    @ContentLength
    private Long contentLength;

    @Version
    private Long vstamp;
    @VersionNumber
    private UUID version;
    @VersionLabel
    private String label;

    @MimeType
    private String mimeType;

    @LockOwner
    private String lockOwner;

    @AncestorId
    private Long ancestorId;

    @AncestorRootId
    private Long ancestralRootId;

    @SuccessorId
    private Long successorId;

}
