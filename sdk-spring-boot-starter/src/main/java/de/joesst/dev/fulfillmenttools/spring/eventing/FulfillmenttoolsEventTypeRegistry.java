package de.joesst.dev.fulfillmenttools.spring.eventing;

import de.joesst.dev.fulfillmenttools.externalactions.ExternalAction;
import de.joesst.dev.fulfillmenttools.facilities.Facility;
import de.joesst.dev.fulfillmenttools.facilitygroups.FacilityGroup;
import de.joesst.dev.fulfillmenttools.handoverjobs.HandoverJob;
import de.joesst.dev.fulfillmenttools.inbound.StowJob;
import de.joesst.dev.fulfillmenttools.orders.Order;
import de.joesst.dev.fulfillmenttools.packjobs.PackJob;
import de.joesst.dev.fulfillmenttools.pickjobs.PickJob;
import de.joesst.dev.fulfillmenttools.reservations.Reservation;
import de.joesst.dev.fulfillmenttools.returns.Return;
import de.joesst.dev.fulfillmenttools.routingplans.RoutingPlan;
import de.joesst.dev.fulfillmenttools.stocks.StockItem;
import de.joesst.dev.fulfillmenttools.storagelocations.StorageLocation;
import de.joesst.dev.fulfillmenttools.users.User;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Maps fulfillmenttools platform event type strings to their corresponding Java entity classes.
 *
 * <p>The registry is populated with the full set of known platform event types on construction.
 * Applications can extend it at startup by calling {@link #register(String, Class)}.
 *
 * <p>This class is thread-safe. All read and write operations use a {@link ConcurrentHashMap}.
 */
public class FulfillmenttoolsEventTypeRegistry {

    private final Map<String, Class<?>> mappings = new ConcurrentHashMap<>();

    /**
     * Creates a registry pre-populated with all known fulfillmenttools platform event types.
     */
    public FulfillmenttoolsEventTypeRegistry() {
        register("ORDER_CREATED", Order.class);
        register("ORDER_MODIFIED", Order.class);
        register("ORDER_CANCELLED", Order.class);
        register("ORDER_CANCELLED_BY_EXPIRY", Order.class);
        register("ORDER_FORCE_CANCELLED", Order.class);
        register("ORDER_UNLOCKED", Order.class);

        register("PICK_JOB_CREATED", PickJob.class);
        register("PICK_JOB_PICKING_COMMENCED", PickJob.class);
        register("PICK_JOB_PICKING_FINISHED", PickJob.class);
        register("PICK_JOB_PICKING_PAUSED", PickJob.class);
        register("PICK_JOB_PICK_LINE_PICKED", PickJob.class);
        register("PICK_JOB_ABORTED", PickJob.class);
        register("PICK_JOB_CANCELED", PickJob.class);
        register("PICK_JOB_REROUTED", PickJob.class);
        register("PICK_JOB_RESET", PickJob.class);
        register("PICK_JOB_OPENED", PickJob.class);
        register("PICKING_COMPLETED", PickJob.class);

        register("PACK_JOB_CREATED", PackJob.class);
        register("PACK_JOB_UPDATED", PackJob.class);
        register("PACK_JOB_CLOSED", PackJob.class);
        register("PACK_JOB_CANCELED", PackJob.class);

        register("HANDOVERJOB_CREATED", HandoverJob.class);
        register("HANDOVERJOB_HANDED_OVER", HandoverJob.class);
        register("HANDOVERJOB_REVERTED", HandoverJob.class);
        register("HANDOVERJOB_CANCELED", HandoverJob.class);

        register("RETURN_CREATED", Return.class);
        register("RETURN_CLAIMED", Return.class);
        register("RETURN_CLOSED", Return.class);
        register("RETURN_CANCELED", Return.class);
        register("RETURN_UPDATED", Return.class);

        register("FACILITY_CREATED", Facility.class);
        register("FACILITY_DELETED", Facility.class);
        register("FACILITY_UPDATED", Facility.class);
        register("FACILITY_SUSPENDED", Facility.class);
        register("FACILITY_WENT_OFFLINE", Facility.class);
        register("FACILITY_WENT_ONLINE", Facility.class);

        register("FACILITY_GROUP_CREATED", FacilityGroup.class);
        register("FACILITY_GROUP_DELETED", FacilityGroup.class);
        register("FACILITY_GROUP_UPDATED", FacilityGroup.class);

        register("USER_CREATED", User.class);
        register("USER_DELETED", User.class);
        register("USER_UPDATED", User.class);

        register("INVENTORY_STOCKS_CREATED", StockItem.class);
        register("INVENTORY_STOCKS_DELETED", StockItem.class);
        register("INVENTORY_STOCKS_VALUE_CHANGED", StockItem.class);
        register("INVENTORY_STOCKS_LOCATION_CHANGED", StockItem.class);

        register("INVENTORY_RESERVATIONS_CREATED", Reservation.class);
        register("INVENTORY_RESERVATIONS_DELETED", Reservation.class);

        register("STORAGE_LOCATIONS_CREATED", StorageLocation.class);
        register("STORAGE_LOCATIONS_DELETED", StorageLocation.class);
        register("STORAGE_LOCATIONS_INFORMATION_CHANGED", StorageLocation.class);

        register("ROUTING_PLAN_ROUTED", RoutingPlan.class);
        register("ROUTING_PLAN_NOT_ROUTABLE", RoutingPlan.class);
        register("ROUTING_PLAN_CANCELLED", RoutingPlan.class);
        register("ROUTING_PLAN_SPLITTED", RoutingPlan.class);
        register("ROUTING_PLAN_FALLBACK", RoutingPlan.class);
        register("ROUTING_PLAN_WAITING", RoutingPlan.class);
        register("ROUTING_PLAN_REROUTEPLAN_CREATED", RoutingPlan.class);
        register("UPCOMING_TIME_TRIGGERED_REROUTE", RoutingPlan.class);

        register("STOW_JOB_CREATED", StowJob.class);
        register("STOW_JOB_COMMENCED", StowJob.class);
        register("STOW_JOB_PAUSED", StowJob.class);
        register("STOW_JOB_OPENED", StowJob.class);
        register("STOW_JOB_LINE_ITEMS_STOWED", StowJob.class);
        register("STOW_JOB_CLOSED", StowJob.class);
        register("STOW_JOB_CANCELED", StowJob.class);

        register("EXTERNAL_ACTION_EXECUTED", ExternalAction.class);
    }

    /**
     * Registers a mapping from an event type string to a Java entity class.
     *
     * <p>If a mapping for the given event type already exists it will be replaced.
     *
     * @param eventType   the platform event type string (e.g. {@code "ORDER_CREATED"})
     * @param entityClass the class to deserialize the event payload into
     * @throws IllegalArgumentException if {@code eventType} is null or blank, or if
     *                                  {@code entityClass} is null
     */
    public void register(String eventType, Class<?> entityClass) {
        if (eventType == null || eventType.isBlank()) {
            throw new IllegalArgumentException("eventType must not be null or blank");
        }
        if (entityClass == null) {
            throw new IllegalArgumentException("entityClass must not be null");
        }
        mappings.put(eventType, entityClass);
    }

    /**
     * Resolves the entity class for a given event type string.
     *
     * @param eventType the platform event type string
     * @return an {@link Optional} containing the registered class, or empty when the type
     *         is not registered
     */
    public Optional<Class<?>> resolve(String eventType) {
        return Optional.ofNullable(mappings.get(eventType));
    }
}
