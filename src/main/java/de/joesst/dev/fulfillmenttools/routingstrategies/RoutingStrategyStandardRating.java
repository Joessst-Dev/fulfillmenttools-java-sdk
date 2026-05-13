package de.joesst.dev.fulfillmenttools.routingstrategies;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * A built-in (standard) routing strategy rating that ranks candidate facilities using a
 * predefined implementation.
 *
 * <p>Discriminator value: {@code "StandardRating"}
 *
 * @param type           discriminator field, always {@code "StandardRating"} (required)
 * @param active         whether this rating is active (required)
 * @param implementation the rating implementation to use (required)
 * @param maxPenalty     the maximum penalty this rating can contribute (required, minimum 0)
 * @param configuration  optional implementation-specific configuration object
 * @param name           optional display name
 * @param description    optional description
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record RoutingStrategyStandardRating(
        String type,
        Boolean active,
        RatingImplementation implementation,
        Double maxPenalty,
        Object configuration,
        String name,
        String description
) implements RoutingStrategyRating {}
