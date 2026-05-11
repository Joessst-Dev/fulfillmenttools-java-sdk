package de.joesst.dev.fulfillmenttools;

public record ApiError(
        String description,
        String summary,
        int requestVersion,
        int version
) {}
