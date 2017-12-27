package be.rmangels.filemanager.infrastructure.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;

import javax.inject.Named;

@Named
@Getter
public class ApplicationProperties {

    @Value("${storage.folder}")
    private String storageFolder;
}
