package de.joesst.dev.fulfillmenttools.orders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * The source of an order, identifying the external system that created it.
 *
 * <p>Maps to the {@code source} field on {@code OrderForCreation} in the fulfillmenttools
 * OpenAPI spec, which is a {@code oneOf} between {@code SourceRef} and {@code TenantSourceId}.
 *
 * <p>Use {@link BySourceRef} when you have a fulfillmenttools source reference ID, or
 * {@link ByTenantSourceId} when you have a tenant-specific source identifier.
 *
 * <p>Thread-safety: immutable sealed hierarchy; safe for concurrent use.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
@JsonSubTypes({
        @JsonSubTypes.Type(value = OrderSource.BySourceRef.class),
        @JsonSubTypes.Type(value = OrderSource.ByTenantSourceId.class)
})
public sealed interface OrderSource
        permits OrderSource.BySourceRef, OrderSource.ByTenantSourceId {

    /**
     * Order source identified by a fulfillmenttools {@code sourceRef}.
     *
     * @param sourceRef The fulfillmenttools source reference identifier.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    record BySourceRef(String sourceRef) implements OrderSource {

        /**
         * Returns a builder for constructing a {@code BySourceRef}.
         *
         * @return a new {@link Builder}.
         */
        static Builder builder() {
            return new Builder();
        }

        /**
         * Builder for {@link BySourceRef}.
         */
        static final class Builder {

            private String sourceRef;

            private Builder() {}

            /**
             * Sets the fulfillmenttools source reference identifier.
             * @param sourceRef the source reference identifier
             * @return this builder
             */
            public Builder sourceRef(String sourceRef) {
                this.sourceRef = sourceRef;
                return this;
            }

            /**
             * Builds a {@link BySourceRef}.
             *
             * @return a new instance.
             */
            public BySourceRef build() {
                return new BySourceRef(sourceRef);
            }
        }
    }

    /**
     * Order source identified by a tenant-specific source identifier.
     *
     * @param tenantSourceId The tenant's own source system identifier.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    record ByTenantSourceId(String tenantSourceId) implements OrderSource {

        /**
         * Returns a builder for constructing a {@code ByTenantSourceId}.
         *
         * @return a new {@link Builder}.
         */
        static Builder builder() {
            return new Builder();
        }

        /**
         * Builder for {@link ByTenantSourceId}.
         */
        static final class Builder {

            private String tenantSourceId;

            private Builder() {}

            /**
             * Sets the tenant's own source system identifier.
             * @param tenantSourceId the tenant source identifier
             * @return this builder
             */
            public Builder tenantSourceId(String tenantSourceId) {
                this.tenantSourceId = tenantSourceId;
                return this;
            }

            /**
             * Builds a {@link ByTenantSourceId}.
             *
             * @return a new instance.
             */
            public ByTenantSourceId build() {
                return new ByTenantSourceId(tenantSourceId);
            }
        }
    }
}
