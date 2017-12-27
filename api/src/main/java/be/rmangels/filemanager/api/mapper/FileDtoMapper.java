package be.rmangels.filemanager.api.mapper;

import be.rmangels.filemanager.api.dto.FileDto;
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
