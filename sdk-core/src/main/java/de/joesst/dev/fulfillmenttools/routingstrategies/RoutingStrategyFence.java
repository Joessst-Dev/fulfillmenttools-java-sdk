package de.joesst.dev.fulfillmenttools.routingstrategies;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Sealed interface representing a routing strategy fence.
 *
 * <p>Implementations are discriminated by the {@code type} property:
 * <ul>
 *   <li>{@code "StandardFence"} — {@link RoutingStrategyStandardFence}</li>
 *   <li>{@code "ToolkitFence"} — {@link RoutingStrategyToolkitFence}</li>
 * </ul>
 *
 * <p>Thread-safety: instances are immutable records.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = RoutingStrategyStandardFence.class, name = "StandardFence"),
        @JsonSubTypes.Type(value = RoutingStrategyToolkitFence.class, name = "ToolkitFence")
})
public sealed interface RoutingStrategyFence
        permits RoutingStrategyStandardFence, RoutingStrategyToolkitFence {
}
