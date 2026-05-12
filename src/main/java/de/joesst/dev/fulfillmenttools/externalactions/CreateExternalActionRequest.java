package de.joesst.dev.fulfillmenttools.externalactions;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class CreateExternalActionRequest {

    private final String processRef;
    private final Map<String, Object> nameLocalized;
    private final List<String> groups;
    private final Map<String, Object> action;
    private final Map<String, Object> customAttributes;

    private CreateExternalActionRequest(Builder builder) {
        this.processRef = Objects.requireNonNull(builder.processRef, "processRef must not be null");
        this.nameLocalized = Objects.requireNonNull(builder.nameLocalized, "nameLocalized must not be null");
        this.groups = Objects.requireNonNull(builder.groups, "groups must not be null");
        this.action = Objects.requireNonNull(builder.action, "action must not be null");
        this.customAttributes = builder.customAttributes;
    }

    public String processRef() { return processRef; }
    public Map<String, Object> nameLocalized() { return nameLocalized; }
    public List<String> groups() { return groups; }
    public Map<String, Object> action() { return action; }
    public Map<String, Object> customAttributes() { return customAttributes; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {

        private String processRef;
        private Map<String, Object> nameLocalized;
        private List<String> groups;
        private Map<String, Object> action;
        private Map<String, Object> customAttributes;

        public Builder processRef(String processRef) { this.processRef = processRef; return this; }
        public Builder nameLocalized(Map<String, Object> nameLocalized) { this.nameLocalized = nameLocalized; return this; }
        public Builder groups(List<String> groups) { this.groups = groups; return this; }
        public Builder action(Map<String, Object> action) { this.action = action; return this; }
        public Builder customAttributes(Map<String, Object> customAttributes) { this.customAttributes = customAttributes; return this; }

        public CreateExternalActionRequest build() { return new CreateExternalActionRequest(this); }
    }
}
