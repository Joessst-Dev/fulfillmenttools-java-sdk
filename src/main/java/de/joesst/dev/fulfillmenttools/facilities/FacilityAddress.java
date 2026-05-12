package de.joesst.dev.fulfillmenttools.facilities;

import java.util.Map;

/**
 * Physical address of a facility.
 */
public record FacilityAddress(
        String street,
        String houseNumber,
        String city,
        String postalCode,
        String country,
        String additionalAddressInfo,
        String province,
        String companyName,
        Map<String, Object> customAttributes
) {}
