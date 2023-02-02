package com.fmss.hr.services;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class FileStorageService {

    @Value("${resourcesPath}")
    private String resourcesPath;

    //private final Path filePath = Paths.get(System.getProperty("user.dir"),"/var/lib/uploads"); //TODO docker locali

    public String save(MultipartFile file, String candidateName) {
        try {
            String filename = candidateName + "-CV.pdf";
            Files.copy(file.getInputStream(), Paths.get(System.getProperty("user.dir"), resourcesPath + "uploads/").resolve(filename), StandardCopyOption.REPLACE_EXISTING); //bu ikisi id falan alıyo

            return resourcesPath + "uploads/" + filename;
        }
        catch (Exception e) {
            throw new RuntimeException("Could not store the file " + e.getMessage() );
        }
    }

    public Resource getFileByName(String fileName) {
        return getFileByPath(String.valueOf(Paths.get(System.getProperty("user.dir"), resourcesPath + "uploads/").resolve(fileName)));
    }

    public void deleteAll() {
        FileSystemUtils.deleteRecursively(Paths.get(System.getProperty("user.dir"), resourcesPath + "uploads/").toFile());
    }

    public List<String> getAllFiles() {
        return Stream.of(Objects.requireNonNull(new File(String.valueOf(Paths.get(System.getProperty("user.dir"), resourcesPath + "uploads/"))).listFiles()))
                .filter(file -> !file.isDirectory())
                .map(File::getName)
                .collect(Collectors.toList());
    }

    public Resource getFileByPath(String path) {
        try {
            Path file = Paths.get(path);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new RuntimeException("Could not read file");
            }
        }
        catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }
}
