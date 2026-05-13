package de.joesst.dev.fulfillmenttools.id;

/**
 * Marker interface for system-generated UUID identifiers assigned by the
 * fulfillmenttools platform.
 *
 * <p>Passing a {@code PlatformId} subtype where a different subtype is expected
 * is a compile error, preventing silent 404s caused by swapped IDs.
 */
public non-sealed interface PlatformId extends TypedId {}
