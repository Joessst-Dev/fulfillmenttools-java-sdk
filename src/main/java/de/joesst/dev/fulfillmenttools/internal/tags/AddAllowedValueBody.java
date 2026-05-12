package de.joesst.dev.fulfillmenttools.internal.tags;

import java.util.List;

record AddAllowedValueBody(Integer version, List<ActionItem> actions) {
    record ActionItem(String action, String allowedValue) {}
}
