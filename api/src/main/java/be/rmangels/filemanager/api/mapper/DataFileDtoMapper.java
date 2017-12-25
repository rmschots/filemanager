package be.rmangels.filemanager.api.mapper;

import be.rmangels.filemanager.api.dto.DataFileDto;
import be.rmangels.filemanager.infrastructure.model.DataFile;

import javax.inject.Named;

@Named
public class DataFileDtoMapper {

    public DataFileDto mapToDataFileDto(DataFile dataFile) {
        return new DataFileDto()
                .setFilename(dataFile.getFilename());
    }
}
