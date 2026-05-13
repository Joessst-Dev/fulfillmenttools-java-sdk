package de.joesst.dev.fulfillmenttools.internal.tags;

import de.joesst.dev.fulfillmenttools.tags.Tag;

import java.util.List;

record TagSearchResponse(List<Tag> tags, PageInfoDto pageInfo, Integer total) {
    record PageInfoDto(String endCursor, Boolean hasNextPage, Boolean hasPreviousPage, String startCursor) {}
}
