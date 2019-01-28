package com.example.spring_content.contentstore;

import com.example.spring_content.entities.SopDocument;
import org.springframework.content.commons.repository.ContentStore;
import org.springframework.stereotype.Component;

@Component
public interface SopDocumentContent extends ContentStore<SopDocument, String>
{
}
