package de.joesst.dev.fulfillmenttools.routingstrategies;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * A single predicate used within a {@link RoutingStrategyConditionRule}.
 *
 * @param propertyPath       dot-separated path to the property being evaluated (required)
 * @param entityOperator     the operator to apply (required)
 * @param expectedValue      the value to compare against; may be a string, number, or boolean
 * @param transformation     optional transformation to apply to the property value
 * @param transformationArgs arguments for the transformation function
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record RoutingStrategyConditionPredicate(
        String propertyPath,
        ToolkitEntityOperatorType entityOperator,
        Object expectedValue,
        ToolkitTransformationType transformation,
        List<Object> transformationArgs
) {}
