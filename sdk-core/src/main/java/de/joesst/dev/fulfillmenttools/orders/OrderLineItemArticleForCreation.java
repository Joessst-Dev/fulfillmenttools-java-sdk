package de.joesst.dev.fulfillmenttools.orders;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.joesst.dev.fulfillmenttools.id.TenantArticleId;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import de.joesst.dev.fulfillmenttools.model.CustomAttributes;

/**
 * Article data for creating an order line item.
 *
 * <p>Maps to the {@code AbstractArticle} / {@code OrderLineItemArticle} schema on the write side
 * in the fulfillmenttools OpenAPI spec. {@code tenantArticleId} is required.
 *
 * <p>Thread-safety: immutable; safe for concurrent use.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class OrderLineItemArticleForCreation {

    private final TenantArticleId tenantArticleId;
    private final String title;
    private final String imageUrl;
    private final Double weight;
    private final Map<String, String> titleLocalized;
    private final List<ArticleAttribute> attributes;
    private final CustomAttributes customAttributes;

    private OrderLineItemArticleForCreation(Builder builder) {
        this.tenantArticleId = Objects.requireNonNull(builder.tenantArticleId, "tenantArticleId must not be null");

        this.title = builder.title;
        this.imageUrl = builder.imageUrl;
        this.weight = builder.weight;
        this.titleLocalized = builder.titleLocalized;
        this.attributes = builder.attributes;
        this.customAttributes = builder.customAttributes;
    }

    /**
     * Returns the tenant article ID.
     *
     * @return the tenant article ID (never null)
     */
    public TenantArticleId tenantArticleId() { return tenantArticleId; }

    /**
     * Returns the article title.
     *
     * @return the title, or null if not set
     */
    public String title() { return title; }

    /**
     * Returns the article image URL.
     *
     * @return the image URL, or null if not set
     */
    public String imageUrl() { return imageUrl; }

    /**
     * Returns the article weight.
     *
     * @return the weight, or null if not set
     */
    public Double weight() { return weight; }

    /**
     * Returns localized translations for the article title, keyed by locale (e.g. {@code en_US}).
     *
     * @return the localized titles map, or null if not set
     */
    public Map<String, String> titleLocalized() { return titleLocalized; }

    /**
     * Returns article attributes for display and platform customization.
     *
     * @return the attributes list, or null if not set
     */
    public List<ArticleAttribute> attributes() { return attributes; }

    /**
     * Returns custom attributes for this article.
     *
     * @return the custom attributes map, or null if not set
     */
    public CustomAttributes customAttributes() { return customAttributes; }

    /**
     * Creates a new builder for constructing an {@code OrderLineItemArticleForCreation}.
     *
     * @return a new builder
     */
    public static Builder builder() { return new Builder(); }

    /**
     * Builder for {@link OrderLineItemArticleForCreation}.
     */
    public static final class Builder {

        /** Creates a new Builder. */
        public Builder() {}

        private TenantArticleId tenantArticleId;
        private String title;
        private String imageUrl;
        private Double weight;
        private Map<String, String> titleLocalized;
        private List<ArticleAttribute> attributes;
        private CustomAttributes customAttributes;

        /**
         * Sets the tenant article ID (required).
         *
         * @param tenantArticleId the article ID
         * @return this builder
         */
        public Builder tenantArticleId(TenantArticleId tenantArticleId) { this.tenantArticleId = tenantArticleId; return this; }

        /**
         * Sets the article title.
         *
         * @param title the title
         * @return this builder
         */
        public Builder title(String title) { this.title = title; return this; }

        /**
         * Sets the article image URL.
         *
         * @param imageUrl the image URL
         * @return this builder
         */
        public Builder imageUrl(String imageUrl) { this.imageUrl = imageUrl; return this; }

        /**
         * Sets the article weight.
         *
         * @param weight the weight
         * @return this builder
         */
        public Builder weight(Double weight) { this.weight = weight; return this; }

        /**
         * Sets localized translations for the article title.
         *
         * @param titleLocalized the localized titles map
         * @return this builder
         */
        public Builder titleLocalized(Map<String, String> titleLocalized) { this.titleLocalized = titleLocalized; return this; }

        /**
         * Sets article attributes.
         *
         * @param attributes the attributes list
         * @return this builder
         */
        public Builder attributes(List<ArticleAttribute> attributes) { this.attributes = attributes; return this; }

        /**
         * Sets custom attributes for this article.
         *
         * @param customAttributes the custom attributes map
         * @return this builder
         */
        public Builder customAttributes(CustomAttributes customAttributes) { this.customAttributes = customAttributes; return this; }

        /**
         * Builds the {@link OrderLineItemArticleForCreation}.
         *
         * @return a new article
         * @throws NullPointerException if tenantArticleId has not been set
         */
        public OrderLineItemArticleForCreation build() { return new OrderLineItemArticleForCreation(this); }
    }
}
