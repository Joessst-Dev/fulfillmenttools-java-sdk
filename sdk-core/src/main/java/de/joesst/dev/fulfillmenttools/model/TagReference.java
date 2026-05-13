package de.joesst.dev.fulfillmenttools.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.joesst.dev.fulfillmenttools.id.TagId;

/**
 * A reference to a tag entity, carrying the tag's identifier and value.
 *
 * <p>Used wherever the fulfillmenttools API attaches tag references to a resource
 * (e.g. facilities, listings). Maps to the {@code TagReference} schema in the
 * fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param id    the unique identifier of the tag
 * @param value the value or label of the tag
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record TagReference(TagId id, String value) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private TagId id;
        private String value;

        public Builder id(TagId id) { this.id = id; return this; }
        public Builder value(String value) { this.value = value; return this; }

        public TagReference build() {
            return new TagReference(id, value);
        }
    }
}
