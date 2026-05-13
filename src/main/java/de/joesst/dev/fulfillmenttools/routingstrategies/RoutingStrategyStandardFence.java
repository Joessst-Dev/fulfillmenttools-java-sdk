package de.joesst.dev.fulfillmenttools.routingstrategies;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * A built-in (standard) routing strategy fence that filters candidate facilities based on a
 * predefined implementation.
 *
 * <p>Discriminator value: {@code "StandardFence"}
 *
 * @param type            discriminator field, always {@code "StandardFence"} (required)
 * @param active          whether this fence is active (required)
 * @param implementation  the fence implementation to use (required)
 * @param activeMode      the mode in which the fence is active
 * @param supportedModes  the modes supported by this fence
 * @param name            optional display name
 * @param description     optional description
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record RoutingStrategyStandardFence(
        String type,
        Boolean active,
        FenceImplementation implementation,
        FenceMode activeMode,
        List<FenceMode> supportedModes,
        String name,
        String description
) implements RoutingStrategyFence {}
