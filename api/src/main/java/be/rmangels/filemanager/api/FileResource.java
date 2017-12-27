package be.rmangels.filemanager.api;

import be.rmangels.filemanager.api.dto.FileDto;
import be.rmangels.filemanager.api.mapper.FileDtoMapper;
import be.rmangels.filemanager.infrastructure.config.ApplicationProperties;
import be.rmangels.filemanager.infrastructure.util.FileManagerException;
import be.rmangels.filemanager.service.FileService;
import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static be.rmangels.filemanager.api.FileResource.FILE_RESOURCE_URL;
import static be.rmangels.filemanager.api.dto.FileDto.COMPARE_BY_DIRECTORY_AND_NAME;
import static com.google.common.collect.Lists.newArrayList;

@RestController
@RequestMapping(FILE_RESOURCE_URL)
public class FileResource {

    private static final Logger LOGGER = Logger.getLogger(FileResource.class);
    static final String FILE_RESOURCE_URL = "/api/files";

    @Inject
    private ApplicationProperties applicationProperties;

    @Inject
    private FileService fileService;

    @Inject
    private FileDtoMapper fileDtoMapper;

    @GetMapping
    public List<FileDto> getAllFiles() {
        return fileService.findAllInDirectory(newArrayList()).stream()
                .map(fileDtoMapper::mapToDataFileDto)
                .sorted(COMPARE_BY_DIRECTORY_AND_NAME)
                .collect(Collectors.toList());
    }

    @GetMapping("{directoryStructure}")
    public List<FileDto> getFilesInDirectory(@PathVariable List<String> directoryStructure) {
        return fileService.findAllInDirectory(directoryStructure).stream()
                .map(fileDtoMapper::mapToDataFileDto)
                .sorted(COMPARE_BY_DIRECTORY_AND_NAME)
                .collect(Collectors.toList());
    }

    @PostMapping("/upload")
    public ResponseEntity uploadFile(@RequestParam("file") MultipartFile uploadfile) {
        if (uploadfile.isEmpty()) {
            return ResponseEntity.badRequest().body("please select a file!");
        }
        try {
            saveUploadedFiles(newArrayList(uploadfile));
        } catch (FileManagerException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok(String.format("Successfully uploaded - %s", uploadfile.getOriginalFilename()));
    }

    private void saveUploadedFiles(List<MultipartFile> files) {
        files.stream()
                .filter(file -> !file.isEmpty())
                .forEach(file -> {
                    byte[] bytes;
                    try {
                        bytes = file.getBytes();
                        Path parentDir = Paths.get(applicationProperties.getStorageFolder());
                        if (!parentDir.toFile().exists()) {
                            Files.createDirectories(parentDir);
                        }
                        Path path = Paths.get(applicationProperties.getStorageFolder() + file.getOriginalFilename());
                        Files.write(path, bytes);
                    } catch (IOException e) {
                        LOGGER.error("IOException when uploading file", e);
                        throw FileManagerException.of("IOException when uploading file", e);
                    }
                });
    }
}
