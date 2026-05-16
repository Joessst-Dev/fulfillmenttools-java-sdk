package de.joesst.dev.fulfillmenttools.purchaseorders;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.id.PurchaseOrderId;

import java.time.Instant;
import java.util.List;
import de.joesst.dev.fulfillmenttools.model.CustomAttributes;

/**
 * Represents a fulfillmenttools purchase order, which records requested inbound goods
 * against an inbound process.
 *
 * <p>Received as the payload of {@code INBOUND_PROCESS_PURCHASE_ORDER_CREATED},
 * {@code INBOUND_PROCESS_PURCHASE_ORDER_CANCELED},
 * {@code INBOUND_PROCESS_PURCHASE_ORDER_DELETED},
 * {@code INBOUND_PROCESS_PURCHASE_ORDER_LINE_ITEMS_CHANGED}, and
 * {@code INBOUND_PROCESS_PURCHASE_ORDER_REQUESTED_DATE_CHANGED} events.
 *
 * @param id the platform-generated {@link PurchaseOrderId}
 * @param version the optimistic-locking version
 * @param created the timestamp when this purchase order was created
 * @param lastModified the timestamp of the last modification
 * @param facilityRef the {@link FacilityId} of the facility this purchase order belongs to
 * @param inboundProcessRef reference to the parent inbound process
 * @param orderDate the date the purchase order was placed
 * @param requestedDate the requested delivery date
 * @param requestedItems the line items requested on this purchase order
 * @param status the current lifecycle status
 * @param cancelled deprecated — use {@link #status} instead
 * @param customAttributes tenant-defined key/value extension attributes
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record PurchaseOrder(
        PurchaseOrderId id,
        Integer version,
        Instant created,
        Instant lastModified,
        FacilityId facilityRef,
        String inboundProcessRef,
        Instant orderDate,
        RequestedDate requestedDate,
        List<InboundLineItem> requestedItems,
        PurchaseOrderStatus status,
        Boolean cancelled,
        CustomAttributes customAttributes
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private PurchaseOrderId id;
        private Integer version;
        private Instant created;
        private Instant lastModified;
        private FacilityId facilityRef;
        private String inboundProcessRef;
        private Instant orderDate;
        private RequestedDate requestedDate;
        private List<InboundLineItem> requestedItems;
        private PurchaseOrderStatus status;
        private Boolean cancelled;
        private CustomAttributes customAttributes;

        public Builder id(PurchaseOrderId id) {
            this.id = id;
            return this;
        }

        public Builder version(Integer version) {
            this.version = version;
            return this;
        }

        public Builder created(Instant created) {
            this.created = created;
            return this;
        }

        public Builder lastModified(Instant lastModified) {
            this.lastModified = lastModified;
            return this;
        }

        public Builder facilityRef(FacilityId facilityRef) {
            this.facilityRef = facilityRef;
            return this;
        }

        public Builder inboundProcessRef(String inboundProcessRef) {
            this.inboundProcessRef = inboundProcessRef;
            return this;
        }

        public Builder orderDate(Instant orderDate) {
            this.orderDate = orderDate;
            return this;
        }

        public Builder requestedDate(RequestedDate requestedDate) {
            this.requestedDate = requestedDate;
            return this;
        }

        public Builder requestedItems(List<InboundLineItem> requestedItems) {
            this.requestedItems = requestedItems;
            return this;
        }

        public Builder status(PurchaseOrderStatus status) {
            this.status = status;
            return this;
        }

        public Builder cancelled(Boolean cancelled) {
            this.cancelled = cancelled;
            return this;
        }

        public Builder customAttributes(CustomAttributes customAttributes) {
            this.customAttributes = customAttributes;
            return this;
        }

        public PurchaseOrder build() {
            return new PurchaseOrder(id, version, created, lastModified, facilityRef, inboundProcessRef,
                    orderDate, requestedDate, requestedItems, status, cancelled, customAttributes);
        }
    }
}
