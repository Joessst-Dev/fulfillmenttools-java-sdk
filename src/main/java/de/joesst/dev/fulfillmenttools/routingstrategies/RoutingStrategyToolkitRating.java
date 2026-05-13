package de.joesst.dev.fulfillmenttools.routingstrategies;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;

/**
 * A custom (toolkit) routing strategy rating that ranks candidate facilities based on a
 * user-defined rule.
 *
 * <p>Discriminator value: {@code "ToolkitRating"}
 *
 * @param type               discriminator field, always {@code "ToolkitRating"} (required)
 * @param active             whether this rating is active (required)
 * @param nameLocalized      localized display name (required)
 * @param descriptionLocalized localized description
 * @param maxPenalty         the maximum penalty this rating can contribute (required)
 * @param referenceId        external reference identifier (required)
 * @param comparisonRule     a V2 comparison rule; mutually exclusive with {@code rule}
 * @param rule               a V1 toolkit rule; mutually exclusive with {@code comparisonRule}
 * @param name               optional plain-text name
 * @param description        optional plain-text description
 * @param entity1            deprecated — left-side entity (use predicate-level entity instead)
 * @param entity2            deprecated — right-side entity (use predicate-level entity instead)
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record RoutingStrategyToolkitRating(
        String type,
        Boolean active,
        Map<String, String> nameLocalized,
        Map<String, String> descriptionLocalized,
        Integer maxPenalty,
        String referenceId,
        ToolkitComparisonRule comparisonRule,
        ToolkitRule rule,
        String name,
        String description,
        ToolkitAllowedEntities entity1,
        ToolkitAllowedEntities entity2
) implements RoutingStrategyRating {}
