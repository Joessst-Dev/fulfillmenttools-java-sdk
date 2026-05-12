package de.joesst.dev.fulfillmenttools.externalactions;

import java.util.Objects;

public final class CreateExternalActionRequest {

    private final String name;
    private final String actionType;

    private CreateExternalActionRequest(Builder builder) {
        this.name = Objects.requireNonNull(builder.name, "name must not be null");
        this.actionType = Objects.requireNonNull(builder.actionType, "actionType must not be null");
    }

    public String name() { return name; }
    public String actionType() { return actionType; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {

        private String name;
        private String actionType;

        public Builder name(String name) { this.name = name; return this; }
        public Builder actionType(String actionType) { this.actionType = actionType; return this; }

        public CreateExternalActionRequest build() { return new CreateExternalActionRequest(this); }
    }
}
