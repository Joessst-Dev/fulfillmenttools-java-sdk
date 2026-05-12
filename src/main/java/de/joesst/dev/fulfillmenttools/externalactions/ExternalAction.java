package de.joesst.dev.fulfillmenttools.externalactions;

public record ExternalAction(
        String id,
        String name,
        String actionType,
        String status
) {}
