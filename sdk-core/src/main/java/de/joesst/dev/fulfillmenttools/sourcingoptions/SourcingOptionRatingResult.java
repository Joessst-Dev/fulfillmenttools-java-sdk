package de.joesst.dev.fulfillmenttools.sourcingoptions;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * A single rating criterion result contributing to the overall penalty score of a sourcing option.
 *
 * <p>Maps to the {@code RatingResult} schema in the fulfillmenttools OpenAPI specification.
 *
 * @param id                   unique identifier of this rating result
 * @param name                 name of the rating criterion (e.g. {@code DISTANCE})
 * @param penalty              penalty value assigned by this criterion
 * @param type                 the rating type (e.g. {@code StandardRating} or {@code ToolkitRating})
 * @param routingStrategyNodeId reference to the routing strategy node that produced this result
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record SourcingOptionRatingResult(
        String id,
        String name,
        Double penalty,
        String type,
        String routingStrategyNodeId
) {

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Builder() {}

        private String id;
        private String name;
        private Double penalty;
        private String type;
        private String routingStrategyNodeId;

        public Builder id(String id) { this.id = id; return this; }
        public Builder name(String name) { this.name = name; return this; }
        public Builder penalty(Double penalty) { this.penalty = penalty; return this; }
        public Builder type(String type) { this.type = type; return this; }
        public Builder routingStrategyNodeId(String routingStrategyNodeId) { this.routingStrategyNodeId = routingStrategyNodeId; return this; }

        public SourcingOptionRatingResult build() {
            return new SourcingOptionRatingResult(id, name, penalty, type, routingStrategyNodeId);
        }
    }
}
