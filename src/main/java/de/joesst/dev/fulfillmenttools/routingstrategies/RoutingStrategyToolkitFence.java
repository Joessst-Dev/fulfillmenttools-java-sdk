package de.joesst.dev.fulfillmenttools.routingstrategies;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;

/**
 * A custom (toolkit) routing strategy fence that filters candidate facilities based on a
 * user-defined rule.
 *
 * <p>Discriminator value: {@code "ToolkitFence"}
 *
 * @param type               discriminator field, always {@code "ToolkitFence"} (required)
 * @param active             whether this fence is active (default {@code false})
 * @param nameLocalized      localized display name
 * @param descriptionLocalized localized description
 * @param name               optional plain-text name
 * @param description        optional plain-text description
 * @param comparisonRule     a V2 comparison rule; mutually exclusive with {@code rule}
 * @param rule               a V1 toolkit rule; mutually exclusive with {@code comparisonRule}
 * @param order              execution order of this fence
 * @param referenceId        external reference identifier
 * @param entity1            deprecated — entity for the left side (use predicate-level entity instead)
 * @param entity2            deprecated — entity for the right side (use predicate-level entity instead)
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record RoutingStrategyToolkitFence(
        String type,
        Boolean active,
        Map<String, String> nameLocalized,
        Map<String, String> descriptionLocalized,
        String name,
        String description,
        ToolkitComparisonRule comparisonRule,
        ToolkitRule rule,
        Integer order,
        String referenceId,
        ToolkitAllowedEntities entity1,
        ToolkitAllowedEntities entity2
) implements RoutingStrategyFence {}
