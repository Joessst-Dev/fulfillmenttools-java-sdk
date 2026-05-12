package de.joesst.dev.fulfillmenttools.orders;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.joesst.dev.fulfillmenttools.model.Coordinates;

import java.util.List;
import java.util.Map;

/**
 * A consumer address used on an order.
 *
 * <p>Maps to the {@code ConsumerAddress} schema in the fulfillmenttools OpenAPI spec.
 * At minimum {@code street}, {@code city}, {@code postalCode}, and {@code country} are required
 * per the base {@code Address} schema.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param street               The street name.
 * @param houseNumber          The house number.
 * @param city                 The city.
 * @param postalCode           The postal code.
 * @param country              ISO 3166-1 alpha-2 two-letter country code (e.g. {@code DE}).
 * @param additionalAddressInfo Optional supplementary address information.
 * @param province             Optional province or state.
 * @param firstName            Optional first name of the recipient.
 * @param lastName             Optional last name of the recipient.
 * @param personalTitle        Optional personal title (e.g. {@code Dr.}).
 * @param salutation           Optional salutation.
 * @param companyName          Optional company name.
 * @param email                Optional email address.
 * @param addressType          The address type: {@code POSTAL_ADDRESS}, {@code PARCEL_LOCKER},
 *                             or {@code INVOICE_ADDRESS}.
 * @param coordinates          Optional geo-coordinates for the address.
 * @param phoneNumbers         Optional list of phone numbers for the recipient.
 * @param customAttributes     Free-form custom attributes.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
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
        List<PhoneNumber> phoneNumbers,
        Map<String, Object> customAttributes
) {}
