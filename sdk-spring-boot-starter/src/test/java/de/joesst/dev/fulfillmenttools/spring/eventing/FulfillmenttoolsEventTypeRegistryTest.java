package de.joesst.dev.fulfillmenttools.spring.eventing;

import de.joesst.dev.fulfillmenttools.externalactions.ExternalAction;
import de.joesst.dev.fulfillmenttools.facilities.Facility;
import de.joesst.dev.fulfillmenttools.facilitygroups.FacilityGroup;
import de.joesst.dev.fulfillmenttools.handoverjobs.HandoverJob;
import de.joesst.dev.fulfillmenttools.inbound.StowJob;
import de.joesst.dev.fulfillmenttools.listings.Listing;
import de.joesst.dev.fulfillmenttools.orders.Order;
import de.joesst.dev.fulfillmenttools.packjobs.PackJob;
import de.joesst.dev.fulfillmenttools.pickjobs.PickJob;
import de.joesst.dev.fulfillmenttools.processes.Process;
import de.joesst.dev.fulfillmenttools.reservations.Reservation;
import de.joesst.dev.fulfillmenttools.returns.Return;
import de.joesst.dev.fulfillmenttools.routingplans.RoutingPlan;
import de.joesst.dev.fulfillmenttools.stocks.StockItem;
import de.joesst.dev.fulfillmenttools.storagelocations.StorageLocation;
import de.joesst.dev.fulfillmenttools.users.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FulfillmenttoolsEventTypeRegistryTest {

    private FulfillmenttoolsEventTypeRegistry registry;

    @BeforeEach
    void setUp() {
        registry = new FulfillmenttoolsEventTypeRegistry();
    }

    @Nested
    class WhenResolvingPreRegisteredEventTypes {

        @ParameterizedTest(name = "{0} → {1}")
        @MethodSource("preRegisteredMappings")
        void shouldResolveToExpectedEntityClass(String eventType, Class<?> expectedClass) {
            assertThat(registry.resolve(eventType))
                    .isPresent()
                    .hasValue(expectedClass);
        }

        static Stream<Arguments> preRegisteredMappings() {
            return Stream.of(
                    // Orders
                    Arguments.of("ORDER_CREATED", Order.class),
                    Arguments.of("ORDER_MODIFIED", Order.class),
                    Arguments.of("ORDER_CANCELLED", Order.class),
                    Arguments.of("ORDER_CANCELLED_BY_EXPIRY", Order.class),
                    Arguments.of("ORDER_FORCE_CANCELLED", Order.class),
                    Arguments.of("ORDER_UNLOCKED", Order.class),

                    // Processes
                    Arguments.of("PROCESS_ANONYMIZED", Process.class),
                    Arguments.of("PROCESS_DELETED", Process.class),

                    // Pick Jobs
                    Arguments.of("PICK_JOB_CREATED", PickJob.class),
                    Arguments.of("PICK_JOB_PICKING_COMMENCED", PickJob.class),
                    Arguments.of("PICK_JOB_PICKING_FINISHED", PickJob.class),
                    Arguments.of("PICK_JOB_PICKING_PAUSED", PickJob.class),
                    Arguments.of("PICK_JOB_PICK_LINE_PICKED", PickJob.class),
                    Arguments.of("PICK_JOB_ABORTED", PickJob.class),
                    Arguments.of("PICK_JOB_CANCELED", PickJob.class),
                    Arguments.of("PICK_JOB_REROUTED", PickJob.class),
                    Arguments.of("PICK_JOB_RESET", PickJob.class),
                    Arguments.of("PICK_JOB_OPENED", PickJob.class),
                    Arguments.of("PICKING_COMPLETED", PickJob.class),
                    Arguments.of("PICKING_PICK_JOB_COMPLETE", PickJob.class),

                    // Pack Jobs
                    Arguments.of("PACK_JOB_CREATED", PackJob.class),
                    Arguments.of("PACK_JOB_UPDATED", PackJob.class),
                    Arguments.of("PACK_JOB_CLOSED", PackJob.class),
                    Arguments.of("PACK_JOB_CANCELED", PackJob.class),

                    // Handover Jobs
                    Arguments.of("HANDOVERJOB_CREATED", HandoverJob.class),
                    Arguments.of("HANDOVERJOB_HANDED_OVER", HandoverJob.class),
                    Arguments.of("HANDOVERJOB_REVERTED", HandoverJob.class),
                    Arguments.of("HANDOVERJOB_CANCELED", HandoverJob.class),

                    // Returns
                    Arguments.of("RETURN_CREATED", Return.class),
                    Arguments.of("RETURN_CLAIMED", Return.class),
                    Arguments.of("RETURN_CLOSED", Return.class),
                    Arguments.of("RETURN_CANCELED", Return.class),
                    Arguments.of("RETURN_UPDATED", Return.class),

                    // Facilities
                    Arguments.of("FACILITY_CREATED", Facility.class),
                    Arguments.of("FACILITY_DELETED", Facility.class),
                    Arguments.of("FACILITY_UPDATED", Facility.class),
                    Arguments.of("FACILITY_SUSPENDED", Facility.class),
                    Arguments.of("FACILITY_WENT_OFFLINE", Facility.class),
                    Arguments.of("FACILITY_WENT_ONLINE", Facility.class),
                    Arguments.of("FACILITY_GROUP_CREATED", FacilityGroup.class),
                    Arguments.of("FACILITY_GROUP_DELETED", FacilityGroup.class),
                    Arguments.of("FACILITY_GROUP_UPDATED", FacilityGroup.class),

                    // Users
                    Arguments.of("USER_CREATED", User.class),
                    Arguments.of("USER_DELETED", User.class),
                    Arguments.of("USER_UPDATED", User.class),

                    // Inventory Stocks
                    Arguments.of("INVENTORY_STOCKS_CREATED", StockItem.class),
                    Arguments.of("INVENTORY_STOCKS_DELETED", StockItem.class),
                    Arguments.of("INVENTORY_STOCKS_VALUE_CHANGED", StockItem.class),
                    Arguments.of("INVENTORY_STOCKS_LOCATION_CHANGED", StockItem.class),
                    Arguments.of("INVENTORY_FACILITY_STOCK_CHANGED", StockItem.class),

                    // Inventory Reservations
                    Arguments.of("INVENTORY_RESERVATIONS_CREATED", Reservation.class),
                    Arguments.of("INVENTORY_RESERVATIONS_DELETED", Reservation.class),

                    // Storage Locations
                    Arguments.of("STORAGE_LOCATIONS_CREATED", StorageLocation.class),
                    Arguments.of("STORAGE_LOCATIONS_DELETED", StorageLocation.class),
                    Arguments.of("STORAGE_LOCATIONS_INFORMATION_CHANGED", StorageLocation.class),

                    // Routing Plans
                    Arguments.of("ROUTING_PLAN_ROUTED", RoutingPlan.class),
                    Arguments.of("ROUTING_PLAN_NOT_ROUTABLE", RoutingPlan.class),
                    Arguments.of("ROUTING_PLAN_CANCELLED", RoutingPlan.class),
                    Arguments.of("ROUTING_PLAN_SPLITTED", RoutingPlan.class),
                    Arguments.of("ROUTING_PLAN_FALLBACK", RoutingPlan.class),
                    Arguments.of("ROUTING_PLAN_WAITING", RoutingPlan.class),
                    Arguments.of("ROUTING_PLAN_REROUTEPLAN_CREATED", RoutingPlan.class),
                    Arguments.of("UPCOMING_TIME_TRIGGERED_REROUTE", RoutingPlan.class),

                    // Stow Jobs
                    Arguments.of("STOW_JOB_CREATED", StowJob.class),
                    Arguments.of("STOW_JOB_COMMENCED", StowJob.class),
                    Arguments.of("STOW_JOB_PAUSED", StowJob.class),
                    Arguments.of("STOW_JOB_OPENED", StowJob.class),
                    Arguments.of("STOW_JOB_LINE_ITEMS_STOWED", StowJob.class),
                    Arguments.of("STOW_JOB_CLOSED", StowJob.class),
                    Arguments.of("STOW_JOB_CANCELED", StowJob.class),

                    // External Actions
                    Arguments.of("EXTERNAL_ACTION_EXECUTED", ExternalAction.class),

                    // Listings
                    Arguments.of("LISTINGS_CREATED", Listing.class),
                    Arguments.of("LISTINGS_DELETED", Listing.class),
                    Arguments.of("LISTINGS_INFORMATION_CHANGED", Listing.class),
                    Arguments.of("LISTINGS_STATUS_CHANGED", Listing.class),
                    Arguments.of("LISTINGS_STOCK_CONFIGURATIONS_CHANGED", Listing.class)
            );
        }
    }

    @Nested
    class WhenResolvingUnknownEventType {

        @Test
        void shouldReturnEmpty() {
            assertThat(registry.resolve("UNKNOWN_EVENT")).isEmpty();
        }
    }

    @Nested
    class WhenRegisteringCustomEventType {

        @Test
        void shouldResolveAfterRegistration() {
            registry.register("MY_CUSTOM_EVENT", Order.class);
            assertThat(registry.resolve("MY_CUSTOM_EVENT")).hasValue(Order.class);
        }

        @Test
        void shouldOverwriteExistingMapping() {
            registry.register("ORDER_CREATED", PickJob.class);
            assertThat(registry.resolve("ORDER_CREATED")).hasValue(PickJob.class);
        }
    }

    @Nested
    class WhenRegisteringInvalidInput {

        @Test
        void shouldThrowOnNullEventType() {
            assertThatThrownBy(() -> registry.register(null, Order.class))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void shouldThrowOnBlankEventType() {
            assertThatThrownBy(() -> registry.register("  ", Order.class))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void shouldThrowOnNullEntityClass() {
            assertThatThrownBy(() -> registry.register("ORDER_CREATED", null))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }
}
