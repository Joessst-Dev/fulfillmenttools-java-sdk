package de.joesst.dev.fulfillmenttools.id;

/**
 * Marker interface for all fulfillmenttools typed identifiers.
 *
 * <p>Sealed to exactly two categories: {@link PlatformId} for system-generated UUIDs
 * and {@link TenantId} for customer-defined identifiers.
 */
public sealed interface TypedId permits PlatformId, TenantId {

    /**
     * Returns the raw string value of this identifier.
     * @return the identifier value; never {@code null}
     */
    String value();
}
