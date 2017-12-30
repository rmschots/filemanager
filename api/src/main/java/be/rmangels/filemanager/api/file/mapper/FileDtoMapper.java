package be.rmangels.filemanager.api.file.mapper;

import be.rmangels.filemanager.api.file.dto.FileDto;
import be.rmangels.filemanager.infrastructure.model.ManagedFile;

import javax.inject.Named;

@Named
public class FileDtoMapper {

    public FileDto mapToDataFileDto(ManagedFile dataFile) {
        return new FileDto()
                .setFilename(dataFile.getFilename())
                .setCreated(dataFile.getCreated())
                .setModified(dataFile.getModified())
                .setDirectory(dataFile.isDirectory())
                .setSize(dataFile.getSize().orElse(null));
    }
}
