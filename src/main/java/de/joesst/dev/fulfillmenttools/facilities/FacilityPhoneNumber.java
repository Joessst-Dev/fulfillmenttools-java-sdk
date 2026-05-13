package de.joesst.dev.fulfillmenttools.facilities;

import java.util.Map;

/**
 * A phone number with optional type and label information.
 *
 * @param value the phone number string
 * @param type classification of the phone line (e.g. "LANDLINE", "MOBILE", "FAX")
 * @param label human-readable label (e.g. "Main", "Warehouse", "Customer Service")
 * @param customAttributes free-form custom attributes
 */
public record FacilityPhoneNumber(String value, String type, String label, Map<String, Object> customAttributes) {}
