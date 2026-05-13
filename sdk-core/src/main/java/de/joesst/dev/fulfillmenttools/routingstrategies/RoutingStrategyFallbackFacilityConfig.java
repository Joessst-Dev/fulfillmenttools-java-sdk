package de.joesst.dev.fulfillmenttools.routingstrategies;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.joesst.dev.fulfillmenttools.id.FacilityId;

import java.util.List;

/**
 * Configuration for routing a not-routable order to a pre-configured fallback facility after a
 * specified wait time.
 *
 * @param facilityRefs     list of facility IDs to use as fallback (required, 1 item)
 * @param active           whether fallback facility routing is active (required, default false)
 * @param fallbackAfterTime ISO 8601 duration after which routing to the fallback facility is
 *                          triggered (required, default {@code PT0H})
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record RoutingStrategyFallbackFacilityConfig(
        List<FacilityId> facilityRefs,
        Boolean active,
        String fallbackAfterTime
) {

    /**
     * Returns a builder for constructing a {@code RoutingStrategyFallbackFacilityConfig}.
     *
     * @return a new {@link Builder}.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link RoutingStrategyFallbackFacilityConfig}.
     */
    public static final class Builder {

        private List<FacilityId> facilityRefs;
        private Boolean active;
        private String fallbackAfterTime;

        private Builder() {}

        /**
         * Sets the list of facility IDs to use as fallback.
         * @param facilityRefs facility ID references (1 item)
         * @return this builder
         */
        public Builder facilityRefs(List<FacilityId> facilityRefs) {
            this.facilityRefs = facilityRefs;
            return this;
        }

        /**
         * Sets whether fallback facility routing is active.
         * @param active the active flag (default false)
         * @return this builder
         */
        public Builder active(Boolean active) {
            this.active = active;
            return this;
        }

        /**
         * Sets the ISO 8601 duration after which routing to the fallback facility is triggered.
         * @param fallbackAfterTime the fallback duration (default {@code PT0H})
         * @return this builder
         */
        public Builder fallbackAfterTime(String fallbackAfterTime) {
            this.fallbackAfterTime = fallbackAfterTime;
            return this;
        }

        /**
         * Builds a {@link RoutingStrategyFallbackFacilityConfig}.
         *
         * @return a new instance.
         */
        public RoutingStrategyFallbackFacilityConfig build() {
            return new RoutingStrategyFallbackFacilityConfig(facilityRefs, active, fallbackAfterTime);
        }
    }
}
