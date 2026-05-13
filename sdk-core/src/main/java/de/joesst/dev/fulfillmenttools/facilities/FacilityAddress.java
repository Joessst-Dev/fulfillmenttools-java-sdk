package de.joesst.dev.fulfillmenttools.facilities;

import de.joesst.dev.fulfillmenttools.model.Coordinates;
import de.joesst.dev.fulfillmenttools.model.TimeZone;

import java.util.List;
import java.util.Map;

/**
 * Physical address and contact information for a facility.
 *
 * @param street street name
 * @param houseNumber street number or building identifier
 * @param city city name
 * @param postalCode zip or postal code
 * @param country ISO 3166-1 alpha-2 country code
 * @param additionalAddressInfo supplementary address details (e.g. floor, suite)
 * @param province state or province code
 * @param companyName registered company or organization name
 * @param phoneNumbers list of phone numbers for contacting the facility
 * @param emailAddresses list of email addresses for contacting the facility
 * @param resolvedCoordinates latitude and longitude if geocoded
 * @param resolvedTimeZone IANA time zone identifier
 * @param customAttributes free-form custom attributes
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
        List<FacilityPhoneNumber> phoneNumbers,
        List<FacilityEmailAddress> emailAddresses,
        Coordinates resolvedCoordinates,
        TimeZone resolvedTimeZone,
        Map<String, Object> customAttributes
) {}
