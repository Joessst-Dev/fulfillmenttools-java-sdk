package de.joesst.dev.fulfillmenttools.orders;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.Map;
import java.util.Objects;

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

    private final String tenantArticleId;
    private final String title;
    private final String imageUrl;
    private final Double weight;
    private final Map<String, String> titleLocalized;
    private final List<ArticleAttribute> attributes;
    private final Map<String, Object> customAttributes;

    private OrderLineItemArticleForCreation(Builder builder) {
        this.tenantArticleId = Objects.requireNonNull(builder.tenantArticleId, "tenantArticleId must not be null");
        this.title = builder.title;
        this.imageUrl = builder.imageUrl;
        this.weight = builder.weight;
        this.titleLocalized = builder.titleLocalized;
        this.attributes = builder.attributes;
        this.customAttributes = builder.customAttributes;
    }

    public String tenantArticleId() { return tenantArticleId; }
    public String title() { return title; }
    public String imageUrl() { return imageUrl; }
    public Double weight() { return weight; }
    /** Localized translations for the article title, keyed by locale (e.g. {@code en_US}). */
    public Map<String, String> titleLocalized() { return titleLocalized; }
    /** Article attributes for display and platform customization. */
    public List<ArticleAttribute> attributes() { return attributes; }
    public Map<String, Object> customAttributes() { return customAttributes; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {

        private String tenantArticleId;
        private String title;
        private String imageUrl;
        private Double weight;
        private Map<String, String> titleLocalized;
        private List<ArticleAttribute> attributes;
        private Map<String, Object> customAttributes;

        public Builder tenantArticleId(String tenantArticleId) { this.tenantArticleId = tenantArticleId; return this; }
        public Builder title(String title) { this.title = title; return this; }
        public Builder imageUrl(String imageUrl) { this.imageUrl = imageUrl; return this; }
        public Builder weight(Double weight) { this.weight = weight; return this; }
        public Builder titleLocalized(Map<String, String> titleLocalized) { this.titleLocalized = titleLocalized; return this; }
        public Builder attributes(List<ArticleAttribute> attributes) { this.attributes = attributes; return this; }
        public Builder customAttributes(Map<String, Object> customAttributes) { this.customAttributes = customAttributes; return this; }

        public OrderLineItemArticleForCreation build() { return new OrderLineItemArticleForCreation(this); }
    }
}
