package de.joesst.dev.fulfillmenttools.externalactions;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Request object for creating a new external action.
 *
 * <p>All required fields are validated at {@link Builder#build()} time. The builder enforces
 * that {@code processRef}, {@code nameLocalized}, {@code groups}, and {@code action} are present.
 *
 * <p>Maps to the {@code ExternalActionForCreation} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable; safe for concurrent use after construction.
 *
 * <p>Usage example:
 * <pre>{@code
 * CreateExternalActionRequest request = CreateExternalActionRequest.builder()
 *     .processRef("proc-1")
 *     .nameLocalized(Map.of("en_US", "Notify Warehouse"))
 *     .groups(List.of("warehouse"))
 *     .action(new ExternalLinkActionDefinition(ExternalActionType.BLANK_LINK, "https://example.com"))
 *     .build();
 * }</pre>
 */
public final class CreateExternalActionRequest {

    private final String processRef;
    private final Map<String, String> nameLocalized;
    private final List<String> groups;
    private final ExternalActionDefinition action;
    private final Map<String, Object> customAttributes;

    private CreateExternalActionRequest(Builder builder) {
        this.processRef    = Objects.requireNonNull(builder.processRef,    "processRef must not be null");
        this.nameLocalized = Objects.requireNonNull(builder.nameLocalized, "nameLocalized must not be null");
        this.groups        = Objects.requireNonNull(builder.groups,        "groups must not be null");
        this.action        = Objects.requireNonNull(builder.action,        "action must not be null");
        this.customAttributes = builder.customAttributes;
    }

    /**
     * Returns the process reference.
     * @return the process reference; never {@code null}
     */
    public String processRef() { return processRef; }

    /**
     * Returns the localized names map.
     * @return localized names; key is locale (e.g. {@code "en_US"}), value is translation;
     *         never {@code null}
     */
    public Map<String, String> nameLocalized() { return nameLocalized; }

    /**
     * Returns the grouping tags.
     * @return grouping tags; never {@code null}
     */
    public List<String> groups() { return groups; }

    /**
     * Returns the typed action definition.
     * @return the typed action definition; never {@code null}
     */
    public ExternalActionDefinition action() { return action; }

    /**
     * Returns the free-form custom attributes.
     * @return free-form custom attributes, or {@code null} if not set
     */
    public Map<String, Object> customAttributes() { return customAttributes; }

    /**
     * Creates a new builder instance.
     * @return a new builder instance
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Builder for {@link CreateExternalActionRequest}.
     */
    public static final class Builder {

        /** Creates a new Builder instance. */
        public Builder() {}

        private String processRef;
        private Map<String, String> nameLocalized;
        private List<String> groups;
        private ExternalActionDefinition action;
        private Map<String, Object> customAttributes;

        /**
         * Sets the ID of the global process.
         * @param processRef ID of the global process (required)
         * @return this builder
         */
        public Builder processRef(String processRef) {
            this.processRef = processRef;
            return this;
        }

        /**
         * Sets the localized names map.
         * @param nameLocalized localized names map; key is locale, value is translation (required)
         * @return this builder
         */
        public Builder nameLocalized(Map<String, String> nameLocalized) {
            this.nameLocalized = nameLocalized;
            return this;
        }

        /**
         * Sets the grouping tags.
         * @param groups grouping tags (required)
         * @return this builder
         */
        public Builder groups(List<String> groups) {
            this.groups = groups;
            return this;
        }

        /**
         * Sets the typed action definition.
         * @param action the typed action definition (required)
         * @return this builder
         */
        public Builder action(ExternalActionDefinition action) {
            this.action = action;
            return this;
        }

        /**
         * Sets the free-form custom attributes.
         * @param customAttributes free-form custom attributes (optional)
         * @return this builder
         */
        public Builder customAttributes(Map<String, Object> customAttributes) {
            this.customAttributes = customAttributes;
            return this;
        }

        /**
         * Builds the request, validating that all required fields are set.
         *
         * @return a new {@link CreateExternalActionRequest}
         * @throws NullPointerException if any required field is {@code null}
         */
        public CreateExternalActionRequest build() {
            return new CreateExternalActionRequest(this);
        }
    }
}
