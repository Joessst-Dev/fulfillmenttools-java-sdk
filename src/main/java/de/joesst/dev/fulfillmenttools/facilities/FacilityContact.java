package de.joesst.dev.fulfillmenttools.facilities;

import java.util.Map;

public record FacilityContact(String firstName, String lastName, String roleDescription, Map<String, Object> customAttributes) {}
