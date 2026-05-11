package de.joesst.dev.fulfillmenttools.model;

import java.util.List;

public record Page<T>(List<T> items, String nextCursor) {

    public boolean hasMore() {
        return nextCursor != null && !nextCursor.isEmpty();
    }
}
