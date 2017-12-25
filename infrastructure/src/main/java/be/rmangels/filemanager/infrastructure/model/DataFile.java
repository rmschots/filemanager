package be.rmangels.filemanager.infrastructure.model;

import be.rmangels.filemanager.infrastructure.util.NestedBuilder;
import lombok.Getter;

@Getter
public class DataFile {

    private String filename;

    private DataFile() {
    }

    public static class Builder extends NestedBuilder<DataFile> {

        public static Builder of(String filename) {
            return new Builder().withFilename(filename);
        }

        private Builder() {
        }

        @Override
        protected DataFile createInstance() {
            return new DataFile();
        }

        public Builder withFilename(String filename) {
            instance().filename = filename;
            return this;
        }
    }
}
