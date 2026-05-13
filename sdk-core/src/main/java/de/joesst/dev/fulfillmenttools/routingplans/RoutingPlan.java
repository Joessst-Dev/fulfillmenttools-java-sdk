package de.joesst.dev.fulfillmenttools.routingplans;

import de.joesst.dev.fulfillmenttools.id.FacilityId;
import de.joesst.dev.fulfillmenttools.id.OrderId;
import de.joesst.dev.fulfillmenttools.id.PickJobId;
import de.joesst.dev.fulfillmenttools.id.ProcessId;
import de.joesst.dev.fulfillmenttools.id.RoutingPlanId;
import de.joesst.dev.fulfillmenttools.id.TenantOrderId;
import de.joesst.dev.fulfillmenttools.orders.DeliveryPreferences;
import de.joesst.dev.fulfillmenttools.pickjobs.WorkflowInformation;
import de.joesst.dev.fulfillmenttools.routingstrategies.RoutingStrategyNodeConfig;

import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * A routing plan represents the decision-making record for fulfilling a customer order from
 * one or more facilities.
 *
 * <p>Maps to the {@code RoutingPlan} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param id                                  Platform-generated routing plan identifier (required).
 * @param version                             Optimistic-locking version (required).
 * @param created                             Creation timestamp (required).
 * @param lastModified                        Last modification timestamp (required).
 * @param facilityRef                         Reference to the assigned facility.
 * @param orderRef                            Reference to the originating order (required).
 * @param tenantOrderId                       The tenant's own {@link TenantOrderId} order ID.
 * @param processId                           Global process ID for GDPR and other processes
 *                                            (required).
 * @param status                              Current routing plan status (required).
 * @param consolidatedStatus                  Consolidated status that merges multiple status
 *                                            transitions (required).
 * @param orderDate                           Date the order was created at the supplying system
 *                                            (required).
 * @param priority                            Routing priority; higher values take precedence
 *                                            (required, minimum 0).
 * @param routingRun                          Iteration count through the routing process
 *                                            (required).
 * @param finalizeRun                         Iteration count through the finalizer process
 *                                            (required).
 * @param splitCount                          Number of order splits before this plan was created.
 * @param fallbackRoutingAfterTimeInSeconds   Time in seconds after which fallback routing is
 *                                            attempted.
 * @param runId                               ID of the routing run this plan was created in.
 * @param runType                             Rule type of the routing run. Known values:
 *                                            {@code DEFAULT}, {@code REROUTE},
 *                                            {@code ORDERSPLIT_ON_SHORTPICK},
 *                                            {@code MANUAL_ASSIGNMENT},
 *                                            {@code PROCESS_MANUALASSIGNMENT},
 *                                            {@code REACTIVATION}.
 * @param anonymized                          Whether GDPR-related data has been anonymized.
 * @param childRoutingPlanRef                 Reference to the child routing plan.
 * @param parentRoutingPlanRef                Reference to the parent routing plan.
 * @param pickJobRef                          Reference to the pick job created from this plan.
 * @param reRoutedFacilityRef                 Facility the order was rerouted from.
 * @param reRoutedPickJobRef                  Original pick job that was rerouted.
 * @param reRoutedRoutingPlanRef              Original routing plan that was rerouted.
 * @param firstRoutingAttempt                 Date of the first routing attempt.
 * @param provisioningTime                    Point in time by which the order must be provisioned.
 * @param targetTimeBaseDate                  Base date used to calculate the target time.
 * @param predecessorRerouteRoutingPlanRefs   IDs of predecessor routing plans (required).
 * @param successorRerouteRoutingPlanRefs     IDs of successor routing plans (required).
 * @param facilityBlackList                   Facilities to ignore during rerouting.
 * @param statusHistory                       Deprecated: historic status list; prefer
 *                                            {@code history}.
 * @param activeConfig                        The routing strategy node configuration active for
 *                                            this plan.
 * @param deliveryPreferences                 Delivery preferences indicating how and where the
 *                                            order should be fulfilled.
 * @param earliestPickingStart                The earliest allowed picking start time.
 * @param latestPickingStart                  The latest allowed picking start time.
 * @param rerouteDescription                  Description of why this plan was rerouted.
 * @param reRouteReason                       Machine-readable reroute reason code. Known values:
 *                                            {@code ORDERMODIFIED}, {@code PROCESSREROUTE},
 *                                            {@code MANUAL}, {@code SHORTPICK},
 *                                            {@code TIMETRIGGERED}, {@code STOCKZEROED},
 *                                            {@code ABORTED}, {@code RECALCULATION}.
 * @param targetAddress                       The delivery target address.
 * @param workflowInformation                 Workflow context for this routing plan (required).
 * @param customServices                      Custom services attached to this routing plan.
 * @param decisionLogs                        References to decision log entries (required).
 * @param expectedLineItems                   Line items expected at a facility via transfer.
 * @param history                             Status history entries for this plan.
 * @param orderLineItems                      The routing plan line items.
 * @param statusReasons                       Deprecated: status reason entries.
 * @param targetAddressesByDeliveryEvent      Delivery addresses grouped by delivery event.
 * @param transfers                           Transfer relationships for this routing plan.
 */
