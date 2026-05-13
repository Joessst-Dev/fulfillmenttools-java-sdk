package de.joesst.dev.fulfillmenttools.routingstrategies;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * A predicate for a Toolkit V2 fence that compares a left-side entity property against a
 * right-side entity property using the specified operator.
 *
 * @param entityOperator        the comparison operator (required)
 * @param leftEntity            the entity for the left side (required)
 * @param leftPropertyPath      path to the left-side property (required)
 * @param leftTransformation    optional transformation for the left value
 * @param leftTransformationArgs arguments for the left transformation
 * @param rightEntity           the entity for the right side (required)
 * @param rightPropertyPath     path to the right-side property (required)
 * @param rightTransformation   optional transformation for the right value
 * @param rightTransformationArgs arguments for the right transformation
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ToolkitComparisonPredicate(
        ToolkitRuleComparisonOperatorType entityOperator,
        ToolkitAllowedEntities leftEntity,
        String leftPropertyPath,
        ToolkitTransformationType leftTransformation,
        List<Object> leftTransformationArgs,
        ToolkitAllowedEntities rightEntity,
        String rightPropertyPath,
        ToolkitTransformationType rightTransformation,
        List<Object> rightTransformationArgs
) {}
