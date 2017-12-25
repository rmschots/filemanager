package be.rmangels.filemanager.api;

import be.rmangels.filemanager.api.dto.DataFileDto;
import be.rmangels.filemanager.api.mapper.DataFileDtoMapper;
import be.rmangels.filemanager.infrastructure.util.FileManagerException;
import be.rmangels.filemanager.service.DataFileService;
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

import static com.google.common.collect.Lists.newArrayList;

@RestController
@RequestMapping("/api/datafiles")
public class DataFileResource {

    private static final Logger LOGGER = Logger.getLogger(DataFileResource.class);

    private static final String UPLOADED_FOLDER = "uploads/";
    @Inject
    private DataFileService dataFileService;

    @Inject
    private DataFileDtoMapper dataFileDtoMapper;

    @GetMapping
    public List<DataFileDto> getAllDataFiles() {
        return dataFileService.findAll().stream()
                .map(dataFileDtoMapper::mapToDataFileDto)
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
                        Path parentDir = Paths.get(UPLOADED_FOLDER);
                        if (!parentDir.toFile().exists()) {
                            Files.createDirectories(parentDir);
                        }
                        Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
                        Files.write(path, bytes);
                    } catch (IOException e) {
                        LOGGER.error("IOException when uploading file", e);
                        throw FileManagerException.of("IOException when uploading file", e);
                    }
                });
    }
}
