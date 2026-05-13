package de.joesst.dev.fulfillmenttools.facilities;

import java.util.Map;

/**
 * Primary contact person for a facility.
 *
 * @param firstName contact person's first name
 * @param lastName contact person's family name
 * @param roleDescription job title or role description
 * @param customAttributes free-form custom attributes
 */
public record FacilityContact(String firstName, String lastName, String roleDescription, Map<String, Object> customAttributes) {}
