package com.blackbox.service;


import com.blackbox.model.FileEntity;
import com.blackbox.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.UUID;

@Service
public class FileService {

    private final Path root = Paths.get("uploads");

    @Autowired
    private FileRepository repository;

    public FileService() throws IOException {
        Files.createDirectories(root);
    }

    public FileEntity save(MultipartFile file) throws IOException {
        if (file.getOriginalFilename().endsWith(".exe")) {
            throw new RuntimeException("Executable files are not allowed.");
        }

        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path path = root.resolve(filename);
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

        FileEntity entity = new FileEntity();
        entity.setOriginalName(file.getOriginalFilename());
        entity.setFileType(file.getContentType());
        entity.setPath(path.toString());
        entity.setSize(file.getSize());

        return repository.save(entity);
    }

    public Resource load(Long id) throws Exception {
        FileEntity fileEntity = repository.findById(id).orElseThrow();
        Path filePath = Paths.get(fileEntity.getPath());
        return new UrlResource(filePath.toUri());
    }

    public List<FileEntity> listFiles() {
        return repository.findAll();
    }
}
