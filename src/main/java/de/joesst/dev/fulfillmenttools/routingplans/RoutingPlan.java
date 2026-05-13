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
) {}