public record RoutingPlan(
        RoutingPlanId id,
        Integer version,
        Instant created,
        Instant lastModified,
        FacilityId facilityRef,
        OrderId orderRef,
        TenantOrderId tenantOrderId,
        ProcessId processId,
        String status,
        String consolidatedStatus,
        Instant orderDate,
        Double priority,
        Double routingRun,
        Double finalizeRun,
        Double splitCount,
        Double fallbackRoutingAfterTimeInSeconds,
        String runId,
        String runType,
        Boolean anonymized,
        RoutingPlanId childRoutingPlanRef,
        RoutingPlanId parentRoutingPlanRef,
        PickJobId pickJobRef,
        FacilityId reRoutedFacilityRef,
        PickJobId reRoutedPickJobRef,
        RoutingPlanId reRoutedRoutingPlanRef,
        Instant firstRoutingAttempt,
        Instant provisioningTime,
        Instant targetTimeBaseDate,
        List<RoutingPlanId> predecessorRerouteRoutingPlanRefs,
        List<RoutingPlanId> successorRerouteRoutingPlanRefs,
        List<FacilityId> facilityBlackList,
        List<String> statusHistory,
        RoutingStrategyNodeConfig activeConfig,
        DeliveryPreferences deliveryPreferences,
        EarliestPickingStart earliestPickingStart,
        LatestPickingStart latestPickingStart,
        RerouteDescription rerouteDescription,
        String reRouteReason,
        TargetAddress targetAddress,
        WorkflowInformation workflowInformation,
        List<CustomServiceReference> customServices,
        List<DecisionLogRef> decisionLogs,
        List<ExpectedLineItem> expectedLineItems,
        List<RoutingPlanHistory> history,
        List<RoutingPlanLineItem> orderLineItems,
        List<RoutingPlanStatusReason> statusReasons,
        List<DeliveryEventTargetAddress> targetAddressesByDeliveryEvent,
        List<RoutingPlanTransfer> transfers
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private RoutingPlanId id;
        private Integer version;
        private Instant created;
        private Instant lastModified;
        private FacilityId facilityRef;
        private OrderId orderRef;
        private TenantOrderId tenantOrderId;
        private ProcessId processId;
        private String status;
        private String consolidatedStatus;
        private Instant orderDate;
        private Double priority;
        private Double routingRun;
        private Double finalizeRun;
        private Double splitCount;
        private Double fallbackRoutingAfterTimeInSeconds;
        private String runId;
        private String runType;
        private Boolean anonymized;
        private RoutingPlanId childRoutingPlanRef;
        private RoutingPlanId parentRoutingPlanRef;
        private PickJobId pickJobRef;
        private FacilityId reRoutedFacilityRef;
        private PickJobId reRoutedPickJobRef;
        private RoutingPlanId reRoutedRoutingPlanRef;
        private Instant firstRoutingAttempt;
        private Instant provisioningTime;
        private Instant targetTimeBaseDate;
        private List<RoutingPlanId> predecessorRerouteRoutingPlanRefs;
        private List<RoutingPlanId> successorRerouteRoutingPlanRefs;
        private List<FacilityId> facilityBlackList;
        private List<String> statusHistory;
        private RoutingStrategyNodeConfig activeConfig;
        private DeliveryPreferences deliveryPreferences;
        private EarliestPickingStart earliestPickingStart;
        private LatestPickingStart latestPickingStart;
        private RerouteDescription rerouteDescription;
        private String reRouteReason;
        private TargetAddress targetAddress;
        private WorkflowInformation workflowInformation;
        private List<CustomServiceReference> customServices;
        private List<DecisionLogRef> decisionLogs;
        private List<ExpectedLineItem> expectedLineItems;
        private List<RoutingPlanHistory> history;
        private List<RoutingPlanLineItem> orderLineItems;
        private List<RoutingPlanStatusReason> statusReasons;
        private List<DeliveryEventTargetAddress> targetAddressesByDeliveryEvent;
        private List<RoutingPlanTransfer> transfers;

        private Builder() {}

        public Builder id(RoutingPlanId id) { this.id = id; return this; }
        public Builder version(Integer version) { this.version = version; return this; }
        public Builder created(Instant created) { this.created = created; return this; }
        public Builder lastModified(Instant lastModified) { this.lastModified = lastModified; return this; }
        public Builder facilityRef(FacilityId facilityRef) { this.facilityRef = facilityRef; return this; }
        public Builder orderRef(OrderId orderRef) { this.orderRef = orderRef; return this; }
        public Builder tenantOrderId(TenantOrderId tenantOrderId) { this.tenantOrderId = tenantOrderId; return this; }
        public Builder processId(ProcessId processId) { this.processId = processId; return this; }
        public Builder status(String status) { this.status = status; return this; }
        public Builder consolidatedStatus(String consolidatedStatus) { this.consolidatedStatus = consolidatedStatus; return this; }
        public Builder orderDate(Instant orderDate) { this.orderDate = orderDate; return this; }
        public Builder priority(Double priority) { this.priority = priority; return this; }
        public Builder routingRun(Double routingRun) { this.routingRun = routingRun; return this; }
        public Builder finalizeRun(Double finalizeRun) { this.finalizeRun = finalizeRun; return this; }
        public Builder splitCount(Double splitCount) { this.splitCount = splitCount; return this; }
        public Builder fallbackRoutingAfterTimeInSeconds(Double fallbackRoutingAfterTimeInSeconds) { this.fallbackRoutingAfterTimeInSeconds = fallbackRoutingAfterTimeInSeconds; return this; }
        public Builder runId(String runId) { this.runId = runId; return this; }
        public Builder runType(String runType) { this.runType = runType; return this; }
        public Builder anonymized(Boolean anonymized) { this.anonymized = anonymized; return this; }
        public Builder childRoutingPlanRef(RoutingPlanId childRoutingPlanRef) { this.childRoutingPlanRef = childRoutingPlanRef; return this; }
        public Builder parentRoutingPlanRef(RoutingPlanId parentRoutingPlanRef) { this.parentRoutingPlanRef = parentRoutingPlanRef; return this; }
        public Builder pickJobRef(PickJobId pickJobRef) { this.pickJobRef = pickJobRef; return this; }
        public Builder reRoutedFacilityRef(FacilityId reRoutedFacilityRef) { this.reRoutedFacilityRef = reRoutedFacilityRef; return this; }
        public Builder reRoutedPickJobRef(PickJobId reRoutedPickJobRef) { this.reRoutedPickJobRef = reRoutedPickJobRef; return this; }
        public Builder reRoutedRoutingPlanRef(RoutingPlanId reRoutedRoutingPlanRef) { this.reRoutedRoutingPlanRef = reRoutedRoutingPlanRef; return this; }
        public Builder firstRoutingAttempt(Instant firstRoutingAttempt) { this.firstRoutingAttempt = firstRoutingAttempt; return this; }
        public Builder provisioningTime(Instant provisioningTime) { this.provisioningTime = provisioningTime; return this; }
        public Builder targetTimeBaseDate(Instant targetTimeBaseDate) { this.targetTimeBaseDate = targetTimeBaseDate; return this; }
        public Builder predecessorRerouteRoutingPlanRefs(List<RoutingPlanId> predecessorRerouteRoutingPlanRefs) { this.predecessorRerouteRoutingPlanRefs = predecessorRerouteRoutingPlanRefs; return this; }
        public Builder successorRerouteRoutingPlanRefs(List<RoutingPlanId> successorRerouteRoutingPlanRefs) { this.successorRerouteRoutingPlanRefs = successorRerouteRoutingPlanRefs; return this; }
        public Builder facilityBlackList(List<FacilityId> facilityBlackList) { this.facilityBlackList = facilityBlackList; return this; }
        public Builder statusHistory(List<String> statusHistory) { this.statusHistory = statusHistory; return this; }
        public Builder activeConfig(RoutingStrategyNodeConfig activeConfig) { this.activeConfig = activeConfig; return this; }
        public Builder deliveryPreferences(DeliveryPreferences deliveryPreferences) { this.deliveryPreferences = deliveryPreferences; return this; }
        public Builder earliestPickingStart(EarliestPickingStart earliestPickingStart) { this.earliestPickingStart = earliestPickingStart; return this; }
        public Builder latestPickingStart(LatestPickingStart latestPickingStart) { this.latestPickingStart = latestPickingStart; return this; }
        public Builder rerouteDescription(RerouteDescription rerouteDescription) { this.rerouteDescription = rerouteDescription; return this; }
        public Builder reRouteReason(String reRouteReason) { this.reRouteReason = reRouteReason; return this; }
        public Builder targetAddress(TargetAddress targetAddress) { this.targetAddress = targetAddress; return this; }
        public Builder workflowInformation(WorkflowInformation workflowInformation) { this.workflowInformation = workflowInformation; return this; }
        public Builder customServices(List<CustomServiceReference> customServices) { this.customServices = customServices; return this; }
        public Builder decisionLogs(List<DecisionLogRef> decisionLogs) { this.decisionLogs = decisionLogs; return this; }
        public Builder expectedLineItems(List<ExpectedLineItem> expectedLineItems) { this.expectedLineItems = expectedLineItems; return this; }
        public Builder history(List<RoutingPlanHistory> history) { this.history = history; return this; }
        public Builder orderLineItems(List<RoutingPlanLineItem> orderLineItems) { this.orderLineItems = orderLineItems; return this; }
        public Builder statusReasons(List<RoutingPlanStatusReason> statusReasons) { this.statusReasons = statusReasons; return this; }
        public Builder targetAddressesByDeliveryEvent(List<DeliveryEventTargetAddress> targetAddressesByDeliveryEvent) { this.targetAddressesByDeliveryEvent = targetAddressesByDeliveryEvent; return this; }
        public Builder transfers(List<RoutingPlanTransfer> transfers) { this.transfers = transfers; return this; }

        public RoutingPlan build() {
            return new RoutingPlan(id, version, created, lastModified, facilityRef, orderRef, tenantOrderId, processId, status, consolidatedStatus, orderDate, priority, routingRun, finalizeRun, splitCount, fallbackRoutingAfterTimeInSeconds, runId, runType, anonymized, childRoutingPlanRef, parentRoutingPlanRef, pickJobRef, reRoutedFacilityRef, reRoutedPickJobRef, reRoutedRoutingPlanRef, firstRoutingAttempt, provisioningTime, targetTimeBaseDate, predecessorRerouteRoutingPlanRefs, successorRerouteRoutingPlanRefs, facilityBlackList, statusHistory, activeConfig, deliveryPreferences, earliestPickingStart, latestPickingStart, rerouteDescription, reRouteReason, targetAddress, workflowInformation, customServices, decisionLogs, expectedLineItems, history, orderLineItems, statusReasons, targetAddressesByDeliveryEvent, transfers);
        }
    }
}
