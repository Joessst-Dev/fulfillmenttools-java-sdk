package de.joesst.dev.fulfillmenttools.orders;

import de.joesst.dev.fulfillmenttools.model.Coordinates;

import java.util.List;
import java.util.Map;

public record ConsumerAddress(
        String street,
        String houseNumber,
        String city,
        String postalCode,
        String country,
        String additionalAddressInfo,
        String province,
        String firstName,
        String lastName,
        String personalTitle,
        String salutation,
        String companyName,
        String email,
        String addressType,
        Coordinates coordinates,
        List<Map<String, Object>> phoneNumbers,
        Map<String, Object> customAttributes
) {}
