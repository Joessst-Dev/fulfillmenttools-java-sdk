package de.joesst.dev.fulfillmenttools.stocks;

/**
 * Known condition values for use with {@link StockSearchQuery.Builder#conditionsContainsEq}
 * and {@link StockSearchQuery.Builder#conditionsContainsIn}.
 */
public final class StockCondition {

    /** Stock is damaged or defective. */
    public static final String DEFECTIVE = "DEFECTIVE";

    private StockCondition() {}
}
