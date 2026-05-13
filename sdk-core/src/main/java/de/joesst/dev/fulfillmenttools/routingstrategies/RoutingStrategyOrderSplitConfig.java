package de.joesst.dev.fulfillmenttools.routingstrategies;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Configuration for order splitting within a routing strategy node.
 *
 * @param active                                   whether order splitting is active (required)
 * @param maxSplitCount                            maximum number of splits allowed (required)
 * @param activeForSameday                         whether splitting is active for same-day orders
 *                                                 (required)
 * @param shouldUseWaitingRoomForPreBackOrderItems whether pre-back-order items should use the
 *                                                 waiting room (required)
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record RoutingStrategyOrderSplitConfig(
        Boolean active,
        Integer maxSplitCount,
        Boolean activeForSameday,
        Boolean shouldUseWaitingRoomForPreBackOrderItems
) {

    /**
     * Returns a builder for constructing a {@code RoutingStrategyOrderSplitConfig}.
     *
     * @return a new {@link Builder}.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder for {@link RoutingStrategyOrderSplitConfig}.
     */
    public static final class Builder {

        private Boolean active;
        private Integer maxSplitCount;
        private Boolean activeForSameday;
        private Boolean shouldUseWaitingRoomForPreBackOrderItems;

        private Builder() {}

        /**
         * Sets whether order splitting is active.
         * @param active the active flag
         * @return this builder
         */
        public Builder active(Boolean active) {
            this.active = active;
            return this;
        }

        /**
         * Sets the maximum number of splits allowed.
         * @param maxSplitCount the maximum split count
         * @return this builder
         */
        public Builder maxSplitCount(Integer maxSplitCount) {
            this.maxSplitCount = maxSplitCount;
            return this;
        }

        /**
         * Sets whether splitting is active for same-day orders.
         * @param activeForSameday the same-day active flag
         * @return this builder
         */
        public Builder activeForSameday(Boolean activeForSameday) {
            this.activeForSameday = activeForSameday;
            return this;
        }

        /**
         * Sets whether pre-back-order items should use the waiting room.
         * @param shouldUseWaitingRoomForPreBackOrderItems the waiting room flag
         * @return this builder
         */
        public Builder shouldUseWaitingRoomForPreBackOrderItems(Boolean shouldUseWaitingRoomForPreBackOrderItems) {
            this.shouldUseWaitingRoomForPreBackOrderItems = shouldUseWaitingRoomForPreBackOrderItems;
            return this;
        }

        /**
         * Builds a {@link RoutingStrategyOrderSplitConfig}.
         *
         * @return a new instance.
         */
        public RoutingStrategyOrderSplitConfig build() {
            return new RoutingStrategyOrderSplitConfig(
                    active, maxSplitCount, activeForSameday, shouldUseWaitingRoomForPreBackOrderItems);
        }
    }
}
