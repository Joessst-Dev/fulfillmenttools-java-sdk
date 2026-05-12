package de.joesst.dev.fulfillmenttools.internal.tags;

import java.util.List;

record CreateTagBody(String id, List<String> allowedValues) {}
