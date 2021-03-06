package be.rmangels.filemanager.infrastructure.model;

import lombok.Getter;

import java.time.OffsetDateTime;

@Getter
public class DataFile extends ManagedFile {


    private DataFile() {
        super();
    }

    @Override
    public boolean isDirectory() {
        return false;
    }

    public static class Builder extends ManagedFile.Builder<Builder, DataFile> {

        public static Builder of() {
            return new Builder();
        }

        private Builder() {
        }

        @Override
        protected DataFile createInstance() {
            return new DataFile();
        }
    }
}
