package com.example.spring_content.repository;

import com.example.spring_content.entities.SopDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.versions.LockingAndVersioningRepository;

public interface SopDocumentRepository extends JpaRepository<SopDocument, Long>, LockingAndVersioningRepository<SopDocument, Long>
{
}
