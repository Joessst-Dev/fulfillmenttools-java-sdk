package de.joesst.dev.fulfillmenttools.externalactions;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Request object for replacing an existing external action (full PUT).
 *
 * <p>All required fields are validated at {@link Builder#build()} time. The builder enforces
 * that {@code version}, {@code nameLocalized}, {@code groups}, and {@code action} are present.
 *
 * <p>Maps to the {@code ExternalActionForReplacement} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable; safe for concurrent use after construction.
 *
 * <p>Usage example:
 * <pre>{@code
 * UpdateExternalActionRequest request = UpdateExternalActionRequest.builder()
 *     .version(2)
 *     .nameLocalized(Map.of("en_US", "Updated Name"))
 *     .groups(List.of("warehouse"))
 *     .action(new ExternalLinkActionDefinition(ExternalActionType.BLANK_LINK, "https://new.example.com"))
 *     .build();
 * }</pre>
 */
public final class UpdateExternalActionRequest {

    private final Integer version;
    private final Map<String, String> nameLocalized;
    private final List<String> groups;
    private final ExternalActionDefinition action;
    private final Map<String, Object> customAttributes;

    private UpdateExternalActionRequest(Builder builder) {
        this.version       = Objects.requireNonNull(builder.version,       "version must not be null");
        this.nameLocalized = Objects.requireNonNull(builder.nameLocalized, "nameLocalized must not be null");
        this.groups        = Objects.requireNonNull(builder.groups,        "groups must not be null");
        this.action        = Objects.requireNonNull(builder.action,        "action must not be null");
        this.customAttributes = builder.customAttributes;
    }

    /** @return the optimistic-locking version; never {@code null} */
    public Integer version() { return version; }

    /**
     * @return localized names; key is locale (e.g. {@code "en_US"}), value is translation;
     *         never {@code null}
     */
    public Map<String, String> nameLocalized() { return nameLocalized; }

    /** @return grouping tags; never {@code null} */
    public List<String> groups() { return groups; }

    /** @return the typed action definition; never {@code null} */
    public ExternalActionDefinition action() { return action; }

    /** @return free-form custom attributes, or {@code null} if not set */
    public Map<String, Object> customAttributes() { return customAttributes; }

    /** @return a new builder instance */
    public static Builder builder() { return new Builder(); }

    /**
     * Builder for {@link UpdateExternalActionRequest}.
     */
    public static final class Builder {

        private Integer version;
        private Map<String, String> nameLocalized;
        private List<String> groups;
        private ExternalActionDefinition action;
        private Map<String, Object> customAttributes;

        /**
         * @param version optimistic-locking version of the entity to update (required)
         * @return this builder
         */
        public Builder version(Integer version) {
            this.version = version;
            return this;
        }

        /**
         * @param nameLocalized localized names map; key is locale, value is translation (required)
         * @return this builder
         */
        public Builder nameLocalized(Map<String, String> nameLocalized) {
            this.nameLocalized = nameLocalized;
            return this;
        }

        /**
         * @param groups grouping tags (required)
         * @return this builder
         */
        public Builder groups(List<String> groups) {
            this.groups = groups;
            return this;
        }

        /**
         * @param action the typed action definition (required)
         * @return this builder
         */
        public Builder action(ExternalActionDefinition action) {
            this.action = action;
            return this;
        }

        /**
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
         * @return a new {@link UpdateExternalActionRequest}
         * @throws NullPointerException if any required field is {@code null}
         */
        public UpdateExternalActionRequest build() {
            return new UpdateExternalActionRequest(this);
        }
    }
}
