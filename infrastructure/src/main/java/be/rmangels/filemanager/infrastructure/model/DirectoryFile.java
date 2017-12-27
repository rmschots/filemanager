package be.rmangels.filemanager.infrastructure.model;

import lombok.Getter;

@Getter
public class DirectoryFile extends ManagedFile {

    private DirectoryFile() {
        super();
    }

    @Override
    public boolean isDirectory() {
        return true;
    }

    public static class Builder extends ManagedFile.Builder<Builder, DirectoryFile> {

        public static Builder of() {
            return new Builder();
        }

        private Builder() {
        }

        @Override
        protected DirectoryFile createInstance() {
            return new DirectoryFile();
        }
    }
}
