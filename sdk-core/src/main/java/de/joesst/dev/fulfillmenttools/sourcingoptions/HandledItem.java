package de.joesst.dev.fulfillmenttools.sourcingoptions;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * An article and quantity handled within a sourcing option node or transfer.
 *
 * <p>Maps to the {@code HandledItem} schema in the fulfillmenttools OpenAPI specification.
 *
 * @param tenantArticleId the tenant-specific article identifier
 * @param quantity        the quantity of the article
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record HandledItem(
        String tenantArticleId,
        Double quantity
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private String tenantArticleId;
        private Double quantity;

        public Builder tenantArticleId(String tenantArticleId) { this.tenantArticleId = tenantArticleId; return this; }
        public Builder quantity(Double quantity) { this.quantity = quantity; return this; }

        public HandledItem build() {
            return new HandledItem(tenantArticleId, quantity);
        }
    }
}
