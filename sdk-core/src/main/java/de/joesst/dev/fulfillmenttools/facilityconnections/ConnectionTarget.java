package de.joesst.dev.fulfillmenttools.facilityconnections;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.List;

/**
 * Discriminated union representing the target of a facility connection.
 *
 * <p>Jackson uses the {@code type} field as the discriminator for polymorphic
 * deserialization. The field is carried in every record component so it is
 * visible both during serialization and deserialization.
 *
 * <p>Use the static factory methods on each variant to construct instances:
 * <pre>{@code
 * ConnectionTarget customer  = ConnectionTarget.Customer.of();
 * ConnectionTarget facility  = ConnectionTarget.ManagedFacility.of("fac-123");
 * ConnectionTarget supplier  = ConnectionTarget.Supplier.of("fac-456");
 * }</pre>
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type",
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ConnectionTarget.Customer.class, name = "CUSTOMER"),
        @JsonSubTypes.Type(value = ConnectionTarget.ManagedFacility.class, name = "MANAGED_FACILITY"),
        @JsonSubTypes.Type(value = ConnectionTarget.Supplier.class, name = "SUPPLIER")
})
public sealed interface ConnectionTarget
        permits ConnectionTarget.Customer, ConnectionTarget.ManagedFacility, ConnectionTarget.Supplier {

    /**
     * Returns the type discriminator string for this target variant.
     *
     * @return one of {@code "CUSTOMER"}, {@code "MANAGED_FACILITY"}, or {@code "SUPPLIER"}
     */
    String type();

    /**
     * A connection whose target is a customer (end consumer).
     *
     * @param type always {@code "CUSTOMER"}
     */
    record Customer(String type) implements ConnectionTarget {

        /**
         * Creates a {@code Customer} target.
         *
         * @return a new {@code Customer} instance
         */
        public static Customer of() {
            return new Customer("CUSTOMER");
        }
    }

    /**
     * A connection whose target is a managed facility, optionally with exclusion lists.
     *
     * @param type                     always {@code "MANAGED_FACILITY"}
     * @param facilityRef              reference to the target facility
     * @param excludedFacilityRefs     optional list of facility refs to exclude from routing
     * @param excludedFacilityGroupRefs optional list of facility group refs to exclude from routing
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    record ManagedFacility(
            String type,
            String facilityRef,
            List<String> excludedFacilityRefs,
            List<String> excludedFacilityGroupRefs
    ) implements ConnectionTarget {

        /**
         * Creates a {@code ManagedFacility} target pointing to the given facility.
         *
         * @param facilityRef reference to the target facility; must not be {@code null}
         * @return a new {@code ManagedFacility} instance with no exclusions
         */
        public static ManagedFacility of(String facilityRef) {
            return new ManagedFacility("MANAGED_FACILITY", facilityRef, null, null);
        }

        /**
         * Creates a {@code ManagedFacility} target with explicit exclusion lists.
         *
         * @param facilityRef              reference to the target facility; must not be {@code null}
         * @param excludedFacilityRefs     facility refs to exclude; may be {@code null}
         * @param excludedFacilityGroupRefs facility group refs to exclude; may be {@code null}
         * @return a new {@code ManagedFacility} instance
         */
        public static ManagedFacility of(String facilityRef,
                                         List<String> excludedFacilityRefs,
                                         List<String> excludedFacilityGroupRefs) {
            return new ManagedFacility("MANAGED_FACILITY", facilityRef, excludedFacilityRefs, excludedFacilityGroupRefs);
        }
    }

    /**
     * A connection whose target is a supplier facility, optionally with exclusion lists.
     *
     * @param type                     always {@code "SUPPLIER"}
     * @param facilityRef              reference to the target supplier facility
     * @param excludedFacilityRefs     optional list of facility refs to exclude from routing
     * @param excludedFacilityGroupRefs optional list of facility group refs to exclude from routing
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    record Supplier(
            String type,
            String facilityRef,
            List<String> excludedFacilityRefs,
            List<String> excludedFacilityGroupRefs
    ) implements ConnectionTarget {

        /**
         * Creates a {@code Supplier} target pointing to the given facility.
         *
         * @param facilityRef reference to the target supplier facility; must not be {@code null}
         * @return a new {@code Supplier} instance with no exclusions
         */
        public static Supplier of(String facilityRef) {
            return new Supplier("SUPPLIER", facilityRef, null, null);
        }

        /**
         * Creates a {@code Supplier} target with explicit exclusion lists.
         *
         * @param facilityRef              reference to the target supplier facility; must not be {@code null}
         * @param excludedFacilityRefs     facility refs to exclude; may be {@code null}
         * @param excludedFacilityGroupRefs facility group refs to exclude; may be {@code null}
         * @return a new {@code Supplier} instance
         */
        public static Supplier of(String facilityRef,
                                   List<String> excludedFacilityRefs,
                                   List<String> excludedFacilityGroupRefs) {
            return new Supplier("SUPPLIER", facilityRef, excludedFacilityRefs, excludedFacilityGroupRefs);
        }
    }
}
