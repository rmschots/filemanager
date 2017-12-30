package be.rmangels.filemanager.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;

import javax.inject.Named;

@Named
@Getter
public class ApplicationProperties {

    @Value("${storage.folder}")
    private String storageFolder;

    @Value("${google.client.clientId}")
    private String clientId;

    @Value("${login.success.redirect.url}")
    private String loginSuccessRedirectUrl;
}
