package be.rmangels.filemanager.infrastructure.files;

import be.rmangels.filemanager.config.ApplicationProperties;
import be.rmangels.filemanager.infrastructure.model.DataFile;
import be.rmangels.filemanager.infrastructure.model.DirectoryFile;
import be.rmangels.filemanager.infrastructure.model.FileContent;
import be.rmangels.filemanager.infrastructure.model.ManagedFile;
import be.rmangels.filemanager.infrastructure.util.FileManagerException;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.time.ZoneOffset.UTC;
import static java.util.stream.Collectors.toList;

@Named
public class FileExplorer {

    @Inject
    private ApplicationProperties applicationProperties;

    public List<ManagedFile> findFilesInDirectory(List<String> directoryStructure) {
        Path currentPath = getPathForDirectoryStructure(directoryStructure);
        try (Stream<Path> paths = Files.list(currentPath)) {
            return paths.map(this::mapPathToManagedFile)
                    .collect(toList());
        } catch (IOException e) {
            throw FileManagerException.of("Error reading files from " + directoryStructure.stream().collect(Collectors.joining("/")), e);
        }
    }

    private Path getPathForDirectoryStructure(List<String> directoryStructure) {
        return Paths.get(
                applicationProperties.getStorageFolder(),
                directoryStructure.toArray(new String[directoryStructure.size()])
        );
    }

    public void saveFile(List<String> directoryStructure, FileContent fileContent) {
        Path directory = getPathForDirectoryStructure(directoryStructure);
        Path destinationPath = directory.resolve(fileContent.getFilename());
        try {
            Files.copy(fileContent.getData(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw FileManagerException.of("Could not write file " + destinationPath.getFileName().toString(), e);
        }

    }

    private ManagedFile mapPathToManagedFile(Path file) {
        BasicFileAttributes attr;
        try {
            attr = Files.readAttributes(file, BasicFileAttributes.class);
        } catch (IOException e) {
            throw FileManagerException.of("could not read file properties of " + file.getFileName().toString(), e);
        }
        if (attr.isDirectory()) {
            return DirectoryFile.Builder.of().withSize(getFolderSize(file))
                    .withCreated(attr.creationTime().toInstant().atOffset(UTC))
                    .withModified(attr.lastModifiedTime().toInstant().atOffset(UTC))
                    .withFilename(file.getFileName().toString())
                    .build();
        } else {
            return DataFile.Builder.of().withSize(attr.size())
                    .withCreated(attr.creationTime().toInstant().atOffset(UTC))
                    .withModified(attr.lastModifiedTime().toInstant().atOffset(UTC))
                    .withFilename(file.getFileName().toString())
                    .build();
        }
    }

    private long getFolderSize(Path folder) {
        try (Stream<Path> subFiles = Files.walk(folder)) {
            return subFiles.filter(p -> p.toFile().isFile())
                    .mapToLong(p -> p.toFile().length())
                    .sum();
        } catch (IOException e) {
            throw FileManagerException.of("Error determining size of folder " + folder.getFileName().toString(), e);
        }
    }
}
