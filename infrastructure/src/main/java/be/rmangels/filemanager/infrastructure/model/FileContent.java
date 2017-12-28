package be.rmangels.filemanager.infrastructure.model;

import be.rmangels.filemanager.infrastructure.util.NestedBuilder;
import lombok.Getter;

import java.io.InputStream;

@Getter
public class FileContent {

    private InputStream data;
    private String filename;

    private FileContent() {
    }

    public static class Builder extends NestedBuilder<FileContent> {

        public static Builder of() {
            return new Builder();
        }

        private Builder() {
        }

        @Override
        protected FileContent createInstance() {
            return new FileContent();
        }

        public Builder withFilename(String filename) {
            instance().filename = filename;
            return this;
        }

        public Builder withData(InputStream data) {
            instance().data = data;
            return this;
        }
    }
}
