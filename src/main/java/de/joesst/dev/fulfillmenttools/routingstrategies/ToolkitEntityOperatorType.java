package de.joesst.dev.fulfillmenttools.routingstrategies;

/**
 * The possible operator types that can be used on toolkit entities when building predicates.
 */
public enum ToolkitEntityOperatorType {
    /** Value equals operator. */
    VALUE_EQUALS,
    /** Value not equals operator. */
    VALUE_NOT_EQUALS,
    /** Any value equals operator. */
    ANY_VALUE_EQUALS,
    /** Any value greater than or equals operator. */
    ANY_VALUE_GREATER_EQUALS,
    /** Any value greater than operator. */
    ANY_VALUE_GREATER_THAN,
    /** Any value less than or equals operator. */
    ANY_VALUE_LESS_EQUALS,
    /** Any value less than operator. */
    ANY_VALUE_LESS_THAN,
    /** Any value contains operator. */
    ANY_VALUE_CONTAINS,
    /** Any value not contains operator. */
    ANY_VALUE_NOT_CONTAINS,
    /** Every value equals operator. */
    EVERY_VALUE_EQUALS,
    /** Every value greater than or equals operator. */
    EVERY_VALUE_GREATER_EQUALS,
    /** Every value greater than operator. */
    EVERY_VALUE_GREATER_THAN,
    /** Every value less than or equals operator. */
    EVERY_VALUE_LESS_EQUALS,
    /** Every value less than operator. */
    EVERY_VALUE_LESS_THAN,
    /** Every value contains operator. */
    EVERY_VALUE_CONTAINS,
    /** Every value not contains operator. */
    EVERY_VALUE_NOT_CONTAINS,
    /** No value equals operator. */
    NO_VALUE_EQUALS,
    /** No value greater than or equals operator. */
    NO_VALUE_GREATER_EQUALS,
    /** No value greater than operator. */
    NO_VALUE_GREATER_THAN,
    /** No value less than or equals operator. */
    NO_VALUE_LESS_EQUALS,
    /** No value less than operator. */
    NO_VALUE_LESS_THAN,
    /** No value contains operator. */
    NO_VALUE_CONTAINS,
    /** No value not contains operator. */
    NO_VALUE_NOT_CONTAINS,
    /** Value contains operator. */
    VALUE_CONTAINS,
    /** Value not contains operator. */
    VALUE_NOT_CONTAINS,
    /** Greater than operator. */
    GREATER_THAN,
    /** Greater than or equals operator. */
    GREATER_EQUALS,
    /** Less than operator. */
    LESS_THAN,
    /** Less than or equals operator. */
    LESS_EQUALS
}
