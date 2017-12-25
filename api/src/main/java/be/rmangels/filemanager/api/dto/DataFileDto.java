package be.rmangels.filemanager.api.dto;

import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Accessors(chain = true)
public class DataFileDto {
    public String filename;

    public DataFileDto() {
    }
}
