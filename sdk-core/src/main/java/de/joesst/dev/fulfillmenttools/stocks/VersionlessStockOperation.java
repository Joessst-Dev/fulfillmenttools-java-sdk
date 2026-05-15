package de.joesst.dev.fulfillmenttools.stocks;

/**
 * Marker interface for a single operation in a versionless bulk upsert via
 * {@code POST /api/stocks/actions} ({@code UPDATE_VERSIONLESS}).
 *
 * <p>Use {@link VersionlessStockCreate} to create a new stock entry, or
 * {@link VersionlessStockUpdate} to update an existing one.
 */
public sealed interface VersionlessStockOperation permits VersionlessStockCreate, VersionlessStockUpdate {}
