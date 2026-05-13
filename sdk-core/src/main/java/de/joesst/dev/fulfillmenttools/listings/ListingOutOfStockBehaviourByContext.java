package de.joesst.dev.fulfillmenttools.listings;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * Associates a specific out-of-stock behaviour with one or more evaluation contexts.
 *
 * <p>Maps to the {@code OutOfStockBehaviourByContext} schema in the fulfillmenttools OpenAPI spec.
 * This feature is currently in Alpha status.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param outOfStockBehaviour The behaviour to apply when the article is out of stock.
 *                            One of: {@code NONE}, {@code BACKORDER}, {@code PREORDER},
 *                            {@code RESTOCK}, {@code PREORDER_AND_RESTOCK}.
 * @param context             The list of conditions under which this behaviour applies
 *                            (1–5 entries).
 * @param priority            The priority of this entry; lower value means higher priority
 *                            (range 1–1000).
 * @param outOfStockConfig    Optional additional configuration required for {@code PREORDER}
 *                            or {@code RESTOCK} behaviours.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ListingOutOfStockBehaviourByContext(
        String outOfStockBehaviour,
        List<ListingOutOfStockBehaviourContext> context,
        Integer priority,
        ListingOutOfStockConfig outOfStockConfig
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String outOfStockBehaviour;
        private List<ListingOutOfStockBehaviourContext> context;
        private Integer priority;
        private ListingOutOfStockConfig outOfStockConfig;

        private Builder() {}

        public Builder outOfStockBehaviour(String outOfStockBehaviour) { this.outOfStockBehaviour = outOfStockBehaviour; return this; }
        public Builder context(List<ListingOutOfStockBehaviourContext> context) { this.context = context; return this; }
        public Builder priority(Integer priority) { this.priority = priority; return this; }
        public Builder outOfStockConfig(ListingOutOfStockConfig outOfStockConfig) { this.outOfStockConfig = outOfStockConfig; return this; }

        public ListingOutOfStockBehaviourByContext build() {
            return new ListingOutOfStockBehaviourByContext(outOfStockBehaviour, context, priority, outOfStockConfig);
        }
    }
}
