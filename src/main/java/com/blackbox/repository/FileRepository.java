package com.blackbox.repository;

import com.blackbox.model.FileEntity; // ✅ Corrected package
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Long> {
}
