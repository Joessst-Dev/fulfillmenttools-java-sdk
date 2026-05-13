package de.joesst.dev.fulfillmenttools.listings;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * Configuration that instructs the client how items should be scanned during picking.
 *
 * <p>Maps to the {@code ScanningRuleConfiguration} schema in the fulfillmenttools OpenAPI spec.
 *
 * <p>Thread-safety: immutable record; safe for concurrent use.
 *
 * @param values The ordered list of scanning rules to apply.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ListingScanningRule(
        List<ScanningRuleValue> values
) {

    /**
     * A single scanning rule entry with its priority and type.
     *
     * @param priority         Rank of this rule relative to others; lower number is preferred
     *                         ({@code >= 0}).
     * @param scanningRuleType The type of scanning rule: {@code ARTICLE} or {@code LOCATION}.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record ScanningRuleValue(
            Integer priority,
            String scanningRuleType
    ) {}
}
