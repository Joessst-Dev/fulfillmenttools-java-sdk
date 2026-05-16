package de.joesst.dev.fulfillmenttools.itemreturnjobs;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.joesst.dev.fulfillmenttools.id.TenantArticleId;

/**
 * Article reference within an item return job line item.
 *
 * @param tenantArticleId the tenant's article identifier
 * @param title the human-readable article title
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ItemReturnJobLineItemArticle(
        TenantArticleId tenantArticleId,
        String title
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private TenantArticleId tenantArticleId;
        private String title;

        public Builder tenantArticleId(TenantArticleId tenantArticleId) {
            this.tenantArticleId = tenantArticleId;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public ItemReturnJobLineItemArticle build() {
            return new ItemReturnJobLineItemArticle(tenantArticleId, title);
        }
    }
}
