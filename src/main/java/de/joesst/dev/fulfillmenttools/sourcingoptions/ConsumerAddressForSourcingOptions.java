package de.joesst.dev.fulfillmenttools.sourcingoptions;

import de.joesst.dev.fulfillmenttools.model.Coordinates;

import java.util.List;
import java.util.Map;
import java.util.Objects;

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
    private final Map<String, Object> customAttributes;

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

    public String country() { return country; }
    public String firstName() { return firstName; }
    public String lastName() { return lastName; }
    public String email() { return email; }
    public String street() { return street; }
    public String houseNumber() { return houseNumber; }
    public String city() { return city; }
    public String postalCode() { return postalCode; }
    public String province() { return province; }
    public String companyName() { return companyName; }
    public String additionalAddressInfo() { return additionalAddressInfo; }
    public String personalTitle() { return personalTitle; }
    public String salutation() { return salutation; }
    public String addressType() { return addressType; }
    public Coordinates coordinates() { return coordinates; }
    public List<PhoneNumber> phoneNumbers() { return phoneNumbers; }
    public Map<String, Object> customAttributes() { return customAttributes; }

    public static Builder builder() { return new Builder(); }

    public record PhoneNumber(String value, String type, String label, Map<String, Object> customAttributes) {}

    public static final class Builder {

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
        private Map<String, Object> customAttributes;

        public Builder country(String country) { this.country = country; return this; }
        public Builder firstName(String firstName) { this.firstName = firstName; return this; }
        public Builder lastName(String lastName) { this.lastName = lastName; return this; }
        public Builder email(String email) { this.email = email; return this; }
        public Builder street(String street) { this.street = street; return this; }
        public Builder houseNumber(String houseNumber) { this.houseNumber = houseNumber; return this; }
        public Builder city(String city) { this.city = city; return this; }
        public Builder postalCode(String postalCode) { this.postalCode = postalCode; return this; }
        public Builder province(String province) { this.province = province; return this; }
        public Builder companyName(String companyName) { this.companyName = companyName; return this; }
        public Builder additionalAddressInfo(String additionalAddressInfo) { this.additionalAddressInfo = additionalAddressInfo; return this; }
        public Builder personalTitle(String personalTitle) { this.personalTitle = personalTitle; return this; }
        public Builder salutation(String salutation) { this.salutation = salutation; return this; }
        public Builder addressType(String addressType) { this.addressType = addressType; return this; }
        public Builder coordinates(Coordinates coordinates) { this.coordinates = coordinates; return this; }
        public Builder phoneNumbers(List<PhoneNumber> phoneNumbers) { this.phoneNumbers = phoneNumbers; return this; }
        public Builder customAttributes(Map<String, Object> customAttributes) { this.customAttributes = customAttributes; return this; }

        public ConsumerAddressForSourcingOptions build() { return new ConsumerAddressForSourcingOptions(this); }
    }
}
