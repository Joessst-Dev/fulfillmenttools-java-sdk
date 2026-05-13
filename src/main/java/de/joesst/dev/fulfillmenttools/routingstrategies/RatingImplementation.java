package de.joesst.dev.fulfillmenttools.routingstrategies;

/**
 * The built-in rating implementations available in the fulfillmenttools routing engine.
 */
public enum RatingImplementation {
    STOCK_BALANCING("STOCK-BALANCING"),
    GEO_DISTANCE("GEO-DISTANCE"),
    TURNOVER("TURNOVER"),
    STOCK_AVAILABILITY("STOCK-AVAILABILITY"),
    WORKLOAD_BALANCING("WORKLOAD-BALANCING"),
    MATCHING_BUSINESSTYPE("MATCHING-BUSINESSTYPE"),
    PREFER_STORE("PREFER-STORE"),
    PREFER_WAREHOUSE("PREFER-WAREHOUSE"),
    ZONE("ZONE"),
    EXPIRY_DATE("EXPIRY-DATE"),
    CAPACITY("CAPACITY"),
    DELIVERY_COSTS("DELIVERY-COSTS"),
    DELIVERY_TIME("DELIVERY-TIME"),
    COST_COMPONENTS("COST-COMPONENTS"),
    NUMBER_OF_DELIVERIES("NUMBER-OF-DELIVERIES");

    private final String value;

    RatingImplementation(String value) {
        this.value = value;
    }

    @com.fasterxml.jackson.annotation.JsonValue
    public String getValue() {
        return value;
    }

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
