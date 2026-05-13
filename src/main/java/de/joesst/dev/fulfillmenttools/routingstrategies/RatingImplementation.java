package de.joesst.dev.fulfillmenttools.routingstrategies;

/**
 * The built-in rating implementations available in the fulfillmenttools routing engine.
 */
public enum RatingImplementation {
    /** Stock balancing rating implementation. */
    STOCK_BALANCING("STOCK-BALANCING"),
    /** Geo distance rating implementation. */
    GEO_DISTANCE("GEO-DISTANCE"),
    /** Turnover rating implementation. */
    TURNOVER("TURNOVER"),
    /** Stock availability rating implementation. */
    STOCK_AVAILABILITY("STOCK-AVAILABILITY"),
    /** Workload balancing rating implementation. */
    WORKLOAD_BALANCING("WORKLOAD-BALANCING"),
    /** Matching business type rating implementation. */
    MATCHING_BUSINESSTYPE("MATCHING-BUSINESSTYPE"),
    /** Prefer store rating implementation. */
    PREFER_STORE("PREFER-STORE"),
    /** Prefer warehouse rating implementation. */
    PREFER_WAREHOUSE("PREFER-WAREHOUSE"),
    /** Zone rating implementation. */
    ZONE("ZONE"),
    /** Expiry date rating implementation. */
    EXPIRY_DATE("EXPIRY-DATE"),
    /** Capacity rating implementation. */
    CAPACITY("CAPACITY"),
    /** Delivery costs rating implementation. */
    DELIVERY_COSTS("DELIVERY-COSTS"),
    /** Delivery time rating implementation. */
    DELIVERY_TIME("DELIVERY-TIME"),
    /** Cost components rating implementation. */
    COST_COMPONENTS("COST-COMPONENTS"),
    /** Number of deliveries rating implementation. */
    NUMBER_OF_DELIVERIES("NUMBER-OF-DELIVERIES");

    private final String value;

    /**
     * Creates a new RatingImplementation.
     *
     * @param value the string value
     */
    RatingImplementation(String value) {
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
     * Deserializes a RatingImplementation from a string value.
     *
     * @param value the string value
     * @return the corresponding RatingImplementation
     * @throws IllegalArgumentException if the value is not recognized
     */
    @com.fasterxml.jackson.annotation.JsonCreator
    public static RatingImplementation fromValue(String value) {
        for (RatingImplementation impl : values()) {
            if (impl.value.equalsIgnoreCase(value)) {
                return impl;
            }
        }
        throw new IllegalArgumentException("Unknown RatingImplementation: " + value);
    }
}
