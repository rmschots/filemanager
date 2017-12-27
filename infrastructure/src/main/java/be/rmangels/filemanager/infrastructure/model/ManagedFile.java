package be.rmangels.filemanager.infrastructure.model;

import be.rmangels.filemanager.infrastructure.util.NestedBuilder;
import lombok.AccessLevel;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.Optional;

@Getter
public abstract class ManagedFile {

    protected String filename;
    protected OffsetDateTime created;
    protected OffsetDateTime modified;
    @Getter(AccessLevel.NONE)
    protected Long size;

    ManagedFile() {
    }

    public abstract boolean isDirectory();

    public Optional<Long> getSize() {
        return Optional.ofNullable(size);
    }

    public abstract static class Builder<B extends Builder<B, V>, V extends ManagedFile> extends NestedBuilder<V> {

        @SuppressWarnings("unchecked")
        public B withFilename(String filename) {
            instance().filename = filename;
            return (B) this;
        }

        @SuppressWarnings("unchecked")
        public B withCreated(OffsetDateTime created) {
            instance().created = created;
            return (B) this;
        }

        @SuppressWarnings("unchecked")
        public B withModified(OffsetDateTime modified) {
            instance().modified = modified;
            return (B) this;
        }

        @SuppressWarnings("unchecked")
        public B withSize(Long size) {
            instance().size = size;
            return (B) this;
        }
    }
}
