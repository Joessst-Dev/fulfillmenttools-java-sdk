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
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String street;
        private String houseNumber;
        private String city;
        private String postalCode;
        private String country;
        private String additionalAddressInfo;
        private String province;
        private String firstName;
        private String lastName;
        private String personalTitle;
        private String salutation;
        private String companyName;
        private String email;
        private String addressType;
        private Coordinates coordinates;
        private List<PhoneNumber> phoneNumbers;
        private Map<String, Object> customAttributes;
        private FacilityId facilityRef;
        private TenantFacilityId tenantFacilityId;

        private Builder() {}

        public Builder street(String street) { this.street = street; return this; }
        public Builder houseNumber(String houseNumber) { this.houseNumber = houseNumber; return this; }
        public Builder city(String city) { this.city = city; return this; }
        public Builder postalCode(String postalCode) { this.postalCode = postalCode; return this; }
        public Builder country(String country) { this.country = country; return this; }
        public Builder additionalAddressInfo(String additionalAddressInfo) { this.additionalAddressInfo = additionalAddressInfo; return this; }
        public Builder province(String province) { this.province = province; return this; }
        public Builder firstName(String firstName) { this.firstName = firstName; return this; }
        public Builder lastName(String lastName) { this.lastName = lastName; return this; }
        public Builder personalTitle(String personalTitle) { this.personalTitle = personalTitle; return this; }
        public Builder salutation(String salutation) { this.salutation = salutation; return this; }
        public Builder companyName(String companyName) { this.companyName = companyName; return this; }
        public Builder email(String email) { this.email = email; return this; }
        public Builder addressType(String addressType) { this.addressType = addressType; return this; }
        public Builder coordinates(Coordinates coordinates) { this.coordinates = coordinates; return this; }
        public Builder phoneNumbers(List<PhoneNumber> phoneNumbers) { this.phoneNumbers = phoneNumbers; return this; }
        public Builder customAttributes(Map<String, Object> customAttributes) { this.customAttributes = customAttributes; return this; }
        public Builder facilityRef(FacilityId facilityRef) { this.facilityRef = facilityRef; return this; }
        public Builder tenantFacilityId(TenantFacilityId tenantFacilityId) { this.tenantFacilityId = tenantFacilityId; return this; }

        public TargetAddress build() {
            return new TargetAddress(street, houseNumber, city, postalCode, country, additionalAddressInfo, province, firstName, lastName, personalTitle, salutation, companyName, email, addressType, coordinates, phoneNumbers, customAttributes, facilityRef, tenantFacilityId);
        }
    }
}
