package de.joesst.dev.fulfillmenttools.sourcingoptions;

import de.joesst.dev.fulfillmenttools.model.Coordinates;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import de.joesst.dev.fulfillmenttools.model.CustomAttributes;

/**
 * A consumer address for use in sourcing options requests.
 *
 * <p>Represents a delivery or billing address with comprehensive contact and location information.
 * Use the builder to construct instances with the required country field.
 */
public final class ConsumerAddressForSourcingOptions {

    private final String country;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String street;
    private final String houseNumber;
    private final String city;
    private final String postalCode;
    private final String province;
    private final String companyName;
    private final String additionalAddressInfo;
    private final String personalTitle;
    private final String salutation;
    private final String addressType;
    private final Coordinates coordinates;
    private final List<PhoneNumber> phoneNumbers;
    private final CustomAttributes customAttributes;

    private ConsumerAddressForSourcingOptions(Builder builder) {
        this.country = Objects.requireNonNull(builder.country, "country must not be null");
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.email = builder.email;
        this.street = builder.street;
        this.houseNumber = builder.houseNumber;
        this.city = builder.city;
        this.postalCode = builder.postalCode;
        this.province = builder.province;
        this.companyName = builder.companyName;
        this.additionalAddressInfo = builder.additionalAddressInfo;
        this.personalTitle = builder.personalTitle;
        this.salutation = builder.salutation;
        this.addressType = builder.addressType;
        this.coordinates = builder.coordinates;
        this.phoneNumbers = builder.phoneNumbers;
        this.customAttributes = builder.customAttributes;
    }

    /**
     * Returns the country code.
     * @return the country code; never {@code null}
     */
    public String country() { return country; }

    /**
     * Returns the first name.
     * @return the first name, or {@code null} if not set
     */
    public String firstName() { return firstName; }

    /**
     * Returns the last name.
     * @return the last name, or {@code null} if not set
     */
    public String lastName() { return lastName; }

    /**
     * Returns the email address.
     * @return the email address, or {@code null} if not set
     */
    public String email() { return email; }

    /**
     * Returns the street name.
     * @return the street name, or {@code null} if not set
     */
    public String street() { return street; }

    /**
     * Returns the house number.
     * @return the house number, or {@code null} if not set
     */
    public String houseNumber() { return houseNumber; }

    /**
     * Returns the city.
     * @return the city, or {@code null} if not set
     */
    public String city() { return city; }

    /**
     * Returns the postal code.
     * @return the postal code, or {@code null} if not set
     */
    public String postalCode() { return postalCode; }

    /**
     * Returns the province or state.
     * @return the province, or {@code null} if not set
     */
    public String province() { return province; }

    /**
     * Returns the company name.
     * @return the company name, or {@code null} if not set
     */
    public String companyName() { return companyName; }

    /**
     * Returns additional address information.
     * @return additional address info, or {@code null} if not set
     */
    public String additionalAddressInfo() { return additionalAddressInfo; }

    /**
     * Returns the personal title.
     * @return the personal title, or {@code null} if not set
     */
    public String personalTitle() { return personalTitle; }

    /**
     * Returns the salutation.
     * @return the salutation, or {@code null} if not set
     */
    public String salutation() { return salutation; }

    /**
     * Returns the address type.
     * @return the address type, or {@code null} if not set
     */
    public String addressType() { return addressType; }

    /**
     * Returns the geographic coordinates.
     * @return the coordinates, or {@code null} if not set
     */
    public Coordinates coordinates() { return coordinates; }

    /**
     * Returns the list of phone numbers.
     * @return the phone numbers, or {@code null} if not set
     */
    public List<PhoneNumber> phoneNumbers() { return phoneNumbers; }

    /**
     * Returns the free-form custom attributes.
     * @return the custom attributes map, or {@code null} if not set
     */
    public CustomAttributes customAttributes() { return customAttributes; }

    /**
     * Creates a new builder for constructing instances.
     * @return a new builder
     */
    public static Builder builder() { return new Builder(); }

