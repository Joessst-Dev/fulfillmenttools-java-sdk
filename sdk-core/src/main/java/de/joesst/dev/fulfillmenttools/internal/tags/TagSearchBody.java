package de.joesst.dev.fulfillmenttools.internal.tags;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.joesst.dev.fulfillmenttools.tags.TagSearchQuery;

@JsonInclude(JsonInclude.Include.NON_NULL)
record TagSearchBody(TagSearchQuery query, Integer size, String after, String before, Integer last) {}
