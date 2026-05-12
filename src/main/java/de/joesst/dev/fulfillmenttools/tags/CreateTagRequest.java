package de.joesst.dev.fulfillmenttools.tags;

import java.util.List;
import java.util.Objects;

public final class CreateTagRequest {

    private final String id;
    private final List<String> allowedValues;

    private CreateTagRequest(Builder builder) {
        this.id = Objects.requireNonNull(builder.id, "id must not be null");
        this.allowedValues = Objects.requireNonNull(builder.allowedValues, "allowedValues must not be null");
    }

    public String id() { return id; }
    public List<String> allowedValues() { return allowedValues; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private String id;
        private List<String> allowedValues;

        public Builder id(String id) { this.id = id; return this; }
        public Builder allowedValues(List<String> allowedValues) { this.allowedValues = allowedValues; return this; }

        public CreateTagRequest build() { return new CreateTagRequest(this); }
    }
}
