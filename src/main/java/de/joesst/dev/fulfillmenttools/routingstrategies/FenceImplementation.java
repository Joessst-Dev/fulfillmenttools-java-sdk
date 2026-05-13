package de.joesst.dev.fulfillmenttools.routingstrategies;

/**
 * The built-in fence implementations available in the fulfillmenttools routing engine.
 */
public enum FenceImplementation {
    FACILITY_BUSINESSTYPE("FACILITY-BUSINESSTYPE"),
    STOCK_AVAILABILITY("STOCK-AVAILABILITY"),
    FACILITY_CARRIERAVAILABILITY("FACILITY-CARRIERAVAILABILITY"),
    FACILITY_COUNTRY("FACILITY-COUNTRY"),
    FACILITY_PICKING_TIME_CAPACITY("FACILITY-PICKING-TIME-CAPACITY"),
    PRESELECTED_FACILITY("PRESELECTED-FACILITY"),
    SAMEDAY_POSSIBLE("SAMEDAY-POSSIBLE"),
    AVOID_ZERO_STOCK("AVOID-ZERO-STOCK");

    private final String value;

    FenceImplementation(String value) {
        this.value = value;
    }

    @com.fasterxml.jackson.annotation.JsonValue
    public String getValue() {
        return value;
    }

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
