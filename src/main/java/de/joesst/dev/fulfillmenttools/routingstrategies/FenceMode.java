package de.joesst.dev.fulfillmenttools.routingstrategies;

/**
 * The mode in which a routing strategy fence operates.
 */
public enum FenceMode {
    /** The fence is evaluated once with a fixed set of values. */
    STATIC("static"),
    /** The fence is evaluated reactively based on dynamic conditions. */
    REACTIVE("reactive");

    private final String value;

    FenceMode(String value) {
        this.value = value;
    }

    @com.fasterxml.jackson.annotation.JsonValue
    public String getValue() {
        return value;
    }

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
