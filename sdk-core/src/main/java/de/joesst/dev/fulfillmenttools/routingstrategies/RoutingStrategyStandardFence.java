package de.joesst.dev.fulfillmenttools.routingstrategies;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * A built-in (standard) routing strategy fence that filters candidate facilities based on a
 * predefined implementation.
 *
 * <p>Discriminator value: {@code "StandardFence"}
 *
 * @param type            discriminator field, always {@code "StandardFence"} (required)
 * @param active          whether this fence is active (required)
 * @param implementation  the fence implementation to use (required)
 * @param activeMode      the mode in which the fence is active
 * @param supportedModes  the modes supported by this fence
 * @param name            optional display name
 * @param description     optional description
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record RoutingStrategyStandardFence(
        String type,
        Boolean active,
        FenceImplementation implementation,
        FenceMode activeMode,
        List<FenceMode> supportedModes,
        String name,
        String description
) implements RoutingStrategyFence {

    /**
     * Returns a builder for constructing a {@code RoutingStrategyStandardFence}.
     *
     * @return a new {@link Builder}.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link RoutingStrategyStandardFence}.
     */
    public static final class Builder {

        private String type;
        private Boolean active;
        private FenceImplementation implementation;
        private FenceMode activeMode;
        private List<FenceMode> supportedModes;
        private String name;
        private String description;

        private Builder() {}

        /**
         * Sets the discriminator field.
         * @param type the type value, always {@code "StandardFence"}
         * @return this builder
         */
        public Builder type(String type) {
            this.type = type;
            return this;
        }

        /**
         * Sets whether this fence is active.
         * @param active the active flag
         * @return this builder
         */
        public Builder active(Boolean active) {
            this.active = active;
            return this;
        }

        /**
         * Sets the fence implementation to use.
         * @param implementation the fence implementation
         * @return this builder
         */
        public Builder implementation(FenceImplementation implementation) {
            this.implementation = implementation;
            return this;
        }

        /**
         * Sets the mode in which the fence is active.
         * @param activeMode the active mode
         * @return this builder
         */
        public Builder activeMode(FenceMode activeMode) {
            this.activeMode = activeMode;
            return this;
        }

        /**
         * Sets the modes supported by this fence.
         * @param supportedModes the supported fence modes
         * @return this builder
         */
        public Builder supportedModes(List<FenceMode> supportedModes) {
            this.supportedModes = supportedModes;
            return this;
        }

        /**
         * Sets the optional display name.
         * @param name the display name
         * @return this builder
         */
        public Builder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * Sets the optional description.
         * @param description the description
         * @return this builder
         */
        public Builder description(String description) {
            this.description = description;
            return this;
        }

        /**
         * Builds a {@link RoutingStrategyStandardFence}.
         *
         * @return a new instance.
         */
        public RoutingStrategyStandardFence build() {
            return new RoutingStrategyStandardFence(
                    type, active, implementation, activeMode, supportedModes, name, description);
        }
    }
}
