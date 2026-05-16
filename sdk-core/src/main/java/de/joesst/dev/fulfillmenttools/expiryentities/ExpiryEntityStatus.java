package de.joesst.dev.fulfillmenttools.expiryentities;

/**
 * The lifecycle status of an expiry entity.
 *
 * <p>Maps to the {@code ExpiryEntityStatus} schema in the fulfillmenttools OpenAPI specification.
 */
public enum ExpiryEntityStatus {
    /** The expiry entity is active and will fire at {@code expiryTime}. */
    ACTIVE,
    /** The expiry entity has been deactivated and will not fire. */
    INACTIVE
}
