package be.rmangels.filemanager.api.file.dto;

import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.OffsetDateTime;
import java.util.Comparator;

@Setter
@Accessors(chain = true)
public class FileDto {

    public static final Comparator<FileDto> COMPARE_BY_DIRECTORY_AND_NAME = (o1, o2) -> {
        int directoryCompare = Boolean.compare(o2.isDirectory, o1.isDirectory);
        if (directoryCompare != 0) {
            return directoryCompare;
        }
        return o1.filename.compareToIgnoreCase(o2.filename);
    };

    public String filename;
    public OffsetDateTime created;
    public OffsetDateTime modified;

    public boolean isDirectory;
    public Long size;

    public FileDto() {
    }
}
