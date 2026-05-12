package de.joesst.dev.fulfillmenttools.facilities;

import java.util.Map;

public record FacilityPhoneNumber(String value, String type, String label, Map<String, Object> customAttributes) {}
