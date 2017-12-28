package be.rmangels.filemanager.api;

import be.rmangels.filemanager.api.dto.FileDto;
import be.rmangels.filemanager.api.mapper.FileDtoMapper;
import be.rmangels.filemanager.infrastructure.config.ApplicationProperties;
import be.rmangels.filemanager.infrastructure.model.FileContent;
import be.rmangels.filemanager.service.FileService;
import org.apache.log4j.Logger;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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

    @GetMapping("/download/{directoryStructure}/{filename:.+}")
    public ResponseEntity<ByteArrayResource> download(@PathVariable List<String> directoryStructure, @PathVariable String filename) throws IOException {
        directoryStructure.add(filename);
        Path path = Paths.get(applicationProperties.getStorageFolder(), directoryStructure.toArray(new String[directoryStructure.size()]));

        byte[] data = Files.readAllBytes(path);
        ByteArrayResource resource = new ByteArrayResource(data);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + path.getFileName().toString())
                .contentLength(data.length)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
    }

    @PostMapping("/upload/{directoryStructure}")
    public ResponseEntity uploadFile(@PathVariable List<String> directoryStructure, @RequestParam("file") MultipartFile uploadfile) throws IOException {
        return processUpload(directoryStructure, uploadfile);
    }

    @PostMapping("/upload")
    public ResponseEntity uploadFileToRoot(@RequestParam("file") MultipartFile uploadfile) throws IOException {
        return processUpload(newArrayList(), uploadfile);
    }

    private ResponseEntity processUpload(@PathVariable List<String> directoryStructure, @RequestParam("file") MultipartFile uploadfile) throws IOException {
        if (uploadfile.isEmpty()) {
            return ResponseEntity.badRequest().body("please select a file!");
        }

        FileContent fileContent = FileContent.Builder.of()
                .withFilename(uploadfile.getOriginalFilename())
                .withData(uploadfile.getInputStream())
                .build();

        fileService.saveFile(directoryStructure, fileContent);
        return ResponseEntity.ok(String.format("Successfully uploaded - %s", uploadfile.getOriginalFilename()));
    }


}
