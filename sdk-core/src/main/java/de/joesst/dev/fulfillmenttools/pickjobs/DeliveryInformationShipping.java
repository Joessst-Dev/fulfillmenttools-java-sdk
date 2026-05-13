package de.joesst.dev.fulfillmenttools.pickjobs;

import de.joesst.dev.fulfillmenttools.orders.ConsumerAddress;

import java.util.List;

/**
 * Shipping-specific delivery details within a pick job's delivery information.
 *
 * <p>Maps to the {@code details.shipping} sub-object of
 * {@code PickjobDeliveryInformationForCreation} in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param carrierKey              The carrier to use for shipping.
 * @param carrierProduct          The desired carrier product (e.g. {@code EXPRESS}).
 * @param carrierProductCategory  The product category ({@code STANDARD}, {@code EXPRESS},
 *                                {@code VALUE}, {@code FORWARDING}).
 * @param carrierServices         Additional carrier services (e.g. {@code SIGNATURE}).
 * @param identifier              Identifies the ship-from-store recipient.
 * @param serviceLevel            Delivery service level: {@code DELIVERY} or {@code SAMEDAY}.
 * @param recipientaddress        The recipient's delivery address.
 * @param postalAddress           The postal address for the shipment.
 * @param invoiceAddress          The invoice address for the shipment.
 */
public record DeliveryInformationShipping(
        String carrierKey,
        String carrierProduct,
        String carrierProductCategory,
        List<String> carrierServices,
        String identifier,
        String serviceLevel,
        ConsumerAddress recipientaddress,
        ConsumerAddress postalAddress,
        ConsumerAddress invoiceAddress
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String carrierKey;
        private String carrierProduct;
        private String carrierProductCategory;
        private List<String> carrierServices;
        private String identifier;
        private String serviceLevel;
        private ConsumerAddress recipientaddress;
        private ConsumerAddress postalAddress;
        private ConsumerAddress invoiceAddress;

        private Builder() {}

        public Builder carrierKey(String carrierKey) { this.carrierKey = carrierKey; return this; }
        public Builder carrierProduct(String carrierProduct) { this.carrierProduct = carrierProduct; return this; }
        public Builder carrierProductCategory(String carrierProductCategory) { this.carrierProductCategory = carrierProductCategory; return this; }
        public Builder carrierServices(List<String> carrierServices) { this.carrierServices = carrierServices; return this; }
        public Builder identifier(String identifier) { this.identifier = identifier; return this; }
        public Builder serviceLevel(String serviceLevel) { this.serviceLevel = serviceLevel; return this; }
        public Builder recipientaddress(ConsumerAddress recipientaddress) { this.recipientaddress = recipientaddress; return this; }
        public Builder postalAddress(ConsumerAddress postalAddress) { this.postalAddress = postalAddress; return this; }
        public Builder invoiceAddress(ConsumerAddress invoiceAddress) { this.invoiceAddress = invoiceAddress; return this; }

        public DeliveryInformationShipping build() {
            return new DeliveryInformationShipping(carrierKey, carrierProduct, carrierProductCategory, carrierServices, identifier, serviceLevel, recipientaddress, postalAddress, invoiceAddress);
        }
    }
}
