package de.joesst.dev.fulfillmenttools.internal.tags;

import de.joesst.dev.fulfillmenttools.tags.Tag;

import java.util.List;

record TagListResponse(List<Tag> tags, Integer total) {}
