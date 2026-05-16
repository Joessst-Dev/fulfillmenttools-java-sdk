package de.joesst.dev.fulfillmenttools.itemreturnjobs;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.joesst.dev.fulfillmenttools.id.ItemReturnJobId;
import de.joesst.dev.fulfillmenttools.model.CustomAttributes;
import de.joesst.dev.fulfillmenttools.orders.ConsumerAddress;

import java.time.Instant;
import java.util.List;

/**
 * Represents a fulfillmenttools item return job, which manages the physical return of
 * goods from a consumer to a facility.
 *
 * <p>Received as the payload of {@code ITEM_RETURN_JOB_CREATED} and
 * {@code ITEM_RETURN_JOB_UPDATED} events.
 *
 * @param id the platform-generated {@link ItemReturnJobId}
 * @param version the optimistic-locking version
 * @param created the timestamp when this item return job was created
 * @param lastModified the timestamp of the last modification
 * @param processRef reference to the operative process this return job belongs to
 * @param originFacilityRefs references to the facilities that originally fulfilled the order
 * @param status the current lifecycle status
 * @param consumerAddresses the consumer addresses associated with this return job
 * @param returnableLineItems line items that can still be returned
 * @param itemReturns individual item returns recorded against this job
 * @param anonymized whether this return job has been anonymized for GDPR compliance
 * @param shortId a human-readable short identifier
 * @param tenantOrderId the tenant's external order reference
 * @param scannableCodes scannable codes associated with this return job
 * @param notReturnableLineItems line items that are not eligible for return
 * @param customAttributes tenant-defined key/value extension attributes
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ItemReturnJob(
        ItemReturnJobId id,
        Integer version,
        Instant created,
        Instant lastModified,
        String processRef,
        List<String> originFacilityRefs,
        ItemReturnJobStatus status,
        List<ConsumerAddress> consumerAddresses,
        List<ItemReturnJobLineItem> returnableLineItems,
        List<ItemReturn> itemReturns,
        Boolean anonymized,
        String shortId,
        String tenantOrderId,
        List<String> scannableCodes,
        List<ItemReturnJobLineItem> notReturnableLineItems,
        CustomAttributes customAttributes
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private ItemReturnJobId id;
        private Integer version;
        private Instant created;
        private Instant lastModified;
        private String processRef;
        private List<String> originFacilityRefs;
        private ItemReturnJobStatus status;
        private List<ConsumerAddress> consumerAddresses;
        private List<ItemReturnJobLineItem> returnableLineItems;
        private List<ItemReturn> itemReturns;
        private Boolean anonymized;
        private String shortId;
        private String tenantOrderId;
        private List<String> scannableCodes;
        private List<ItemReturnJobLineItem> notReturnableLineItems;
        private CustomAttributes customAttributes;

        public Builder id(ItemReturnJobId id) {
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

        public Builder processRef(String processRef) {
            this.processRef = processRef;
            return this;
        }

        public Builder originFacilityRefs(List<String> originFacilityRefs) {
            this.originFacilityRefs = originFacilityRefs;
            return this;
        }

        public Builder status(ItemReturnJobStatus status) {
            this.status = status;
            return this;
        }

        public Builder consumerAddresses(List<ConsumerAddress> consumerAddresses) {
            this.consumerAddresses = consumerAddresses;
            return this;
        }

        public Builder returnableLineItems(List<ItemReturnJobLineItem> returnableLineItems) {
            this.returnableLineItems = returnableLineItems;
            return this;
        }

        public Builder itemReturns(List<ItemReturn> itemReturns) {
            this.itemReturns = itemReturns;
            return this;
        }

        public Builder anonymized(Boolean anonymized) {
            this.anonymized = anonymized;
            return this;
        }

        public Builder shortId(String shortId) {
            this.shortId = shortId;
            return this;
        }

        public Builder tenantOrderId(String tenantOrderId) {
            this.tenantOrderId = tenantOrderId;
            return this;
        }

        public Builder scannableCodes(List<String> scannableCodes) {
            this.scannableCodes = scannableCodes;
            return this;
        }

        public Builder notReturnableLineItems(List<ItemReturnJobLineItem> notReturnableLineItems) {
            this.notReturnableLineItems = notReturnableLineItems;
            return this;
        }

        public Builder customAttributes(CustomAttributes customAttributes) {
            this.customAttributes = customAttributes;
            return this;
        }

        public ItemReturnJob build() {
            return new ItemReturnJob(id, version, created, lastModified, processRef,
                    originFacilityRefs, status, consumerAddresses, returnableLineItems,
                    itemReturns, anonymized, shortId, tenantOrderId, scannableCodes,
                    notReturnableLineItems, customAttributes);
        }
    }
}
