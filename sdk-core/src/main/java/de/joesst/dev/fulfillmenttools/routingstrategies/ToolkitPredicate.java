package de.joesst.dev.fulfillmenttools.routingstrategies;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * A predicate used in a toolkit rule (V1 style) that evaluates a property of an entity against
 * an expected value using the specified operator.
 *
 * @param entity            the entity whose property is evaluated
 * @param entityOperator    the operator to apply
 * @param propertyPath      dot-separated path to the entity property (required)
 * @param expectedValue     the value to compare against
 * @param transformation    optional transformation to apply to the property value before comparison
 * @param transformationArgs arguments for the transformation function
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ToolkitPredicate(
        ToolkitAllowedEntities entity,
        ToolkitEntityOperatorType entityOperator,
        String propertyPath,
        Object expectedValue,
        ToolkitTransformationType transformation,
        List<Object> transformationArgs
) {}
