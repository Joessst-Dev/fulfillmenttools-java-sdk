package de.joesst.dev.fulfillmenttools.reservations;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.joesst.dev.fulfillmenttools.id.OrderId;
import de.joesst.dev.fulfillmenttools.id.PickJobId;
import de.joesst.dev.fulfillmenttools.id.ProcessId;
import de.joesst.dev.fulfillmenttools.id.RoutingPlanId;

import java.util.List;

/**
 * A set of related entity references associated with a {@link Reservation}.
 *
 * <p>All fields are optional; absent arrays are represented as {@code null}.
 *
 * <p>Maps to {@code RelatedRefs} in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param orderRefs       references to related orders
 * @param pickJobRefs     references to related pick jobs
 * @param processRefs     references to related processes
 * @param routingPlanRefs references to related routing plans
 * @param transferRefs    references to related transfers (kept as {@code String}; no typed ID class)
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record RelatedRefs(
        List<OrderId> orderRefs,
        List<PickJobId> pickJobRefs,
        List<ProcessId> processRefs,
        List<RoutingPlanId> routingPlanRefs,
        List<String> transferRefs
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private List<OrderId> orderRefs;
        private List<PickJobId> pickJobRefs;
        private List<ProcessId> processRefs;
        private List<RoutingPlanId> routingPlanRefs;
        private List<String> transferRefs;

        public Builder orderRefs(List<OrderId> orderRefs) {
            this.orderRefs = orderRefs;
            return this;
        }

        public Builder pickJobRefs(List<PickJobId> pickJobRefs) {
            this.pickJobRefs = pickJobRefs;
            return this;
        }

        public Builder processRefs(List<ProcessId> processRefs) {
            this.processRefs = processRefs;
            return this;
        }

        public Builder routingPlanRefs(List<RoutingPlanId> routingPlanRefs) {
            this.routingPlanRefs = routingPlanRefs;
            return this;
        }

        public Builder transferRefs(List<String> transferRefs) {
            this.transferRefs = transferRefs;
            return this;
        }

        public RelatedRefs build() {
            return new RelatedRefs(orderRefs, pickJobRefs, processRefs, routingPlanRefs, transferRefs);
        }
    }
}
