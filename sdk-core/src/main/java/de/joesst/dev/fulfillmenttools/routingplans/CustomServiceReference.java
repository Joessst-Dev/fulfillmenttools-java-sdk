package de.joesst.dev.fulfillmenttools.routingplans;

import java.util.List;

/**
 * A reference to a custom service attached to a routing plan.
 *
 * <p>Maps to the {@code CustomServiceReference} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param id                     The custom service reference identifier (required).
 * @param customServiceDefinition The definition of the custom service (required).
 * @param articleItems            The article items associated with this custom service (required).
 * @param customServiceItems      The custom service sub-items (required).
 */
public record CustomServiceReference(
        String id,
        CustomServiceDefinition customServiceDefinition,
        List<ArticleItem> articleItems,
        List<CustomServiceItem> customServiceItems
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String id;
        private CustomServiceDefinition customServiceDefinition;
        private List<ArticleItem> articleItems;
        private List<CustomServiceItem> customServiceItems;

        private Builder() {}

        public Builder id(String id) { this.id = id; return this; }
        public Builder customServiceDefinition(CustomServiceDefinition customServiceDefinition) { this.customServiceDefinition = customServiceDefinition; return this; }
        public Builder articleItems(List<ArticleItem> articleItems) { this.articleItems = articleItems; return this; }
        public Builder customServiceItems(List<CustomServiceItem> customServiceItems) { this.customServiceItems = customServiceItems; return this; }

        public CustomServiceReference build() {
            return new CustomServiceReference(id, customServiceDefinition, articleItems, customServiceItems);
        }
    }
}
