package de.joesst.dev.fulfillmenttools.tags;

import de.joesst.dev.fulfillmenttools.id.TagId;

import java.time.Instant;
import java.util.List;

/**
 * Represents a tag used to organize and categorize orders and items.
 *
 * <p>Maps to the {@code Tag} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param id             Server-assigned unique identifier for this tag.
 * @param version        Optimistic-locking version counter.
 * @param created        Timestamp when this tag was created.
 * @param lastModified   Timestamp when this tag was last modified.
 * @param allowedValues  List of valid values that can be assigned to this tag.
 */
public record Tag(
        TagId id,
        Integer version,
        Instant created,
        Instant lastModified,
        List<String> allowedValues
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private TagId id;
        private Integer version;
        private Instant created;
        private Instant lastModified;
        private List<String> allowedValues;

        public Builder id(TagId id) {
            this.id = id;
            return this;
        }

        public Builder version(Integer version) {
            this.version = version;
            return this;
        }

        public Builder created(Instant created) {
            this.created = created;
            return this;
        }

        public Builder lastModified(Instant lastModified) {
            this.lastModified = lastModified;
            return this;
        }

        public Builder allowedValues(List<String> allowedValues) {
            this.allowedValues = allowedValues;
            return this;
        }

        public Tag build() {
            return new Tag(id, version, created, lastModified, allowedValues);
        }
    }
}
