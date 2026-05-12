package de.joesst.dev.fulfillmenttools.facilityconnections;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * Scopes a facility connection to a subset of entities (e.g. specific facilities,
 * facility groups, categories, or tags).
 *
 * @param type     the kind of entity being referenced
 * @param values   list of entity identifiers of the given type
 * @param operator optional logical operator applied to this context; {@code null} means positive match
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ConnectionContext(ConnectionContextType type, List<String> values, ContextOperator operator) {}
