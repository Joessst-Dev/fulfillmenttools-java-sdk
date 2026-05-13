package de.joesst.dev.fulfillmenttools.routingstrategies;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;

/**
 * Defines a time frame during which the associated entity (node, condition) is active.
 *
 * @param activeFrom  optional date before which the entity is not yet active
 * @param activeUntil optional date after which the entity becomes inactive
 * @param recurrence  whether the time frame recurs (required)
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ActivationTimeFrame(
        Instant activeFrom,
        Instant activeUntil,
        ActivationTimeFrameRecurrenceType recurrence
) {

    /**
     * Returns a builder for constructing an {@code ActivationTimeFrame}.
     *
     * @return a new {@link Builder}.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link ActivationTimeFrame}.
     */
    public static final class Builder {

        private Instant activeFrom;
        private Instant activeUntil;
        private ActivationTimeFrameRecurrenceType recurrence;

        private Builder() {}

        /**
         * Sets the date before which the entity is not yet active.
         * @param activeFrom the start of the activation window
         * @return this builder
         */
        public Builder activeFrom(Instant activeFrom) {
            this.activeFrom = activeFrom;
            return this;
        }

        /**
         * Sets the date after which the entity becomes inactive.
         * @param activeUntil the end of the activation window
         * @return this builder
         */
        public Builder activeUntil(Instant activeUntil) {
            this.activeUntil = activeUntil;
            return this;
        }

        /**
         * Sets whether the time frame recurs.
         * @param recurrence the recurrence type
         * @return this builder
         */
        public Builder recurrence(ActivationTimeFrameRecurrenceType recurrence) {
            this.recurrence = recurrence;
            return this;
        }

        /**
         * Builds an {@link ActivationTimeFrame}.
         *
         * @return a new instance.
         */
        public ActivationTimeFrame build() {
            return new ActivationTimeFrame(activeFrom, activeUntil, recurrence);
        }
    }
}
