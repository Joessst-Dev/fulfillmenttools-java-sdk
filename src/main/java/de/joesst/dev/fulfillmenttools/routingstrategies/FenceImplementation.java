package de.joesst.dev.fulfillmenttools.routingstrategies;

/**
 * The built-in fence implementations available in the fulfillmenttools routing engine.
 */
public enum FenceImplementation {
    /** Facility business type fence implementation. */
    FACILITY_BUSINESSTYPE("FACILITY-BUSINESSTYPE"),
    /** Stock availability fence implementation. */
    STOCK_AVAILABILITY("STOCK-AVAILABILITY"),
    /** Facility carrier availability fence implementation. */
    FACILITY_CARRIERAVAILABILITY("FACILITY-CARRIERAVAILABILITY"),
    /** Facility country fence implementation. */
    FACILITY_COUNTRY("FACILITY-COUNTRY"),
    /** Facility picking time capacity fence implementation. */
    FACILITY_PICKING_TIME_CAPACITY("FACILITY-PICKING-TIME-CAPACITY"),
    /** Preselected facility fence implementation. */
    PRESELECTED_FACILITY("PRESELECTED-FACILITY"),
    /** Same-day possible fence implementation. */
    SAMEDAY_POSSIBLE("SAMEDAY-POSSIBLE"),
    /** Avoid zero stock fence implementation. */
    AVOID_ZERO_STOCK("AVOID-ZERO-STOCK");

    private final String value;

    /**
     * Creates a new FenceImplementation.
     *
     * @param value the string value
     */
    FenceImplementation(String value) {
        this.value = value;
    }

    /**
     * Returns the string value of this implementation.
     *
     * @return the value
     */
    @com.fasterxml.jackson.annotation.JsonValue
    public String getValue() {
        return value;
    }

    /**
     * Deserializes a FenceImplementation from a string value.
     *
     * @param value the string value
     * @return the corresponding FenceImplementation
     * @throws IllegalArgumentException if the value is not recognized
     */
    @com.fasterxml.jackson.annotation.JsonCreator
    public static FenceImplementation fromValue(String value) {
        for (FenceImplementation impl : values()) {
            if (impl.value.equalsIgnoreCase(value)) {
                return impl;
            }
        }
        throw new IllegalArgumentException("Unknown FenceImplementation: " + value);
    }
}
