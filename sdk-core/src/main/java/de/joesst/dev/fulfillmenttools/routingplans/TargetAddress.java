package de.joesst.dev.fulfillmenttools.routingplans;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.id.TenantFacilityId;
import de.joesst.dev.fulfillmenttools.model.Coordinates;
import de.joesst.dev.fulfillmenttools.orders.PhoneNumber;

import java.util.List;
import java.util.Map;

/**
 * The delivery target address for a routing plan.
 *
 * <p>Maps to the {@code TargetAddress} schema in the fulfillmenttools OpenAPI spec, which
 * extends {@code ConsumerAddress} (itself an extension of {@code Address}) with optional
 * facility references.
 *
 * <p>Required fields per the base {@code Address} schema: {@code street}, {@code city},
 * {@code postalCode}, and {@code country}.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param street               The street name (required).
 * @param houseNumber          The house number.
 * @param city                 The city (required).
 * @param postalCode           The postal code (required).
 * @param country              ISO 3166-1 alpha-2 two-letter country code, e.g. {@code "DE"}
 *                             (required).
 * @param additionalAddressInfo Optional supplementary address information.
 * @param province             Optional province or state.
 * @param firstName            Optional first name of the recipient.
 * @param lastName             Optional last name of the recipient.
 * @param personalTitle        Optional personal title, e.g. {@code "Dr."}.
 * @param salutation           Optional salutation.
 * @param companyName          Optional company name.
 * @param email                Optional email address.
 * @param addressType          Optional address type, e.g. {@code "POSTAL_ADDRESS"},
 *                             {@code "PARCEL_LOCKER"}, or {@code "INVOICE_ADDRESS"}.
 * @param coordinates          Optional geo-coordinates.
 * @param phoneNumbers         Optional list of phone numbers.
 * @param customAttributes     Free-form custom attributes.
 * @param facilityRef          Optional ID of the facility associated with this address.
 * @param tenantFacilityId     Optional tenant-side {@link TenantFacilityId} facility ID.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record TargetAddress(
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
        Map<String, Object> customAttributes,
        FacilityId facilityRef,
        TenantFacilityId tenantFacilityId
) {}
