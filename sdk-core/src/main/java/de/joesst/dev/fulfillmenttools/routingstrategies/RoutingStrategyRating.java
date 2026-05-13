package de.joesst.dev.fulfillmenttools.routingstrategies;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Sealed interface representing a routing strategy rating.
 *
 * <p>Ratings rank candidate facilities against each other during order routing. Implementations
 * are discriminated by the {@code type} property:
 * <ul>
 *   <li>{@code "StandardRating"} — {@link RoutingStrategyStandardRating}</li>
 *   <li>{@code "ToolkitRating"} — {@link RoutingStrategyToolkitRating}</li>
 * </ul>
 *
 * <p>Thread-safety: instances are immutable records.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = RoutingStrategyStandardRating.class, name = "StandardRating"),
        @JsonSubTypes.Type(value = RoutingStrategyToolkitRating.class, name = "ToolkitRating")
})
public sealed interface RoutingStrategyRating
        permits RoutingStrategyStandardRating, RoutingStrategyToolkitRating {
}
