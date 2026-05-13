package de.joesst.dev.fulfillmenttools.routingstrategies;

/**
 * The mode in which a routing strategy fence operates.
 */
public enum FenceMode {
    /**
     * The fence is evaluated once with a fixed set of values.
     */
    STATIC("static"),
    /**
     * The fence is evaluated reactively based on dynamic conditions.
     */
    REACTIVE("reactive");

    private final String value;

    /**
     * Creates a new FenceMode.
     *
     * @param value the string value
     */
    FenceMode(String value) {
        this.value = value;
    }

    /**
     * Returns the string value of this mode.
     *
     * @return the value
     */
    @com.fasterxml.jackson.annotation.JsonValue
    public String getValue() {
        return value;
    }

    /**
     * Deserializes a FenceMode from a string value.
     *
     * @param value the string value
     * @return the corresponding FenceMode
     * @throws IllegalArgumentException if the value is not recognized
     */
    @com.fasterxml.jackson.annotation.JsonCreator
    public static FenceMode fromValue(String value) {
        for (FenceMode mode : values()) {
            if (mode.value.equalsIgnoreCase(value)) {
                return mode;
            }
        }
        throw new IllegalArgumentException("Unknown FenceMode: " + value);
    }
}
