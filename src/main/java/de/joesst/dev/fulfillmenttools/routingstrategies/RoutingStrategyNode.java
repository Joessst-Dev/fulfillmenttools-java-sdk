package de.joesst.dev.fulfillmenttools.routingstrategies;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.Map;

/**
 * A node in the routing strategy decision tree. Nodes hold the actual fence/rating configuration
 * and can chain to further conditions via {@code nextCondition}.
 *
 * @param id                   unique identifier of this node (required on read)
 * @param active               whether this node is active (required)
 * @param config               the fence/rating/split/reroute configuration for this node (required)
 * @param nameLocalized        localized display name (required)
 * @param descriptionLocalized localized description
 * @param name                 optional plain-text name
 * @param description          optional plain-text description
 * @param categoryRef          optional reference to a node config category
 * @param nextCondition        optional condition chain evaluated after this node
 * @param activationTimeFrames time frames during which this node is active (max 1)
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record RoutingStrategyNode(
        String id,
        Boolean active,
        RoutingStrategyNodeConfig config,
        Map<String, String> nameLocalized,
        Map<String, String> descriptionLocalized,
        String name,
        String description,
        String categoryRef,
        RoutingStrategyCondition nextCondition,
        List<ActivationTimeFrame> activationTimeFrames
) {}
