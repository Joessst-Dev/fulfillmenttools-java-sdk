package de.joesst.dev.fulfillmenttools.facilityconnections;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class UpdateFacilityConnectionRequest {

    private final Integer version;
    private final String type;
    private final Map<String, Object> target;
    private final String carrierKey;
    private final String carrierName;
    private final List<Map<String, Object>> context;
    private final List<Map<String, Object>> fallbackCosts;
    private final List<Map<String, Object>> nonDeliveryDays;
    private final List<Map<String, Object>> packagingUnitsByContexts;
    private final Map<String, Object> cutoffTimes;
    private final Map<String, Object> fallbackTransitTime;
    private final Map<String, Object> customAttributes;

    private UpdateFacilityConnectionRequest(Builder builder) {
        this.version = Objects.requireNonNull(builder.version, "version must not be null");
        this.type = Objects.requireNonNull(builder.type, "type must not be null");
        this.target = Objects.requireNonNull(builder.target, "target must not be null");
        this.carrierKey = builder.carrierKey;
        this.carrierName = builder.carrierName;
        this.context = builder.context;
        this.fallbackCosts = builder.fallbackCosts;
        this.nonDeliveryDays = builder.nonDeliveryDays;
        this.packagingUnitsByContexts = builder.packagingUnitsByContexts;
        this.cutoffTimes = builder.cutoffTimes;
        this.fallbackTransitTime = builder.fallbackTransitTime;
        this.customAttributes = builder.customAttributes;
    }

    public Integer version() { return version; }
    public String type() { return type; }
    public Map<String, Object> target() { return target; }
    public String carrierKey() { return carrierKey; }
    public String carrierName() { return carrierName; }
    public List<Map<String, Object>> context() { return context; }
    public List<Map<String, Object>> fallbackCosts() { return fallbackCosts; }
    public List<Map<String, Object>> nonDeliveryDays() { return nonDeliveryDays; }
    public List<Map<String, Object>> packagingUnitsByContexts() { return packagingUnitsByContexts; }
    public Map<String, Object> cutoffTimes() { return cutoffTimes; }
    public Map<String, Object> fallbackTransitTime() { return fallbackTransitTime; }
    public Map<String, Object> customAttributes() { return customAttributes; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private Integer version;
        private String type;
        private Map<String, Object> target;
        private String carrierKey;
        private String carrierName;
        private List<Map<String, Object>> context;
        private List<Map<String, Object>> fallbackCosts;
        private List<Map<String, Object>> nonDeliveryDays;
        private List<Map<String, Object>> packagingUnitsByContexts;
        private Map<String, Object> cutoffTimes;
        private Map<String, Object> fallbackTransitTime;
        private Map<String, Object> customAttributes;

        public Builder version(Integer version) { this.version = version; return this; }
        public Builder type(String type) { this.type = type; return this; }
        public Builder target(Map<String, Object> target) { this.target = target; return this; }
        public Builder carrierKey(String carrierKey) { this.carrierKey = carrierKey; return this; }
        public Builder carrierName(String carrierName) { this.carrierName = carrierName; return this; }
        public Builder context(List<Map<String, Object>> context) { this.context = context; return this; }
        public Builder fallbackCosts(List<Map<String, Object>> fallbackCosts) { this.fallbackCosts = fallbackCosts; return this; }
        public Builder nonDeliveryDays(List<Map<String, Object>> nonDeliveryDays) { this.nonDeliveryDays = nonDeliveryDays; return this; }
        public Builder packagingUnitsByContexts(List<Map<String, Object>> packagingUnitsByContexts) { this.packagingUnitsByContexts = packagingUnitsByContexts; return this; }
        public Builder cutoffTimes(Map<String, Object> cutoffTimes) { this.cutoffTimes = cutoffTimes; return this; }
        public Builder fallbackTransitTime(Map<String, Object> fallbackTransitTime) { this.fallbackTransitTime = fallbackTransitTime; return this; }
        public Builder customAttributes(Map<String, Object> customAttributes) { this.customAttributes = customAttributes; return this; }

        public UpdateFacilityConnectionRequest build() { return new UpdateFacilityConnectionRequest(this); }
    }
}