    /**
     * A phone number associated with a consumer address.
     *
     * @param value the phone number value
     * @param type the type of phone number (e.g. mobile, home)
     * @param label an optional display label
     * @param customAttributes free-form custom attributes
     */
    public record PhoneNumber(String value, String type, String label, CustomAttributes customAttributes) {}

    /**
     * Builder for {@link ConsumerAddressForSourcingOptions}.
     */
    public static final class Builder {

        /** Creates a new Builder instance. */
        public Builder() {}

        private String country;
        private String firstName;
        private String lastName;
        private String email;
        private String street;
        private String houseNumber;
        private String city;
        private String postalCode;
        private String province;
        private String companyName;
        private String additionalAddressInfo;
        private String personalTitle;
        private String salutation;
        private String addressType;
        private Coordinates coordinates;
        private List<PhoneNumber> phoneNumbers;
        private CustomAttributes customAttributes;

        /**
         * Sets the country code (required).
         * @param country the country code
         * @return this builder
         */
        public Builder country(String country) { this.country = country; return this; }

        /**
         * Sets the first name.
         * @param firstName the first name
         * @return this builder
         */
        public Builder firstName(String firstName) { this.firstName = firstName; return this; }

        /**
         * Sets the last name.
         * @param lastName the last name
         * @return this builder
         */
        public Builder lastName(String lastName) { this.lastName = lastName; return this; }

        /**
         * Sets the email address.
         * @param email the email address
         * @return this builder
         */
        public Builder email(String email) { this.email = email; return this; }

        /**
         * Sets the street name.
         * @param street the street name
         * @return this builder
         */
        public Builder street(String street) { this.street = street; return this; }

        /**
         * Sets the house number.
         * @param houseNumber the house number
         * @return this builder
         */
        public Builder houseNumber(String houseNumber) { this.houseNumber = houseNumber; return this; }

        /**
         * Sets the city.
         * @param city the city
         * @return this builder
         */
        public Builder city(String city) { this.city = city; return this; }

        /**
         * Sets the postal code.
         * @param postalCode the postal code
         * @return this builder
         */
        public Builder postalCode(String postalCode) { this.postalCode = postalCode; return this; }

        /**
         * Sets the province or state.
         * @param province the province
         * @return this builder
         */
        public Builder province(String province) { this.province = province; return this; }

        /**
         * Sets the company name.
         * @param companyName the company name
         * @return this builder
         */
        public Builder companyName(String companyName) { this.companyName = companyName; return this; }

        /**
         * Sets additional address information.
         * @param additionalAddressInfo additional address info
         * @return this builder
         */
        public Builder additionalAddressInfo(String additionalAddressInfo) { this.additionalAddressInfo = additionalAddressInfo; return this; }

        /**
         * Sets the personal title.
         * @param personalTitle the personal title
         * @return this builder
         */
        public Builder personalTitle(String personalTitle) { this.personalTitle = personalTitle; return this; }

        /**
         * Sets the salutation.
         * @param salutation the salutation
         * @return this builder
         */
        public Builder salutation(String salutation) { this.salutation = salutation; return this; }

        /**
         * Sets the address type.
         * @param addressType the address type
         * @return this builder
         */
        public Builder addressType(String addressType) { this.addressType = addressType; return this; }

        /**
         * Sets the geographic coordinates.
         * @param coordinates the geographic coordinates
         * @return this builder
         */
        public Builder coordinates(Coordinates coordinates) { this.coordinates = coordinates; return this; }

        /**
         * Sets the list of phone numbers.
         * @param phoneNumbers the phone numbers
         * @return this builder
         */
        public Builder phoneNumbers(List<PhoneNumber> phoneNumbers) { this.phoneNumbers = phoneNumbers; return this; }

        /**
         * Sets the free-form custom attributes.
         * @param customAttributes the custom attributes map
         * @return this builder
         */
        public Builder customAttributes(CustomAttributes customAttributes) { this.customAttributes = customAttributes; return this; }

        /**
         * Builds and returns a new instance.
         * @return a new {@link ConsumerAddressForSourcingOptions}
         * @throws NullPointerException if {@code country} has not been set
         */
        public ConsumerAddressForSourcingOptions build() { return new ConsumerAddressForSourcingOptions(this); }
    }
}
