package de.joesst.dev.fulfillmenttools.facilityconnections;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * Scopes packaging unit selection to a subset of delivery destinations or categories.
 *
 * @param type     the dimension being filtered (postal code, country, or category)
 * @param values   list of values for the given type
 * @param operator optional logical operator applied to this context; {@code null} means positive match
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record PackagingUnitContext(PackagingUnitContextType type, List<String> values, ContextOperator operator) {}
