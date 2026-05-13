package de.joesst.dev.fulfillmenttools.reservations;

/**
 * The type of system that owns a {@link Reservation}.
 *
 * <p>Maps to the {@code type} enum of {@code ReservationHost} in the fulfillmenttools OpenAPI spec.
 */
public enum HostType {

    /** The reservation is owned by a stock entry. */
    STOCK("STOCK"),

    /** The reservation is owned by an expected (incoming) stock entry. */
    EXPECTED_STOCK("EXPECTED_STOCK"),

    /** The reservation has no associated host. */
    NONE("NONE");

    private final String value;

    HostType(String value) {
        this.value = value;
    }

    /**
     * Returns the wire value used in JSON serialization.
     *
     * @return the JSON string value
     */
    @com.fasterxml.jackson.annotation.JsonValue
    public String getValue() {
        return value;
    }

    /**
     * Deserializes a JSON string to the corresponding {@code HostType}.
     *
     * @param value the JSON string value
     * @return the matching {@code HostType}
     * @throws IllegalArgumentException if no matching constant exists
     */
    @com.fasterxml.jackson.annotation.JsonCreator
    public static HostType fromValue(String value) {
        for (HostType hostType : values()) {
            if (hostType.value.equalsIgnoreCase(value)) {
                return hostType;
            }
        }
        throw new IllegalArgumentException("Unknown HostType: " + value);
    }
}
