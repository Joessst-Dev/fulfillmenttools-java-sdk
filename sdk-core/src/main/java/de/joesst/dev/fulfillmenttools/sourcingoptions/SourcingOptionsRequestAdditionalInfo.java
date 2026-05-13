package de.joesst.dev.fulfillmenttools.sourcingoptions;

/**
 * Additional configuration options for sourcing options requests.
 *
 * @param includeListingCustomAttributes Whether to include custom attributes from the listing in the result.
 */
public record SourcingOptionsRequestAdditionalInfo(Boolean includeListingCustomAttributes) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private Boolean includeListingCustomAttributes;

        public Builder includeListingCustomAttributes(Boolean includeListingCustomAttributes) { this.includeListingCustomAttributes = includeListingCustomAttributes; return this; }

        public SourcingOptionsRequestAdditionalInfo build() {
            return new SourcingOptionsRequestAdditionalInfo(includeListingCustomAttributes);
        }
    }
}
