package de.joesst.dev.fulfillmenttools.users;

public record User(
        String id,
        String email,
        String firstName,
        String lastName,
        String status
) {}
