package de.joesst.dev.fulfillmenttools.facilities;

import de.joesst.dev.fulfillmenttools.model.Coordinates;
import de.joesst.dev.fulfillmenttools.model.TimeZone;

import java.util.List;
import java.util.Map;

public record FacilityAddress(
        String street,
        String houseNumber,
        String city,
        String postalCode,
        String country,
        String additionalAddressInfo,
        String province,
        String companyName,
        List<FacilityPhoneNumber> phoneNumbers,
        List<FacilityEmailAddress> emailAddresses,
        Coordinates resolvedCoordinates,
        TimeZone resolvedTimeZone,
        Map<String, Object> customAttributes
) {}
