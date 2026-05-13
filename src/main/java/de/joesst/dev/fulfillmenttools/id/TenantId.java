package de.joesst.dev.fulfillmenttools.id;

/**
 * Marker interface for customer-defined (tenant-supplied) identifiers.
 *
 * <p>Tenant IDs are looked up via a URN-style API path. Use {@link #toUrn(String)}
 * to produce the path segment, e.g.:
 * <pre>{@code
 * TenantFacilityId id = new TenantFacilityId("wh-berlin");
 * // → "urn:fft:facility:tenantFacilityId:wh-berlin"
 * String urn = id.toUrn("facility:tenantFacilityId");
 * }</pre>
 */
public non-sealed interface TenantId extends TypedId {

    /**
     * Returns the URN-style path segment used when looking up this resource
     * by its tenant identifier.
     *
     * @param resourceType the resource-type segment, e.g. {@code "facility:tenantFacilityId"}
     * @return the full URN, e.g. {@code "urn:fft:facility:tenantFacilityId:wh-berlin"}
     */
    default String toUrn(String resourceType) {
        return "urn:fft:" + resourceType + ":" + value();
    }
}
